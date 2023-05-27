/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ron.myapplication.socket;

import com.ron.myapplication.views.ChatView;
import java.io.*;
import java.net.Socket;

/**
 *
 * @author ron
 */
public class Client {

    private BufferedReader reader;
    private BufferedWriter writer;
    private Socket socketServer;
    private int portClient;
    private ChatView chatView;

    public Client(String host, int port, ChatView chatView) {
        this.chatView = chatView;
        try {
            this.socketServer = new Socket(host, port);
            this.portClient = this.socketServer.getPort();
            this.reader = new BufferedReader(new InputStreamReader(this.socketServer.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.socketServer.getOutputStream()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Thread read = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String res;
                    while ((res = reader.readLine()) != null) {
                        String[] resp = res.split(":");
                        if (resp[resp.length - 1].equals("Out!!!")) {
                            String[] ex = resp[0].split(" ");
                            int port = Integer.parseInt(ex[0]);
                            if (port == portClient) {
                                break;
                            }
                        }
                        if (!res.isBlank()) {
                            String text = chatView.boxViewChat.getText();
                            text += "------------------------------------------------------------\n";
                            text += "----> "+java.time.LocalTime.now() + "\n";
                            text += res + "\n";
                            chatView.boxViewChat.setText(text);
                        }
                    }
                } catch (IOException e) {
                    offIO();
                    System.out.println(e.getMessage());
                } finally {
                    offIO();
                }
            }
        });
        read.start();
    }

    private void offIO() {
        try {
            if (this.writer != null) {
                this.writer.close();
            }
            if (this.reader != null) {
                this.reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage(String func_name, String message) {
        try {
            this.writer.write(func_name + ":/" + message + ":/" + this.portClient);
            this.writer.newLine();
            this.writer.flush();
        } catch (IOException e) {
            this.offIO();
            System.out.println(e.getMessage());
        }
    }

    public void sendMessageToPort(String message, int port) {
        try {
            this.writer.write("emit_to_port:/" + message + " /? " + port + ":/" + this.portClient);
            this.writer.newLine();
            this.writer.flush();
        } catch (IOException e) {
            this.offIO();
            System.out.println(e.getMessage());
        }
    }
}
