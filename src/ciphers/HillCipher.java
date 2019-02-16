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
public class HillCipher {

    private final String alphabets;
    private String key;
    private int messageLength;

    public HillCipher(String key, int length) {
        alphabets = "abcdefghiklmnopqrstuvwxyz";
        this.key = key;
        this.messageLength = length;
    }

    private int[] generateMatrix(String key) {
        int[] matrix = new int[4];
        int i = 0;
        for (char c : key.toCharArray()) {
            matrix[i++] = getAlphaValue(c);
        }
        return matrix;
    }

    private int getAlphaValue(char c) {
        return (int) c - 'a';
    }

    public String encrypt(String message, String key) {
        int[] matrix = generateMatrix(key.substring(0, 4));
        message = formatMessage(message, this.key);
        String[] pairs = message.split("(?<=\\G.{2})");
        StringBuilder cipherText = new StringBuilder();

        for (String pair : pairs) {
            char c1 = pair.charAt(0);
            char c2 = pair.charAt(1);

            char char1 = (char) ((matrix[0] * getAlphaValue(c1) + matrix[1] * getAlphaValue(c2)) % 26 + 'a');
            char char2 = (char) ((matrix[2] * getAlphaValue(c1) + matrix[3] * getAlphaValue(c2)) % 26 + 'a');

            cipherText.append(Character.toString(char1)).append(Character.toString(char2));
        }
        return cipherText.toString();
    }
    //for others
    public String encrypt(String message, int[] matrix) {
        message = formatMessage(message, this.key);
        String[] pairs = message.split("(?<=\\G.{2})");
        StringBuilder cipherText = new StringBuilder();

        for (String pair : pairs) {
            char c1 = pair.charAt(0);
            char c2 = pair.charAt(1);

            char char1 = (char) ((matrix[0] * getAlphaValue(c1) + matrix[1] * getAlphaValue(c2)) % 26 + 'a');
            char char2 = (char) ((matrix[2] * getAlphaValue(c1) + matrix[3] * getAlphaValue(c2)) % 26 + 'a');

            cipherText.append(Character.toString(char1)).append(Character.toString(char2));
        }
        return cipherText.toString();
    }

    private int getMultiplicativeInverse(int[] matrix) {
        int determinant = (matrix[0] * matrix[3] - matrix[1] * matrix[2]) % 26;
        if (determinant < 0) {
            determinant = determinant + 26;
        }
        int mi = 0;
        for (int i = 0;i<256; i++) {
            if ((i * determinant) % 26 == 1) {
                mi = i;
                break;
            }
        }
        return mi;
    }
    
    private int[] getAdjointMatrix(int[] matrix) {
        int[] adjoint = new int[matrix.length];
        adjoint[0] = matrix[3];
        adjoint[3] = matrix[0];
        adjoint[1] = -matrix[1];
        adjoint[2] = -matrix[2];
        for (int i = 0; i < adjoint.length; i++) {
            if (adjoint[i] < 0) {
                adjoint[i] += 26;
            }
        }
        return adjoint;
    }

    private int[] getInverseMatrix(int inverse, int[] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = (matrix[i] * inverse) % 26;
        }
        return matrix;
    }

    public String decrypt(String cipherText, String key) {
        int[] matrix = generateMatrix(key.substring(0, 4));
        System.out.println("Finding inverse : text  : key :" + cipherText + " " + key);
        int inverse = getMultiplicativeInverse(matrix);
        //System.out.println("Finding adjoint : " + inverse);
        int[] adjoint = getAdjointMatrix(matrix);
        //System.out.println("Finding inverseMatrix");
        int[] inverseMatrix = getInverseMatrix(inverse, adjoint);
        //System.out.println("Calling encrypt from decrypt");
        for(int a: inverseMatrix){
            System.out.println("Inverse Matrix : " + a);
        }
        return this.encrypt(cipherText, inverseMatrix).substring(0, this.messageLength);
    }

    private String formatMessage(String message, String key) {
        int toAppend = 0;
        if (message.length() % key.length() != 0) {
            toAppend = key.length() - (message.length() % key.length());
        }
        for (int i = 0; i < toAppend; i++) {
            message += 'x';
        }
        return message;
    }
    
    public void setMessageLength(int length){
        this.messageLength = length;
    }
    
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Key : ");
        String key = sc.next();
        key = key.toLowerCase();
        System.out.println("Enter word to encrypt: (Make sure length of message is even)");
        String message = sc.next();
        message = message.toLowerCase();

        HillCipher hillCipher = new HillCipher(key, message.length());

        String cipherText = hillCipher.encrypt(message, key);
        System.out.println("Encryption: " + cipherText);

        String decryptMessage = hillCipher.decrypt(cipherText, key);
        System.out.println("Decrypted : " + decryptMessage);
        sc.close();

    }
}
