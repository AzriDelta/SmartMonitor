package com.example.azri.smartmonitor;

import tgio.rncryptor.RNCryptorNative;

public class dataCrypt {


    String password = "accel";
    //String rawUsername = "delta", rawPassword = "delta12345";

    private RNCryptorNative rncryptor = new RNCryptorNative();

    String encryptAndPush(String plainText){

        return new String(rncryptor.encrypt(plainText, password));
    }

    String decryptAndRetrieve(String cipherText){

        return rncryptor.decrypt(cipherText, password);
    }


}
