package com.dream.music.util;

import java.io.*;
import java.net.*;

public class HttpConnectionUtil
{
	@SuppressWarnings("finally")
    public static String downloadFile(String urlPath, String downloadDir) {
        File file = null;
        String path = null;
        try {
            URL url = new URL(urlPath);
            URLConnection urlConnection = url.openConnection();          
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;          
            httpURLConnection.setRequestMethod("POST");        
            httpURLConnection.setRequestProperty("Charset", "UTF-8");   
            httpURLConnection.connect();         
            int fileLength = httpURLConnection.getContentLength();
            String filePathUrl = httpURLConnection.getURL().getFile();
            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);
            URLConnection con = url.openConnection();
            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            path = downloadDir + File.separatorChar + fileFullName;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
            }
            bin.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return path;
        }     
    }
}
