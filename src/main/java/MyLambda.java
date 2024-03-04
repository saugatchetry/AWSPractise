import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class MyLambda implements RequestHandler<List<Integer>, String> {

    @Override
    public String handleRequest(List<Integer> ids, Context context) {
        // Create an HTTP client
        HttpClient httpClient = HttpClients.createDefault();

        // Iterate over id values
        for (Integer id : ids) {
            // Construct the URL with the current id
            String apiUrl = "https://jsonplaceholder.typicode.com/posts/" + id;

            // Create an HTTP GET request
            HttpGet request = new HttpGet(apiUrl);

            try {
                // Execute the request
                HttpResponse response = httpClient.execute(request);

                // Check if the request was successful (status code 200)
                if (response.getStatusLine().getStatusCode() == 200) {
                    // Read the response body
                    String responseBody = EntityUtils.toString(response.getEntity());
                    System.out.println("Response for id " + id + ": " + responseBody);
                } else {
                    // Log error if request was not successful
                    System.err.println("Error for id " + id + ": HTTP " + response.getStatusLine().getStatusCode());
                }
            } catch (IOException e) {
                // Log any exceptions that occur during HTTP request
                System.err.println("Error for id " + id + ": " + e.getMessage());
            }
        }

        // Return success message
        return "All requests completed";
    }
}
