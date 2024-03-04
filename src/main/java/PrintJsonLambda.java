import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class PrintJsonLambda implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
        String data= input != null ? input.toString() : "{}";
        context.getLogger().log("Input: " + data);
        return "Test completed."+data;
    }
}
