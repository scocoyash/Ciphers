package ciphers;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Yash Jain
 */
public class DES_Client {

    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);
        DatagramSocket ds = new DatagramSocket();
        InetAddress ip = InetAddress.getLocalHost();
        int port = 7070;
        byte buf[] = null;

        System.out.println("**********HELLO CLIENT*********");
        System.out.println("Enter key to initiate chat : ");
        String key = sc.nextLine();

        DES messageEncryption = new DES();

        while (true) {
            System.out.println("Enter Message : ");
            String input = sc.next();
            String encryptedMessage = messageEncryption.encrypt(input, key);
            String decryptedMessage = messageEncryption.decrypt(encryptedMessage, key);
            //encryptedMessage = encryptedMessage + messageEncryption.getMAC();
            System.out.println("Encrypted message : " + encryptedMessage);
            System.out.println("Decrypted message : " + decryptedMessage);
            buf = encryptedMessage.getBytes();
            DatagramPacket dataPacket = new DatagramPacket(buf, buf.length, ip, port);
            ds.send(dataPacket);

            if (input.equals("tatatata")) {
                break;
            }
        }
        ds.close();
    }
}
