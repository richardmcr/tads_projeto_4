package uni9.projetopraticoemsistemas.myhealth;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * https://stackoverflow.com/a/3028660
 */
public class DownloadService extends IntentService {

    public static final int UPDATE_PROGRESS = 8344;
    public static final String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/MyHealth/";

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String pdfUrl = intent.getStringExtra("url");
        String pdfName = intent.getStringExtra("name");
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        try {

            //create url and connect
            URL url = new URL(pdfUrl);
            URLConnection connection = url.openConnection();
            connection.connect();

            // this will be useful so that you can show a typical 0-100% progress bar
            int fileLength = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(connection.getInputStream());

            File folderPath = new File(folder);
            folderPath.mkdirs();

            String path = folder + pdfName;
            File filePath = new File(path);
            filePath.createNewFile();

            OutputStream output = new FileOutputStream(filePath);

            byte[] data = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;

                // publishing the progress....
                Bundle resultData = new Bundle();
                resultData.putInt("progress", (int) (total * 100 / fileLength));
                receiver.send(UPDATE_PROGRESS, resultData);
                output.write(data, 0, count);
            }

            // close streams
            output.flush();
            output.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress", 100);

        receiver.send(UPDATE_PROGRESS, resultData);
    }
}