/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ron.socketserver;

import com.ron.socketserver.socket.Server;


/**
 *
 * @author ron
 */
public class Application {
    public static void main(String[] args) {
        Server server = new Server(8080);
    }
}
