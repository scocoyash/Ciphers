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
public class ShiftCipher {
	private int key;
	
	public void setKey(int k)
	{
		key = k;
	}
	
	public String encode(String s)
	{
		int len = s.length();
		StringBuffer result = new StringBuffer();
		
		for(int i=0 ; i<len ; i++)
		{
			char c = (char)(((int)s.charAt(i) + key - 65)%26 + 65);
			result = result.append(c);
		}
		return result.toString();
	}
	
	public String decode(String s)
	{
		int len = s.length();
		StringBuffer result = new StringBuffer();
		int dkey = 26 - key;
		for(int i=0 ; i<len ; i++)
		{
			char c = (char)(((int)s.charAt(i) + dkey - 65)%26 + 65);
			result = result.append(c);
		}
		return result.toString();
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ShiftCipher cipher = new ShiftCipher();
		
		System.out.println("Enter string to encode:");
		String text = br.readLine();
		System.out.println("Enter key for encryption:");
		int k = Integer.parseInt(br.readLine());
		cipher.setKey(k);
		System.out.println("The Plain Text Is : " + text);
		System.out.println("The Cipher Text Is : " + cipher.encode(text.toUpperCase()));
		
		System.out.println("\nEnter string to decode:");
		text = br.readLine();
		System.out.println("Enter key for decryption:");
		k = Integer.parseInt(br.readLine());
		cipher.setKey(k);
		System.out.println("The Cipher Text Is : " + text);
		System.out.println("The Plain Text Is : " + cipher.decode(text.toUpperCase()));
		
		br.close();
	}
}
