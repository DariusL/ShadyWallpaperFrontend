package lt.mano.shadywallpaperfrontend.net;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Darius on 2014.11.06.
 */
public class NetworkingUtils {

    public static void get(String urlString, NetworkReader reader){
        URL url = null;

        try {
            url = new URL(urlString);
        }catch (MalformedURLException e){
            Log.e("", "Malformed url " + urlString);
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection) url.openConnection();
            reader.read(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }

    public interface NetworkReader{
        public void read(InputStream stream);
    }
}
