package ciphers;
import java.util.*;
/**
 *
 * @author Yash Jain
 */
public class DES {

    private static final byte[] IP = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };

    private static final byte[] FP = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
    };

    private static final byte[] E = {
        32, 1, 2, 3, 4, 5,
        4, 5, 6, 7, 8, 9,
        8, 9, 10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1
    };

    private static final byte[][] S = {{
        14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7,
        0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
        4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
        15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13
    }, {
        15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10,
        3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
        0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
        13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9
    }, {
        10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8,
        13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
        13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
        1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12
    }, {
        7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15,
        13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
        10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
        3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14
    }, {
        2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9,
        14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
        4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
        11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3
    }, {
        12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11,
        10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
        9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
        4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13
    }, {
        4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1,
        13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
        1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
        6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12
    }, {
        13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7,
        1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
        7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
        2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
    }};

    private static final byte[] P = {
        16, 7, 20, 21,
        29, 12, 28, 17,
        1, 15, 23, 26,
        5, 18, 31, 10,
        2, 8, 24, 14,
        32, 27, 3, 9,
        19, 13, 30, 6,
        22, 11, 4, 25
    };

    private static final byte[] PC1 = {
        57, 49, 41, 33, 25, 17, 9,
        1, 58, 50, 42, 34, 26, 18,
        10, 2, 59, 51, 43, 35, 27,
        19, 11, 3, 60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7, 62, 54, 46, 38, 30, 22,
        14, 6, 61, 53, 45, 37, 29,
        21, 13, 5, 28, 20, 12, 4
    };

    private static final byte[] PC2 = {
        14, 17, 11, 24, 1, 5,
        3, 28, 15, 6, 21, 10,
        23, 19, 12, 4, 26, 8,
        16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32
    };

    private static final byte[] rotations = {
        1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };

    private static String asciiToHex(String asciiValue) {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString();
    }

    private static String hexToASCII(String hexValue) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexValue.length(); i += 2) {
            String str = hexValue.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    private static long IP(long src) {
        return permute(IP, 64, src);
    } // 64-bit output

    private static long FP(long src) {
        return permute(FP, 64, src);
    } // 64-bit output

    private static long E(int src) {
        return permute(E, 32, src & 0xFFFFFFFFL);
    } // 48-bit output

    private static int P(int src) {
        return (int) permute(P, 32, src & 0xFFFFFFFFL);
    } // 32-bit output

    private static long PC1(long src) {
        return permute(PC1, 64, src);
    } // 56-bit output

    private static long PC2(long src) {
        return permute(PC2, 56, src);
    } // 48-bit output

    private static long permute(byte[] table, int srcWidth, long src) {
        long dst = 0;
        for (int i = 0; i < table.length; i++) {
            int srcPos = srcWidth - table[i];
            dst = (dst << 1) | (src >> srcPos & 0x01);
        }
        return dst;
    }

    private static byte S(int boxNumber, byte src) {
        src = (byte) (src & 0x20 | ((src & 0x01) << 4) | ((src & 0x1E) >> 1));
        return S[boxNumber - 1][src];
    }

    private static long getLongFromBytes(byte[] ba, int offset) {
        long l = 0;
        for (int i = 0; i < 8; i++) {
            byte value;
            if ((offset + i) < ba.length) {
                value = ba[offset + i];
            } else {
                value = 0;
            }
            l = l << 8 | (value & 0xFFL);
        }
        return l;
    }

    private static void getBytesFromLong(byte[] ba, int offset, long l) {
        for (int i = 7; i >= 0; i--) {
            if ((offset + i) < ba.length) { 
                //only last 8 bits to be taken
                ba[offset + i] = (byte) (l & 0xFF);
                l = l >> 8;
            } else {
                break;
            }
        }
    }

    private static int feistel(int r, long subkey) {
        long e = E(r);
        long x = e ^ subkey;
        int dst = 0;
        for (int i = 0; i < 8; i++) {
            dst >>>= 4; /* Remember logical right shift */
            int s = S(8 - i, (byte) (x & 0x3F)); /*Get first 64 bits for S*/
            dst |= s << 28;
            x >>= 6;
        }
        return P(dst);
    }

    private static long[] createSubkeys(/* 64 bits */long key) {
        long subkeys[] = new long[16];

        key = PC1(key);
        int c = (int) (key >> 28);
        int d = (int) (key & 0x0FFFFFFF);

        for (int i = 0; i < 16; i++) {
            // rotate the 28-bit values
            if (rotations[i] == 1) {
                c = ((c << 1) & 0x0FFFFFFF) | (c >> 27);
                d = ((d << 1) & 0x0FFFFFFF) | (d >> 27);
            } else {
                c = ((c << 2) & 0x0FFFFFFF) | (c >> 26);
                d = ((d << 2) & 0x0FFFFFFF) | (d >> 26);
            }

            long cd = (c & 0xFFFFFFFFL) << 28 | (d & 0xFFFFFFFFL);
            subkeys[i] = PC2(cd);
        }

        return subkeys;
        /* 48-bit values */
    }

    public static long encryptBlock(long m, /* 64 bits */ long key) {
        long subkeys[] = createSubkeys(key);
        long ip = IP(m);

        int l = (int) (ip >> 32);
        int r = (int) (ip & 0xFFFFFFFFL);

        // perform 16 rounds
        for (int i = 0; i < 16; i++) {
            int previous_l = l;
            l = r;
            r = previous_l ^ feistel(r, subkeys[i]);
        }
        // reverse the two 32-bit segments (left to right; right to left)
        long rl = (r & 0xFFFFFFFFL) << 32 | (l & 0xFFFFFFFFL);
        long fp = FP(rl);
        return fp;
    }

    public static void encryptBlock(
            byte[] message,
            int messageOffset,
            byte[] ciphertext,
            int ciphertextOffset,
            byte[] key,
            long initVector
    ) {
        long m = getLongFromBytes(message, messageOffset);
        long k = getLongFromBytes(key, 0);
        m = m ^ initVector;
        long c = encryptBlock(m, k);
        getBytesFromLong(ciphertext, ciphertextOffset, c);
    }

    public static byte[] encrypt(byte[] message, byte[] key) {
        byte[] ciphertext = new byte[message.length];
        // encrypt each 8-byte (64-bit) block of the message.
        int k = 0;
        for (int i = 0; i < message.length; i += 8) {
            long initVector;
            if (i == 0) {
                initVector = 0;
            } else {
                byte[] temp = new byte[8];

                for (int j = 0; j < 8; j++) {
                    temp[j] = ciphertext[k++];
                }
//                for (int j = 0; j < temp.length; j++) {
//                    System.out.print(" Temp " + i + " : " + temp[j]);
//                }
//                System.out.println("");
                initVector = getLongFromBytes(temp, 0);
            }
            encryptBlock(message, i, ciphertext, i, key, initVector);
        }
        return ciphertext;
    }

    public static long decryptBlock(long m, /* 64 bits */ long key) {
        long subkeys[] = createSubkeys(key);
        long ip = IP(m);

        int l = (int) (ip >> 32);
        int r = (int) (ip & 0xFFFFFFFFL);

        // perform 16 rounds
        for (int i = 0; i < 16; i++) {
            int previous_l = l;
            l = r;
            r = previous_l ^ feistel(r, subkeys[15 - i]);
        }

        // reverse the two 32-bit segments (left to right; right to left)
        long rl = (r & 0xFFFFFFFFL) << 32 | (l & 0xFFFFFFFFL);
        long fp = FP(rl);
        return fp;
    }

    public static void decryptBlock(
            byte[] message,
            int messageOffset,
            byte[] ciphertext,
            int ciphertextOffset,
            byte[] key,
            long initVector
    ) {
        long m = getLongFromBytes(message, messageOffset);
        long k = getLongFromBytes(key, 0);
        long c = decryptBlock(m, k);
        c = c ^ initVector;
        getBytesFromLong(ciphertext, ciphertextOffset, c);
    }

    public static byte[] decrypt(byte[] message, byte[] key) {
        byte[] ciphertext = new byte[message.length];
        int k = 0;

        // encrypt each 8-byte (64-bit) block of the message.
        for (int i = 0; i < message.length; i += 8) {
            long initVector;
            if (i == 0) {
                initVector = 0;
            } else {
                byte[] temp = new byte[8];
                for (int j = 0; j < 8; j++) {
                    temp[j] = message[k++];
                }
//                for (int j = 0; j < temp.length; j++) {
//                    System.out.println("Temp : " + temp[j]);
//                }
                initVector = getLongFromBytes(temp, 0);
            }
            decryptBlock(message, i, ciphertext, i, key, initVector);
        }

        return ciphertext;
    }

    private static byte[] passwordToKey(String password) {
        byte[] pwbytes = password.getBytes();
        byte[] key = new byte[8];
        for (int i = 0; i < 8; i++) {
            if (i < pwbytes.length) {
                byte b = pwbytes[i];
                // flip the byte
                byte b2 = 0;
                for (int j = 0; j < 8; j++) {
                    b2 <<= 1;
                    b2 |= (b & 0x01);
                    b >>>= 1;
                }
                key[i] = b2;
            } else {
                key[i] = 0;
            }
        }
        return key;
    }

    private static int charToNibble(char c) {
        if (c >= '0' && c <= '9') {
            return (c - '0');
        } else if (c >= 'a' && c <= 'f') {
            return (10 + c - 'a');
        } else if (c >= 'A' && c <= 'F') {
            return (10 + c - 'A');
        } else {
            return 0;
        }
    }

    private byte[] parseBytes(String s) {
        s = s.replace(" ", "");
        byte[] ba = new byte[s.length() / 2];

        if (s.length() % 2 > 0) {
            s = s + '0';
        }
        for (int i = 0; i < s.length(); i += 2) {
            ba[i / 2] = (byte) (charToNibble(s.charAt(i)) << 4 | charToNibble(s.charAt(i + 1)));
        }
        return ba;
    }

    private static String hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02X ", bytes[i]));
        }
        return sb.toString();
    }

