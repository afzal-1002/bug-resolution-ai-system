package com.pw.edu.pl.master.thesis.ai.model.ai.gemini.service;


import java.util.Base64;

public class ImageEncoderUtil {


    public static String encodeImageToBase64(byte[] imageBytes) {
        if (imageBytes == null || imageBytes.length == 0) {
            return null;
        }
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // You might add methods to decode Base64 back to bytes if needed for other purposes
     public static byte[] decodeBase64ToImage(String base64String) {
         if (base64String == null || base64String.isEmpty()) {
             return null;
         }
         return Base64.getDecoder().decode(base64String);
     }
}