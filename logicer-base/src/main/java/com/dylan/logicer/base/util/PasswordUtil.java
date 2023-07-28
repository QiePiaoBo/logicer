package com.dylan.logicer.base.util;

import java.security.MessageDigest;

/**
 * @author Dylan
 */
public class PasswordUtil {
    /**
     * 十六进制下数字到字符的映射数组
     */
    private final static String[] HEX_DIGITS = {"0", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 密码加密
     * @param inputString
     * @return
     */
    public static String createPassword(String inputString) {
        return encodeBymd5(inputString);
    }

    /**
     * 密码校验
     * @param password
     * @param inputString
     * @return
     */
    public static boolean authenticatePassword(String password, String inputString) {
        if (password.equals(encodeBymd5(inputString))) {
            return true;
        } else {
            return false;
        }
    }

    private static String encodeBymd5(String originString) {
        if (originString != null) {
            try {
                // 创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(originString.getBytes());
                // 将得到的字节数组变成字符串返回
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }


}