//    public static boolean test(byte[] message, byte[] expected, String password) {
//        return test(message, expected, passwordToKey(password));
//    }
//
//    private static int testCount = 0;
//
//    public static boolean test(byte[] message, byte[] expected, byte[] key) {
//        System.out.println("Test #" + (++testCount) + ":");
//        System.out.println("\tkey:      " + hex(key));
//        System.out.println("\tmessage:  " + hex(message));
//        System.out.println("\texpected: " + hex(expected));
//        byte[] received = encrypt(message, key);
//        System.out.println("\treceived: " + hex(received));
//        byte[] decryptText = decrypt(received, key);
//        System.out.println("\tDecrypted : " + hex(decryptText));
//        System.out.println("\tDecrypted text: " + hexToASCII(new String(hex(decryptText)).replace(" ", "")));
//        boolean result = Arrays.equals(expected, received);
//        System.out.println("\tverdict: " + (result ? "PASS" : "FAIL"));
//        return result;
//    }
    private byte[] encryptedText;
    private byte[] decryptedText;
    private byte[] MAC;

    private void setMAC(byte[] cipherText) {
        MAC = new byte[8];
        int length = cipherText.length;
        for (int i = length - 1, j = 0; j < 8; i--, j++) {
            MAC[j] = cipherText[i];
        }
    }

    public String getMAC() {
        System.out.println("MAC : " + new String(MAC));
        System.out.println("MAC : " + MAC.toString());
        return new String(MAC);
    }

    public String encrypt(String message, String password) {
        String hex = asciiToHex(message);
        this.encryptedText = encrypt(parseBytes(hex), passwordToKey(password));
        setMAC(this.encryptedText);
        //getMAC();
        return hexToASCII(hex(encryptedText).replace(" ", ""));
    }

    public String decrypt(String message, String password) {
        String hex = asciiToHex(message);
        System.out.println("\tDec hex text : " + hex(parseBytes(hex)));
        System.out.println("\tMessage for decryption:  " + hex(this.encryptedText));
        this.decryptedText = decrypt(this.encryptedText, passwordToKey(password));
        return hexToASCII(hex(decryptedText).replace(" ", ""));
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Message : ");
        String message = sc.next();

        System.out.println("Enter Key : ");
        String key = sc.next();

        DES cipher = new DES();
        String encryptedText = cipher.encrypt(message, key);
        System.out.println("Encrypted Message : " + encryptedText);

        String decryptedText = cipher.decrypt(encryptedText, key);
        System.out.println("Decrypted Message : " + decryptedText);
        //byte[] decoded = Base64.getDecoder().decode(message);
        //message = asciiToHex(message);
        //key = "133457799BBCDFF1";

    }

}
