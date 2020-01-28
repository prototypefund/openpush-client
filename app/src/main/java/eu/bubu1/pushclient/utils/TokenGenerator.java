package eu.bubu1.pushclient.utils;

import android.util.Base64;

import java.security.SecureRandom;

public class TokenGenerator {
    public static String getToken(Integer size) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return Base64.encodeToString(bytes, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
    }
}
