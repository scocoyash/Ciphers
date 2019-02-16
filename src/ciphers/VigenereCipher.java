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
public class VigenereCipher {
    public VigenereCipher(){}
    
    private String encrypt(String str, String key) {
        String cipherText = "";
        
        for (int i = 0; i < str.length(); i++) {
            int x = (str.charAt(i) + key.charAt(i%key.length()))% 26;
            x += 'A';
            cipherText += (char) x;
        }
        return cipherText;
    }
    
    private String decrypt(String cipherText, String key){
        String message = "";
        
        for(int i = 0; i < cipherText.length();i++){
            int x = (cipherText.charAt(i) - key.charAt(i%key.length()) + 26)%26;
            //System.out.println("x  :" + x);
            x += 'A';
            message += (char) x;
        }
        return message;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        VigenereCipher cipher = new VigenereCipher();
        System.out.println("Enter Message : ");
        String message = sc.next();
        System.out.println("Enter Key : ");
        String key = sc.next();
        message = message.toUpperCase();
        key = key.toUpperCase();
        
        String encrypted = cipher.encrypt(message, key);
        System.out.println("Encrypted Text: " + encrypted);

        String decrypted = cipher.decrypt(encrypted, key);
        System.out.println("Decoded Message : " + decrypted);

    }

}
