package lt.mano.shadywallpaperfrontend.net;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import lt.mano.shadywallpaperfrontend.data.Wallpaper;

/**
 * Created by Darius on 2014.11.06.
 */
public class RandomDeserializer implements JsonDeserializer<Wallpaper> {
    @Override
    public Wallpaper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
