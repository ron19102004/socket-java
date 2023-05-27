/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ron.socketserver.socket;

import java.io.*;
import java.net.Socket;

/**
 * @author ron
 */
public class ActionSocketClient implements Runnable {

    private int portClient;

    private BufferedReader reader;
    private BufferedWriter writer;

    public ActionSocketClient(Socket client) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.portClient = client.getPort();
    }
    private void offIO() {
        try {
            if (this.writer != null) this.writer.close();
            if (this.reader != null) this.reader.close();
            Server.clients.remove(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int getPort() {
        return this.portClient;
    }

    @Override
    public void run() {
        try {
            String req;
            while ((req = this.reader.readLine()) != null) {
                String[] re = req.split(":/");
                if (re[1].equals("exit")){
                    this.socket_emit("Out!!!");
                    break;
                }
                switch (re[0]) {
                    case "socket_emit": {
                        this.socket_emit(re[1]);
                        break;
                    }
                    case "broadcast_emit": {
                        this.broadcast_emit(re[1]);
                        break;
                    }
                    case "emit_to_port":{
                        String[] ex = re[1].split("/?");
                        this.emit_to_port(ex[0].trim(),Integer.parseInt(ex[1]));
                        break;
                    }
                    default: {
                        this.emit("error func not exist");
                    }
                }
            }
        } catch (IOException e) {
            this.offIO();
            System.out.println(e.getMessage());
        } finally {
            this.offIO();
        }
    }

    public void socket_emit(String message) {
        try {
            for (ActionSocketClient client : Server.clients) {
                client.emit(message);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void broadcast_emit(String message) {
        try {
            for (ActionSocketClient client : Server.clients) {
                if (client.getPort() != this.portClient) {
                    client.emit(message);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void emit_to_port(String message, int port){
        try {
            int size = Server.clients.size();
            while(size != 0){
                if(Server.clients.get(size - 1).getPort() == port){
                    Server.clients.get(size - 1).emit(message);
                    break;
                }
                size--;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void emit(String message) {
        try {
            this.writer.write(this.portClient +" send : "+ message);
            this.writer.newLine();
            this.writer.flush();
        } catch (IOException e) {
            this.offIO();
            System.out.println(e.getMessage());
        }
    }
}
