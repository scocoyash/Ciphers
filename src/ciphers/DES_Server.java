package ciphers;

import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Yash Jain
 */
public class DES_Server {
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
        return new String(ret);
    }

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(7070);
        Scanner sc = new Scanner(System.in);
        System.out.println("*******HELLO SERVER*******");
        System.out.println("Enter key to initiate chat : ");
        String key = sc.next();
        
        DES messageDecryption = new DES();

        while (true) {
            
            DatagramPacket dataPacket = new DatagramPacket(new byte[16], 16);
            ds.receive(dataPacket);
            byte[] data = dataPacket.getData();
            String encryptedMessage = new String(data);
            //System.out.println("Datapacket : "  + Arrays.toString(data));
            //String encryptedMessage = byteDataToString(received);
            System.out.println("Message Rec : " + encryptedMessage);
//            String MAC = encryptedMessage.substring(encryptedMessage.length()-9, encryptedMessage.length() - 1);
//            System.out.println("MAC : " + MAC);
//            encryptedMessage = encryptedMessage.substring(0, encryptedMessage.length() - 1);
//            System.out.println("Encrypted Message on server : " + encryptedMessage);
            String message = messageDecryption.decrypt(encryptedMessage, key);
            System.out.println("Client:- " + message);
//            if (message.equals("tata")) {
//                System.out.println("Client sent tata.....EXITING");
//                break;
//            }
        }
    }
}
