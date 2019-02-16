package ciphers;

import java.util.*;

public class PlayFair {

    private final String alphabets;
    private int messageLength;
    private String key;

    public PlayFair(String key, int length) {
        alphabets = "abcdefghiklmnopqrstuvwxyz";
        this.messageLength = length;
        this.key = key;
    }

    private String generateMatrix(String key) {
        String matrixString = key + alphabets;
        Set set = new LinkedHashSet();
        StringBuilder mat = new StringBuilder();

        for (char c : matrixString.toCharArray()) {
            set.add(c);
        }
        set.forEach((c) -> {
            mat.append(c);
        });
        return mat.toString();
    }

    private void printMatrix(String matrix) {
        System.out.print("Generated matrix : \n");
        char chars[] = matrix.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i % 5 == 0 & i != 0) {
                System.out.println("");
            }
            System.out.print(chars[i]);
        }
    }

    public String encrypt(String message, String key) {
        message = formatMessage(message);
        String matrix = this.generateMatrix(key.replace(" ", ""));
        message = message.toLowerCase().replace("j", "i");

        String[] pairs = message.split("(?<=\\G.{2})");
        StringBuilder cipherText = new StringBuilder();

        for (String pair : pairs) {
            //System.out.println("Pair  :" + pair);
            byte row1 = (byte) (matrix.indexOf(pair.charAt(0)) / 5);
            byte col1 = (byte) (matrix.indexOf(pair.charAt(0)) % 5);
            byte row2 = (byte) (matrix.indexOf(pair.charAt(1)) / 5);
            byte col2 = (byte) (matrix.indexOf(pair.charAt(1)) % 5);
            //System.out.println("char 1 " + pair.charAt(0) + "  at " + row1 + "   " + col1);
            //System.out.println("char 2 " + pair.charAt(1) + "  at " + row2 + "   " + col2);

            char char1;
            char char2;
            if (col1 == col2) {
                char2 = matrix.charAt(((row2 + 1) % 5 * 5 + col2));
                char1 = matrix.charAt(((row1 + 1) % 5 * 5 + col1));
            } else if (row1 == row2) {
                char1 = matrix.charAt(row1 * 5 + ((col1 + 1) % 5));
                char2 = matrix.charAt(row2 * 5 + ((col2 + 1) % 5));
            } else {
                char1 = matrix.charAt(row1 * 5 + col2);
                char2 = matrix.charAt(row2 * 5 + col1);
            }
            cipherText.append(Character.toString(char1) + Character.toString(char2));
            //System.out.println("Char1  : " + char1);
            //System.out.println("Char 2 : " + char2);
        }
        return cipherText.toString();
    }

    public String decrypt(String cipherText, String key) {
        String matrix = this.generateMatrix(key.replace(" ", ""));
//        System.out.println("Cipher Text as on decrypt func : " + cipherText);
//        System.out.println("Key as on decrypt func : " + key);
//        printMatrix(matrix);
        String[] pairs = cipherText.split("(?<=\\G.{2})");
        StringBuilder message = new StringBuilder();

        for (String pair : pairs) {
            //System.out.println("Pair  :" + pair);
            byte row1 = (byte) (matrix.indexOf(pair.charAt(0)) / 5);
            byte col1 = (byte) (matrix.indexOf(pair.charAt(0)) % 5);
            byte row2 = (byte) (matrix.indexOf(pair.charAt(1)) / 5);
            byte col2 = (byte) (matrix.indexOf(pair.charAt(1)) % 5);
            //System.out.println("char 1 " + pair.charAt(0) + "  at " + row1 + "   " + col1);
            //System.out.println("char 2 " + pair.charAt(1) + "  at " + row2 + "   " + col2);

            char char1 = 0;
            char char2 = 0;
            if (col1 == col2) {
                if(row2 == 0) row2 = (byte) (row2 + 5);
                if(row1 == 0) row1 = (byte) (row1 + 5);
                char2 = matrix.charAt(( (row2 - 1) % 5 * 5 + col2));
                char1 = matrix.charAt(( (row1 - 1) % 5 * 5 + col1));
            } else if (row1 == row2) {
                if ((col2 - 1) >= 0 && (col1 - 1) >= 0) {
                    char1 = matrix.charAt(row1 * 5 + ((col1 - 1) % 5));
                    char2 = matrix.charAt(row2 * 5 + ((col2 - 1) % 5));
                } else if ((col2 - 1) < 0) {
                    char2 = matrix.charAt((row2 + 1) * 5 + ((col2 - 1) % 5));
                } else if ((col1 - 1) < 0) {
                    char1 = matrix.charAt((row1 + 1) * 5 + ((col1 - 1) % 5));
                }

            } else {
                char1 = matrix.charAt(row1 * 5 + col2);
                char2 = matrix.charAt(row2 * 5 + col1);
            }
            //System.out.println("Char1  : " + char1);
            //System.out.println("Char 2 : " + char2);
            message.append(Character.toString(char1) + Character.toString(char2));
        }
        return message.toString();
    }

    public void setMessageLength(int length) {
        this.messageLength = length;
    }

    private String formatMessage(String message) {
        int toAppend = 0;
        if (message.length() % 2 != 0) {
            toAppend = 2 - (message.length() % 2);
        }
        for (int i = 0; i < toAppend; i++) {
            message += 'x';
        }
        return message;
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Key : ");
        String key = sc.next();
        System.out.println("Enter word to encrypt: (Make sure length of message is even)");
        String message = sc.next();

        PlayFair playfair = new PlayFair(key, message.length());

        //playfair.printMatrix(matrix);
        String cipherText = playfair.encrypt(message, key);
        System.out.println("\nEncryption: " + cipherText);

        String decryptMessage = playfair.decrypt(cipherText, key);
        System.out.println("\nDecrypted : " + decryptMessage);

        sc.close();

    }
}
