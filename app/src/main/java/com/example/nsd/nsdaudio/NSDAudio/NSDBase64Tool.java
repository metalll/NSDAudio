package com.example.nsd.nsdaudio.NSDAudio;



import android.util.Base64;



/**
 * Created by NSD on 9/5/16.
 */
public class NSDBase64Tool {

    public static String encode(byte[] data)
    {
        return Base64.encodeToString(data,Base64.DEFAULT);
    }

    public static byte[] decode(String data)
    {
        return Base64.decode(data,Base64.DEFAULT);
    }
}
