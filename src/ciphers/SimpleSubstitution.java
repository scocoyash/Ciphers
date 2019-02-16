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
public class SimpleSubstitution {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Message : (in lower case)");
        String message = sc.next();

        String key = "TIMEODANSFRBCGHJKLPQUVWXYZ";
        System.out.println("Key : " + key);
        String encrypted = encrypt(message.toUpperCase(), key);
        System.out.println("Encrypted : " + encrypted);
        String decrypted = decrypt(encrypted, key);
        System.out.println("Decrypted : " + decrypted);
    }

    public static String encrypt(String message, String key) {
        String cipherText = "";
        String[] keySpace = new String[key.length()];
        for (int i = 0; i < key.length(); i++) {
            keySpace[i] = String.valueOf(key.charAt(i));
        }

        for (int i = 0; i < message.length(); i++) {
            int index = message.charAt(i) - 65;
            if (index > keySpace.length || index < 0) {
                cipherText += String.valueOf(message.charAt(i));
            } else {
                cipherText += keySpace[index];
            }
        }
        return cipherText;
    }

    public static String decrypt(String cipherText, String key) {
        String message = "";

        for (int i = 0; i < cipherText.length(); i++) {
            char character = cipherText.charAt(i);
            int index = key.indexOf(character);
            int ascii = index + 65;
            if (ascii < 65 || ascii > 90) {
                message += String.valueOf(character);
            } else {
                message += String.valueOf((char) ascii);
            }
        }
        return message;
    }
}
