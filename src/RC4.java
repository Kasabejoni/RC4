

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class RC4 {

    static byte[] S = new byte[256];
    static byte[] S1 = new byte[256];
    static byte[] T = new byte[256];
    static String keytoprint;

    public RC4(String keyString) {

        if (keyString.length() < 1 && keyString.length() > 256) {
            throw new IllegalArgumentException("Key length should be in between 1 and 256");
        }

        this.keytoprint = keyString;

        byte[] tempKey = keyString.getBytes(); //convert string to byte array
        byte[] key = new byte[tempKey.length];
        int keyLength = tempKey.length;

        for (int i = 0; i < keyLength; i++) {
            key[i] = (byte) (tempKey[i] & 0xff);
        }
        InitPer(key);

    }

    public void InitPer(byte[] key) {
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
            T[i]= key[i % key.length];
        }
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + T[i]) & 0xFF;

            swap(S, i, j);

        }
        System.arraycopy(S, 0, S1, 0, S.length);

    }

    public byte[] PRG(int length) {
        System.arraycopy(S, 0, S1, 0, S.length);
        int i = 0, j = 0;
        byte[] tempPpad = new byte[length];
        for (int k = 0; k < length; k++) {
            i = (i + 1) & 0xFF;
            j = (j + S1[i]) & 0xFF;
            swap(S1, i, j);


            tempPpad[k] = (byte) (S1[(S1[i] + S1[j]) & 0xFF]);
        }
        return tempPpad;
    }

    public byte[] encrypt(byte[] plain) {
        byte[] pad = PRG(plain.length);
        byte[] encrypt = new byte[plain.length];
        for (int i = 0; i < plain.length; i++) {
            encrypt[i] = (byte) (plain[i] ^ pad[i]);
        }
        return encrypt;
    }





    public static void swap(byte[] S, int i, int j) //swap func
    {

        byte temp;
        temp = S[i];
        S[i] = S[j];
        S[j] = temp;
    }
}
