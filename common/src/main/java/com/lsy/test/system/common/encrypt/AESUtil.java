package com.lsy.test.system.common.encrypt;

import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.apache.commons.codec.binary.Base64;

/**
 * @author lsy
 * @since 2022/6/2 10:02:13
 */
public class AESUtil {

    /**
     * AES随机秘钥
     * @return
     */
    public static String genAesSecret(int length){
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            //下面调用方法的参数决定了生成密钥的长度，可以修改为128, 192或256
            kg.init(length);
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            String secret = Base64.encodeBase64String(b);
            return secret;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("没有此算法");
        }
    }
}
