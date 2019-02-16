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
public class RailFenceCipher {

    private StringBuilder encryptedMessage;
    private StringBuilder message;
    private int key;

    public RailFenceCipher(String message, int key) {
        this.encryptedMessage = new StringBuilder();
        this.message = new StringBuilder(message);
        this.key = key;
    }

    private String encrypt() {
        for (int i = 0; i < this.key; i++) {
            for (int j = i; j < this.message.length(); j += this.key) {
                this.encryptedMessage.append(message.charAt(j));
            }
        }
        return this.encryptedMessage.toString();
    }

    private String decrypt() {
        StringBuilder result = new StringBuilder();
        int skip = this.encryptedMessage.length() / this.key;
        for (int i = 0; i < skip; i++) {
            for (int j = i; j < encryptedMessage.length(); j += skip) {
                result.append(encryptedMessage.charAt(j));
            }
        }
        return result.toString();
    }

    private void formatMessage() {
        int toAppend = 0;
        if (this.message.length() % this.key != 0) {
            toAppend = this.key - (this.message.length() % this.key);
        }

        for (int i = 0; i < toAppend; i++) {
            this.message.append('x');
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter word to encrypt: (Make sure length of message is even)");
        String message = sc.next();
        System.out.println("Enter Key : ");
        int key = sc.nextInt();
        message = message.toLowerCase();

        RailFenceCipher railFenceCipher = new RailFenceCipher(message, key);
        railFenceCipher.formatMessage();
        String cipherText = railFenceCipher.encrypt();
        System.out.println("Encryption: " + cipherText);

        String decryptMessage = railFenceCipher.decrypt();
        System.out.println("Decrypted : " + decryptMessage);
        sc.close();

    }
}
