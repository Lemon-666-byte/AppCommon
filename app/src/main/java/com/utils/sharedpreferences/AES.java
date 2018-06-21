package com.utils.sharedpreferences;

import java.io.ByteArrayOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Yang on 2018/2/2.
 */

public class AES {
    private static final boolean DBG = true;
    private static final String TAG = AES.class.getSimpleName();
    private static final int MAX_DECRYPT_BLOCK = 128;

    public AES() {
    }

    public static String Encrypt(String sKey, String sSrc) throws Exception {
//        LogUtil.i(true, TAG, "Encrypt key:" + sKey + "  src:" + sSrc);
        if(sKey == null) {
//            LOG.e(true, TAG, "Key为空null");
            return null;
        } else {
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/ZeroBytePadding");
            cipher.init(1, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            return android.util.Base64.encodeToString(encrypted, 2);
        }
    }

    public static String Decrypt(String sKey, String sSrc) throws Exception {
        if(sKey == null) {
//            LOG.e(true, TAG, "Key为空null");
            return null;
        } else {
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/ZeroBytePadding");
            cipher.init(2, skeySpec);
            byte[] encrypted1 = android.util.Base64.decode(sSrc, 2);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "utf-8");
        }
    }

    public static byte[] segmentedEncryptedData(String sKey, byte[] encrypted) throws Exception {
        int inputLen = encrypted.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/ZeroBytePadding");
        cipher.init(2, skeySpec);

        while(inputLen - offSet > 0) {
            byte[] cache;
            if(inputLen - offSet > 128) {
                cache = cipher.doFinal(encrypted, offSet, 128);
            } else {
                cache = cipher.doFinal(encrypted, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
            offSet = i * 128;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
}
