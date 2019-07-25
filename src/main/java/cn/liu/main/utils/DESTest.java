package cn.liu.main.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * created by liufeng
 * 2018/11/29
 */
public class DESTest {
    public static void main(String[] args) {
        //des加密是对称加密
        String mingwen = "answer";
        //密钥必须是24位
        String password = "1234567891011121314rrtty";
        String miwen = encrypt(mingwen, password);
        System.out.println("加密：" + miwen);

        String jiemi = dencrypt(miwen, password);
        System.out.println("解密：" + jiemi);
    }

    /**
     * 加密
     *
     * @param string   明文
     * @param password 密钥
     * @return
     */
    public static String encrypt(String string, String password) {
        try {
            // 生成密钥
            SecretKey securityKey = new SecretKeySpec(password.getBytes(), "DESede");
            //加密
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, securityKey);
            byte[] data = cipher.doFinal(string.getBytes("utf-8"));
            return Base64Util.encode(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //解密
    public static String dencrypt(String string, String password) {
        try {
            // 生成密钥
            SecretKey securityKey = new SecretKeySpec(password.getBytes(), "DESede");
            //加密
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, securityKey);
            byte[] data = cipher.doFinal(Base64Util.decode(string.toCharArray()));
            return new String(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
