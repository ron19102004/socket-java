/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.ron.myapplication;

import com.ron.myapplication.socket.Client;
import com.ron.myapplication.views.ChatView;

import javax.swing.UIManager;

/**
 *
 * @author ron
 */
public class MyApplication {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new ChatView();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
