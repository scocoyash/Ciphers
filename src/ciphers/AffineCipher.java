/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ciphers;

import java.util.Scanner;

/**
 *
 * @author Yash Jain
 */
public class AffineCipher {
    private int a;
    private int b;
    public AffineCipher(){
        this.a = 10;
        this.b = 15;
    }
    public AffineCipher(int a,int b){
        this.a = a;
        this.b = b ;
    }
    
    private int getAlphaValue(char c){
        return (int)c - 'a';
    }
    
    private String encrypt(String message){
        StringBuilder cipherText = new StringBuilder();
        for(char c : message.toCharArray()){
            int x = (getAlphaValue(c) * a + b )%26 + 'A';
            cipherText.append((char)x);
        }
        return cipherText.toString();
    }
    
    private String decrypt(String cipherText){
        StringBuilder message = new StringBuilder();
        int c = 26 - this.a;
        
        for(char ch : cipherText.toLowerCase().toCharArray()){
            int x = c*(getAlphaValue(ch) - b);
            if(x >= 0){
                x = x % 26 + 'A';
            }else{
                x = Math.abs((Math.abs(x)%26)-26)%26 + 'A';
            }
            message.append((char)x);
        }
        return message.toString();
    }
    
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter values of a and b : ");
        int a = sc.nextInt();
        int b = sc.nextInt();
        System.out.println("Enter word to encrypt: (Make sure length of message is even)");
        String message = sc.next();
        message = message.toLowerCase();
        
        AffineCipher affineCipher = new AffineCipher(a,b);
        String cipherText = affineCipher.encrypt(message);
        System.out.println("Encryption: " + cipherText);

        String decryptMessage = affineCipher.decrypt(cipherText);
        System.out.println("Decrypted : " + decryptMessage);
        sc.close();

    }
}
