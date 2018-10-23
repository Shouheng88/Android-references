package me.shouheng.libraries.serial;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.shouheng.commons.BaseApplication;
import timber.log.Timber;

/**
 * @author shouh
 * @version $Id: SerializeUtils, v 0.1 2018/10/21 18:03 shouh Exp$
 */
public class SerializeUtils {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private static Object convertFromString(String text) {
        byte[] bytes = hexStringToByteArray(text);
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return in.readObject();
        } catch (Exception e) {
            Timber.e(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Timber.e(e);
                }
            }
        }
        return null;
    }

    private static String convertToString(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //StringWriter stringWriter = new StringWriter();
        ObjectOutputStream out = null;
        if (obj == null) {
            return null;
        }

        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            return bytesToHex(bos.toByteArray());
        } catch (IOException e) {
            Timber.e(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Timber.e(e);
                }
            }
        }
        return null;
    }

    public static void putString(String key, String text) {
        Context context = BaseApplication.getContext();
        context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
                .edit()
                .putString(key, text)
                .apply();
    }

    public static String getString(String key, String defaultValue) {
        Context context = BaseApplication.getContext();
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
                .getString(key, defaultValue);
    }

    public static void remove(String key) {
        Context context = BaseApplication.getContext();
        context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
                .edit()
                .remove(key)
                .apply();
    }

    public static void setStudent(SerializeActivity.Student user) {
        if (user != null) {
            String text = convertToString(user);
            if (text != null) {
                putString("student", text);
            }
        } else {
            remove("student");
        }
    }

    public static SerializeActivity.Student getStudent() {
        String text = getString("student", null);
        if (text != null) {
            return (SerializeActivity.Student) convertFromString(text);
        }
        return null;
    }
}
