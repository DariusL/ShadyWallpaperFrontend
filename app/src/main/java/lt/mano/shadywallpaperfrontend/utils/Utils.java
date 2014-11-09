package lt.mano.shadywallpaperfrontend.utils;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Darius on 2014.11.09.
 */
public class Utils {

    public static void closeStream(Closeable stream){
        try{
            if (stream != null) {
                stream.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void copy(InputStream in, OutputStream out) throws IOException{
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1){
            out.write(buffer, 0, bytesRead);
        }
    }
}
