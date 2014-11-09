package lt.mano.shadywallpaperfrontend.net;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import lt.mano.shadywallpaperfrontend.utils.Utils;

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
        InputStream urlStream = null;
        try{
            connection = (HttpURLConnection) url.openConnection();
            int response = connection.getResponseCode();
            urlStream = connection.getInputStream();
            if(response != HttpURLConnection.HTTP_OK) {
                reader.failure(response, connection.getResponseMessage());
            }
            else {
                urlStream = connection.getInputStream();
                reader.success(urlStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }
            if(urlStream != null){
                Utils.closeStream(urlStream);
            }
        }
    }

    public interface NetworkReader{
        public void success(InputStream stream);
        public void failure(int responseCode, String response);
    }
}
