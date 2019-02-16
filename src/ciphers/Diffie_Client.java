/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ciphers;

/**
 *
 * @author Yash Jain
 */
import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Scanner;

public class Diffie_Client {

    /**
     * @param args the command line arguments
     */
    private static Socket socket;
    public static void main(String[] args) {
        // TODO code application logic here
        try
        {
            String host = "localhost";
            int port = 25000;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
            //Send the message to the server
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter challenge to send to server");
            String cc=sc.nextLine();
            System.out.println("Sending challenge to Server");
            bw.write(cc+"\n");
            bw.flush();
            String schallenge=br.readLine();
            System.out.println("Server challenge recieved : "+schallenge);
            String key="mnbvcxzlkjhgfdsapoiuytrewq";
            StringBuilder encryptSC=new StringBuilder();
            for(int i=0;i<schallenge.length();i++){
                encryptSC.append((char)((schallenge.charAt(i) ^ key.charAt(i % key.length()))+20));
            }
            System.out.println("Sending Response to server : "+encryptSC.toString());
            String encryptedCC=br.readLine();
            bw.write(encryptSC.toString()+"\n");
            bw.flush();
            System.out.println("Response recieved from server : "+encryptedCC);
            StringBuilder CC=new StringBuilder();
            for(int i=0;i<encryptedCC.length();i++){
                CC.append((char)((encryptedCC.charAt(i)-20) ^ key.charAt(i % key.length())));
            }
            if(cc.equalsIgnoreCase(CC.toString())){
                System.out.println("Server Authenticated");
            }
            else{
                System.out.println("Server not authenticated");
                return;
            }
            System.out.println("\nNow Diffie Hellman Key Exchange");
            BigInteger p=new BigInteger("7243469162906133262707138361729247674528418426076702186281286038623238274842547507072974617594640311");
            BigInteger alpha=new BigInteger("3242736143229285405697273596419677873912657748731448981302390864459158863881443495029809033284732127");
            SecureRandom r = new SecureRandom();
            BigInteger a=new BigInteger(80,100,r);
            System.out.println("Your Private key : "+a);
            BigInteger Ephimeral_Client=alpha.modPow(a, p);
            String publicClient=Ephimeral_Client.toString();
            bw.write(publicClient+"\n");
            bw.flush();
            String publicServer=br.readLine();
            BigInteger Ephimeral_Server=new BigInteger(publicServer);
            BigInteger Masking_Client=Ephimeral_Server.modPow(a, p);
            System.out.println("Key for Client : "+Masking_Client);
            System.out.println("Diifie Hellman Key Exchange Successful");

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            //Closing the socket
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
}
