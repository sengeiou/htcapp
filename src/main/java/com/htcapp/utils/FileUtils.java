package com.htcapp.utils;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class FileUtils {

    public static String  generateImage(String str,String rootPath,String secondDir,String savePath){


        byte[] b=Base64.decodeBase64(str);
        OutputStream outputStream= null;
        String endPath=null;
        try {
            rootPath=ResourceUtils.getFile(rootPath).getPath();
            endPath=rootPath+File.separator+secondDir+File.separator+savePath;
            checkParent(endPath);
            outputStream = new FileOutputStream(endPath);

            outputStream.write(b);

            outputStream.flush();

            outputStream.close();

            return  secondDir+"/"+savePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void checkParent(String savePath) throws FileNotFoundException {

        File file=new File(savePath);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }

    }

}
