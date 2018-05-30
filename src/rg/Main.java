package rg;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws UnirestException, IOException, InterruptedException {


        while (true) {
            System.out.println("Wait...");
            Thread.sleep(5000);
            System.out.println((new Date()).getSeconds()+" Check");
            check();
        }
    }


    private static void check() throws UnirestException, IOException {
        HttpResponse<JsonNode> req = Unirest.get("https://tickets.rolandgarros.com/api/tunnel/products/day/20180531/court/SL?_=1527706370007")
                .asJson();

        JsonNode json = req.getBody();

        JSONArray arr = json.getArray();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);
            int p = o.getInt("priceAmount");
            int s = o.getInt("seats");

            if (s > 0 && p < 90) {
                Runtime.getRuntime().exec(new String[] {
                        "osascript",
                        "-e",
                        "display notification \""+p+"\" with title \"Title\" subtitle \"Subtitle\" sound name \"Basso\"" });
            }
        }
    }
}
