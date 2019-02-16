/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ciphers;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author Yash Jain
 */
public class UDP_Client {

    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);
        DatagramSocket ds = new DatagramSocket();
        InetAddress ip = InetAddress.getLocalHost();
        int port = 7070;
        byte buf[] = null;
        
        System.out.println("**********HELLO CLIENT*********");
        System.out.println("Enter key to initiate chat : ");
        String key = sc.nextLine();

        PlayFair messageEncryption = new PlayFair(key, 0);
        CaesarCipher keyEncryption = new CaesarCipher();

        String encrypted_key = keyEncryption.encrypt(key.toLowerCase(), 10);
        System.out.println("Encrypted Key : " + encrypted_key);
        buf = encrypted_key.getBytes();
        DatagramPacket dataPacket = new DatagramPacket(buf, buf.length, ip, port);
        ds.send(dataPacket);

        while (true) {
            System.out.println("Enter Message : ");
            String input = sc.next();
            String encryptedMessage = messageEncryption.encrypt(input, key);
            String decryptedMessage = messageEncryption.decrypt(encryptedMessage, key);
            messageEncryption.setMessageLength(input.length());
            encryptedMessage += input.length();
            System.out.println("Encrypted message : " + encryptedMessage);
            buf = encryptedMessage.getBytes();
            dataPacket = new DatagramPacket(buf, buf.length, ip, port);
            ds.send(dataPacket);

            if (input.equals("tata")) {
                break;
            }
        }
        ds.close();
    }
}
