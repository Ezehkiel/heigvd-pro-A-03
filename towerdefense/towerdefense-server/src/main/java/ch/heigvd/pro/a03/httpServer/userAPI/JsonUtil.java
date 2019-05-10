package ch.heigvd.pro.a03.httpServer.userAPI;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

public class JsonUtil {

    public static String toJson(Object object) {

        /* Is used to avoid password to be serialized in the response
         * https://www.baeldung.com/gson-exclude-fields-serialization
         */
        ExclusionStrategy strategy = new ExclusionStrategy() {

            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getName().equals("password");
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        };

        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(strategy).create();
        return gson.toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}