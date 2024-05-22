package io.github.maidsg.starter.voyage.util.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/*******************************************************************
 * <pre></pre>
 * @文件名称： TimeStampVerificationUtil.java
 * @包 路  径： io.github.maidsg.starter.voyage.util.encryption
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date： 2024/5/22 13:55
 * @Modify：
 */
public class TimeStampVerificationUtil {

    private static final String[] STAND = {"2", "3", "5", "7"};
    private static final String[] CLOCK = {"0", "1", "4", "6", "8", "9"};

    private static final String AES_KEY = "1234666612346666";


    /**
     * 获取加密时间戳
     *
     * @param timeStamp 时间戳
     * @return 加密时间戳
     */
    public static String encryptTimeStamp(Long timeStamp) {
        String last3 = splitLast3(timeStamp);
        String encrypteded = encryptedSwitch(last3);
        return timeStamp.toString().substring(0, 10) + encrypteded;
    }


    /**
     * 验证时间戳
     * @param timeStamp 时间戳
     * @param accessToken token
     * @return 是否验证通过
     */
    public static boolean verifyTimeStamp(final String timeStamp, final String accessToken) {
        long ts = Long.parseLong(timeStamp);
        long now = System.currentTimeMillis();
        // 5分钟过期
        if (now - ts > 1000 * 60 * 5) {
            return false;
        }
        return decryptByAES(accessToken).equals(replaceEncryptedLast3(Long.parseLong(timeStamp)));
    }



    /**
     * 使用AES加密时间戳
     *
     * @param content 时间戳
     * @return aes报文
     */
    public static String encryptByAES(final String content) {
        byte[] bytes = new byte[0];
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            bytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException
                 | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(bytes);
    }


    /**
     * 使用AES解密获取校验时间戳
     *
     * @param content aes报文
     * @return 时间戳
     */
    public static String decryptByAES(final String content) {
        byte[] bytes = new byte[0];
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            bytes = cipher.doFinal(Base64.getDecoder().decode(content));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                 | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String replaceEncryptedLast3(Long timeStamp) {
        String last3 = splitLast3(timeStamp);
        String decrypted = decryptedSwitch(last3);
        return timeStamp.toString().substring(0, 10) + decrypted;
    }

    private static String splitLast3(Long timeStamp) {
        return timeStamp.toString().substring(10);
    }

    private static String encryptedSwitch(String num) {
        StringBuilder switched = new StringBuilder();
        int index = 1;
        for (int i = num.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(String.valueOf(num.charAt(i)));
            switched.insert(0, encrypted(digit, index));
            index++;
        }
        return switched.toString();
    }

    private static String decryptedSwitch(String num) {
        StringBuilder switched = new StringBuilder();
        int index = 1;
        for (int i = num.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(String.valueOf(num.charAt(i)));
            switched.insert(0, decrypted(digit, index));
            index++;
        }
        return switched.toString();
    }

    private static int encrypted(int n, int index) {
        if (isStand(n)) {
            return n;
        } else {
            return index % 2 == 0 ? getClockWise(n, index) : getAntiClockWise(n, index);
        }

    }

    private static int decrypted(int n, int index) {
        if (isStand(n)) {
            return n;
        } else {
            return index % 2 == 0 ? getAntiClockWise(n, index) : getClockWise(n, index);

        }
    }

    private static boolean isStand(int n) {
        for (String s : STAND) {
            if (s.equals(String.valueOf(n))) {
                return true;
            }
        }
        return false;
    }

    private static int getClockWise(int n, int step) {
        int len = CLOCK.length;
        int index = 0;
        for (int i = 0; i < len; i++) {
            if (CLOCK[i].equals(String.valueOf(n))) {
                index = i;
                break;
            }
        }
        int newIndex = (index + step) % len;
        return Integer.parseInt(CLOCK[newIndex]);
    }

    private static int getAntiClockWise(int n, int step) {
        int len = CLOCK.length;
        int index = 0;
        for (int i = 0; i < len; i++) {
            if (CLOCK[i].equals(String.valueOf(n))) {
                index = i;
                break;
            }
        }
        int newIndex = (index - step + len) % len;
        return Integer.parseInt(CLOCK[newIndex]);
    }




}
