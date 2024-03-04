import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonPlaceHolderCaller implements RequestHandler<Object, Void> {

    @Override
    public Void handleRequest(Object input, Context context) {
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.toJsonTree(input).getAsJsonObject();
            String vendor = jsonObject.get("vendor").getAsString();
            JsonArray adIds = jsonObject.getAsJsonArray("ad_ids");

            for (JsonElement adIdElement : adIds) {
                String adId = adIdElement.getAsString();
                String apiUrl = "https://jsonplaceholder.typicode.com/posts/" + adId;

                // Make API call
                String response = makeApiCall(apiUrl);

                // Log the API response
                System.out.println("Vendor: " + vendor + ", Ad ID: " + adId + ", API Response: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String makeApiCall(String apiUrl) throws Exception {
        StringBuilder response = new StringBuilder();
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
}
