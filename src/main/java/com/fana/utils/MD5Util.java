package com.fana.utils;

import org.apache.commons.codec.digest.DigestUtils;

public  class MD5Util {


    private static final String salt = "fsjvmrn@dc,skenfme";


    private static final String saltDB = "wjrnksorntnsjsurjwjw";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

//    private static final String salt = "1a2b3c4d";
//    private static final String salt

    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        System.out.println(str);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass) {
        String str = "" + saltDB.charAt(0) + saltDB.charAt(2) + formPass + saltDB.charAt(5) + saltDB.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass);
        return dbPass;
    }

    public static void main(String[] args) {

        System.out.println(inputPassToDbPass("willmin"));
    }
}