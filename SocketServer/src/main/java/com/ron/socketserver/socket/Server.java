/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ron.socketserver.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author ron
 */
public class Server {

    private ServerSocket serverSocket;
    public static ArrayList<ActionSocketClient> clients;

    public Server(int port) {
        clients = new ArrayList<>();
        this.init(port);
    }

    private void init(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            while (true) {
                Socket client = this.serverSocket.accept();
                ActionSocketClient actionSocketClient = new ActionSocketClient(client);
                clients.add(actionSocketClient);
                Thread thread = new Thread(actionSocketClient);
                thread.start();
                actionSocketClient.socket_emit(actionSocketClient.getPort() + " online !!! \n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
