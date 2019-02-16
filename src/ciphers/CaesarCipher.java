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
public class CaesarCipher {
    
    public CaesarCipher(){
        
    }
    
    public String encrypt(String str, int keyLength) {
        String encrypted = "";
        for (int i = 0; i < str.length(); i++) {
            int c = str.charAt(i);

            if (Character.isUpperCase(c)) {
                //26 letters of the alphabet so mod by 26
                c = c + (keyLength % 26);
                if (c > 'Z') {
                    c = c - 26;
                }
            } else if (Character.isLowerCase(c)) {
                c = c + (keyLength % 26);
                if (c > 'z') {
                    c = c - 26;
                }
            }
            encrypted += (char) c;
        }
        return encrypted;
    }
    
     public String decrypt(String str, int keyLength) {
        String decrypted = "";
        for (int i = 0; i < str.length(); i++) {
            int c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                c = c - (keyLength % 26);
                if (c < 'A') {
                    c = c + 26;
                }
            } else if (Character.isLowerCase(c)) {
                c = c - (keyLength % 26);
                if (c < 'a') {
                    c = c + 26;
                }
            }
            decrypted += (char) c;
        }
        return decrypted;
    }
     
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Message : ");
        String message = sc.next();
        System.out.println("Enter Key : ");
        int key = sc.nextInt();
        
        CaesarCipher cipher = new CaesarCipher();
        String encrypted = cipher.encrypt(message, key);
        System.out.println("Encrypted : " + encrypted);

        String decrypted = cipher.decrypt(encrypted, key);
        System.out.println("Decrypted : " + decrypted);

        System.out.println("Key:" + key);

    }
}
