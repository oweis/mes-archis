package com.dofus.tools.scrapper.helper;

import com.dofus.tools.scrapper.common.Constants;
import com.dofus.tools.scrapper.impl.BestiaireScrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(BestiaireScrapper.class);

    public static void downloadImage(String search, String path) throws IOException {

        // This will get input data from the server
        InputStream inputStream = null;

        // This will read the data from the server;
        OutputStream outputStream = null;

        try {
            // This will open a socket from client to server
            URL url = new URL(search);


            // This socket type will allow to set user_agent
            URLConnection con = url.openConnection();

            // Setting the user agent
            con.setRequestProperty("User-Agent", Constants.USER_AGENT_MOZILLA);

            // Requesting input data from server
            inputStream = con.getInputStream();

            // Open local file writer
            outputStream = new FileOutputStream(path);

            // Limiting byte written to file per loop
            byte[] buffer = new byte[2048];

            // Increments file size
            int length;

            // Looping until server finishes
            while ((length = inputStream.read(buffer)) != -1) {
                // Writing data
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception ex) {
            LOGGER.debug("ERROR ...");
        }

        // closing used resources
        // The computer will not be able to use the image
        // This is a must

        outputStream.close();
        inputStream.close();
    }
}
