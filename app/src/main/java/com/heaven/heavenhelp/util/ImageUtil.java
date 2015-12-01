package com.heaven.heavenhelp.util;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;

/**
 * Created by waylon on 15/12/1.
 */
public class ImageUtil {

    public static String regImg(Bitmap photodata) throws Exception{

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photodata.compress(Bitmap.CompressFormat.PNG, 40, baos);
        baos.close();
        byte[] buffer = baos.toByteArray();
        String photo = Base64.encodeToString(buffer, 0, buffer.length, Base64.DEFAULT);
        photo= URLEncoder.encode(photo, "UTF-8");
        return photo;
    }
}
