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
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Scanner;

public class Diffie_Server {

    /**
     * @param args the command line arguments
     */
    private static Socket socket;
    public static void main(String[] args) {
        // TODO code application logic here
        try
        {
 
            int port = 25000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 25000");
 
            //Server is running always. This is done using this while(true) loop
            while(true)
            {
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                Scanner sc=new Scanner(System.in);
                System.out.println("Enter challenge to send to client");
                String schallenge=sc.nextLine();
                String cc=br.readLine();
                System.out.println("client challenge recieved : "+cc);
                System.out.println("Sending challenge to client");
                bw.write(schallenge+"\n");
                bw.flush();
                String key="mnbvcxzlkjhgfdsapoiuytrewq";
                StringBuilder encryptCC=new StringBuilder();
                for(int i=0;i<cc.length();i++){
                    encryptCC.append((char)((cc.charAt(i) ^ key.charAt(i % key.length()))+20));
                }                
                System.out.println("Sending Response to client"+encryptCC.toString());
                
                bw.write(encryptCC.toString()+"\n");
                bw.flush();
                String encryptedSC=br.readLine();
                System.out.println("Response recieved from client : "+encryptedSC);
                StringBuilder SC=new StringBuilder();
                for(int i=0;i<encryptedSC.length();i++){
                    SC.append((char)((encryptedSC.charAt(i)-20) ^ key.charAt(i % key.length())));
                }
                //System.out.println("SC "+SC);
                if(schallenge.equalsIgnoreCase(SC.toString())){
                    System.out.println("client Authenticated");
                }
                else{
                    System.out.println("client not Authenticated");
                    return;
                }
                System.out.println("\nNow Diffie Hellman Key Exchange");
                BigInteger p=new BigInteger("7243469162906133262707138361729247674528418426076702186281286038623238274842547507072974617594640311");
                BigInteger alpha=new BigInteger("3242736143229285405697273596419677873912657748731448981302390864459158863881443495029809033284732127");
                SecureRandom r = new SecureRandom();
                BigInteger b=new BigInteger(80,100,r);
                System.out.println("Your Private key : "+b);
                BigInteger Ephimeral_Server=alpha.modPow(b, p);
                String publicServer=Ephimeral_Server.toString();
                String publicClient=br.readLine();
                bw.write(publicServer+"\n");
                bw.flush();
                
                BigInteger Ephimeral_Client=new BigInteger(publicClient);
                BigInteger Masking_Server=Ephimeral_Client.modPow(b, p);
                System.out.println("Key for Server : "+Masking_Server);
                System.out.println("Diifie Hellman Key Exchange Successful");  
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }
}
