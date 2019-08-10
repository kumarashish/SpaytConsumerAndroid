package common;

import android.app.Activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import utils.Utils;

public class FileDownloader {
    private static final int  MEGABYTE = 1024 * 1024;

    public static String downloadFile(String fileUrl, File directory, Activity act){
        try {

            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while((bufferLength = inputStream.read(buffer))>0 ){
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
           Utils.showToast(act,"File Downloaded sucessfully");
            //Toast.makeText(act,"File Downloaded sucessfully",Toast.LENGTH_SHORT).show();
            return directory.getName();
        } catch (FileNotFoundException e) {
            Utils.showToast(act,"Exception Occured while downloading file : "+e.fillInStackTrace());
            e.printStackTrace();
        } catch (MalformedURLException e) {
            Utils.showToast(act,"Exception Occured while downloading file : "+e.fillInStackTrace());
            e.printStackTrace();
        } catch (IOException e) {
            Utils.showToast(act,"Exception Occured while downloading file : "+e.fillInStackTrace());
            e.printStackTrace();
        }
        return "";
    }
}
