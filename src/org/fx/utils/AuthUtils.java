package org.fx.utils;

import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthUtils {
    private static String macStr;
    private static String key;

    static {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            System.out.println("mac数组长度：" + mac.length);
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                //字节转换为整数
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
                System.out.println("每8位:" + str);
                if (str.length() == 1) {
                    sb.append("0" + str);
                } else {
                    sb.append(str);
                }
            }
            macStr = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMacStr() {
        return macStr;
    }

    public static String getKey() {
        try {
            if (AuthUtils.auth(key)) {
                return key;
            } else {
                FileReader fr = new FileReader("key.txt");
                BufferedReader br = new BufferedReader(fr);
                String key = br.readLine();
                if(AuthUtils.auth(key)){
                    AuthUtils.key = key;
                    return key;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void setKey(String key) {
        if (AuthUtils.auth(key)) {
            AuthUtils.key = key;
            System.out.println("set key success");
            //持久化
            FileWriter fw = null;
            try {
                fw = new FileWriter("key.txt");
                fw.write(key);
                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static boolean auth(String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            String pass = macStr + "yunhanhe";
            md5.update(pass.getBytes("utf-8"));
            String keyC = new BigInteger(1, md5.digest()).toString(16);
            if (keyC.equals(key)) {
                return true;
            }
            System.out.println("pass:" + pass);
            System.out.println("key:" + key);
            System.out.println("right key:" + keyC);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean auth() {
        return auth(key);
    }
}
