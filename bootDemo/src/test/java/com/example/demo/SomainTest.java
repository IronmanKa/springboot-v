package com.example.demo;

import java.lang.reflect.Method;
import java.util.Calendar;

public class SomainTest {

//    static {
//        try {
////            String libNamePath = "C:\\ide\\tomcat7\\lib\\djEncryptUtil-2.0.dll";
////            String libNamePath = "/home/lin.he/helin/sotest/djEncryptUtil-3.0.so";
////            String libNamePath = "E:\\git_Java\\fxrh-sdkserver\\so\\libfxrh-yueyou-3.0.dll";
//            try {
//                // try to load this library..
//                System.load(libNamePath);
//            } catch (UnsatisfiedLinkError ule) {
//                ule.printStackTrace(System.err);
//
//            }
//        } catch (Exception ioe) {
//            System.err.println("Failed to parse properties file: lib.properties");
//            ioe.printStackTrace(System.err);
//        }
//    }
    /**
     * 直接调用对象方法, 无视private/protected修饰符.
     */
    private static Object invokeMethod(Class<?> cls, String methodName, Class<?>[] parameterTypes,
                                       Object[] parameters) {
        try {
            Method method;
            if (parameterTypes != null) {
                method = cls.getDeclaredMethod(methodName, parameterTypes);
            } else {
                method = cls.getDeclaredMethod(methodName);
            }
            method.setAccessible(true);
            return method.invoke(cls, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

//    public static String encryptBody() {
//        try {
//            String painStr = "白发魔女\ud83d\udc9e练霓裳親不见，（爱）愛无心，（产）產不生，（厂）厰空空!@#！@#abc一二三壹贰叁123全角！＠＃１２３ａｂｃ日文ニュースのほか韩文네이버 중국어사전阿拉伯文صحيفة الشعب اليو结束";
////            System.out.println(painStr);
//            Class<?>[] parameterTypes = new Class[]{String.class};
//            String ss = String.valueOf(invokeMethod(EncryptUtil.class, "encryptBody", parameterTypes, new String[]{painStr}));
//            System.out.println(ss);
//            return ss;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static void decryptBody(String painStr) {
//        //親不见，（爱）愛无心，（产）產不生，（厂）厰空空!@#！@#abc一二三壹贰叁123全角！＠＃１２３ａｂｃ日文ニュースのほか韩文네이버 중국어사전阿拉伯文صحيفة الشعب اليو结束
//        Class<?>[] parameterTypes = new Class[] { String.class };
//        String ss= String.valueOf(invokeMethod(EncryptUtil.class, "decryptBody", parameterTypes,
//                new String[] { painStr }));
//        System.out.println(ss);
//    }
//
//    public static final void main(String[] args) {
//        String str=encryptBody();
//        decryptBody(str);
//    }
public static void main(String[] args) {
    Calendar calendar = Calendar.getInstance();
    System.out.println(calendar.get(Calendar.YEAR));
}

}
