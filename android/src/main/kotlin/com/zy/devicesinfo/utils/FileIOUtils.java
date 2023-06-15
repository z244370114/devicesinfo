package com.zy.devicesinfo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileIOUtils {


    public static String readFileString(final File file) {
        String deviceId = "";
        try {
            InputStream is = new FileInputStream(file);
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                while ((deviceId = br.readLine()) != null) {
                    return deviceId;
                }
                is.close();
                isr.close();
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

}
