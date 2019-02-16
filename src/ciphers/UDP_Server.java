/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ciphers;

import java.net.*;
import java.io.*;

/**
 *
 * @author Yash Jain
 */
public class UDP_Server {

    public static String byteDataToString(byte[] a) {
        if (a == null) {
            return null;
        }
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret.toString();
    }

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(7070);
        byte[] received = new byte[65535];
        System.out.println("*******HELLO SERVER*******");
        DatagramPacket dataPacket;
        String key;
        
        dataPacket = new DatagramPacket(received, received.length);
        ds.receive(dataPacket);
        key = byteDataToString(received);

        PlayFair messageDecryption = new PlayFair(key, 0);
        CaesarCipher keyDecryption = new CaesarCipher();

        key = keyDecryption.decrypt(key, 10);
        System.out.println("Decrypted Key as on Server : " + key);

        received = new byte[65535];
        while (true) {
            dataPacket = new DatagramPacket(received, received.length);
            ds.receive(dataPacket);
            String encryptedMessage = byteDataToString(received);
            int messageLength = Integer.parseInt(encryptedMessage.charAt(encryptedMessage.length() - 1) + "");
            encryptedMessage = encryptedMessage.substring(0, encryptedMessage.length() - 1);
            System.out.println("Encrypted Message on server : " + encryptedMessage);
            messageDecryption.setMessageLength(messageLength);
            String message = messageDecryption.decrypt(encryptedMessage, key);
            System.out.println("Client:- " + message);
            if (message.equals("tata")) {
                System.out.println("Client sent tata.....EXITING");
                break;
            }
            received = new byte[65535];
        }
    }
}
