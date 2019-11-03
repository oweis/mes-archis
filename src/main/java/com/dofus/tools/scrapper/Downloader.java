package com.dofus.tools.scrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;



//Multithreading ...
//Save in resources spring
//use variables to get the params
class Downloader extends Thread {

    private String fileUrl;
    private int serieNumber;

    private String sourcePath;
    private String destinationPath;


    @Override
    public void run() {

        try {
            String[] splitedUrl = fileUrl.split("/");
            String fileName = splitedUrl[splitedUrl.length - 1];
            URL website = new URL(fileUrl);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            File serieFolder = new File("C:\\Users\\crci10\\Desktop\\SerieAssets\\s-" + serieNumber);
            if (!serieFolder.exists()) {
                serieFolder.mkdir();
            }
            FileOutputStream fos = new FileOutputStream("C:\\Users\\crci10\\Desktop\\SerieAssets\\s-" + serieNumber + "\\" + fileName);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setSerieNumber(int serieNumber) {
        this.serieNumber = serieNumber;
    }
}