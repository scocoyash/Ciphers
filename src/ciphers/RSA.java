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
import java.util.*;

public class RSA {

    private int p, q, d;
    private PublicKey pub;

    class PublicKey {

        int n, e;

        PublicKey(int n, int e) {
            this.n = n;
            this.e = e;
        }
    }

    private void setKey() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter p :");
        p = sc.nextInt();
        System.out.println("Enter q :");
        q = sc.nextInt();
        generateKeys();

    }

    private int gcd(int a, int b) {
        if (b != 0) {
            return gcd(b, a % b);
        } else {
            return a;
        }
    }

    public void generateKeys() {
        int phi = (p - 1) * (q - 1);
        //System.out.println("phi : "+phi);
        for (int i = 2; i < phi; i++) {
            if (gcd(phi, i) == 1) {
                pub = new PublicKey(p * q, i);
                break;
            }

        }
        System.out.println("public key (" + pub.n + "," + pub.e + ")");

        for (int i = 1; i < phi; i++) {
            if ((i * pub.e) % phi == 1) {
                d = i;
                break;
            }
        }
        System.out.println("private key : " + d);

    }

    public int encrypt(int P) {
        int result = 1;
        for (int i = 0; i < pub.e; i++) {
            result = (result * P) % pub.n;
        }
        return result;
    }

    public int decrypt(int C) {
        int result = 1;
        for (int i = 0; i < d; i++) {
            result = (result * C) % pub.n;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {

        RSA cipher = new RSA();

        cipher.setKey();
        Scanner br = new Scanner(System.in);
        System.out.println("Enter plain text : ");
        int pt;
        pt = br.nextInt();
        int ct = cipher.encrypt(pt);
        System.out.println("Encrypted text : " + ct);

        System.out.println("Decrypted Plain Text :" + cipher.decrypt(ct));
        br.close();
    }
}
