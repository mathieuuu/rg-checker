package rg;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;

public class RgChecker {

    public static void main(String[] args) throws UnirestException, IOException, InterruptedException {

        while (true) {
            System.out.println("Wait...");
            Thread.sleep(5000);
            System.out.println(LocalDateTime.now().toString()+" Check");
            check();
        }
    }


    private static void check() throws UnirestException, IOException {
        HttpResponse<JsonNode> res = Unirest.get("https://tickets.rolandgarros.com/api/tunnel/products/day/20180531/court/SL?_=1527706370007").asJson();

        JsonNode json = res.getBody();
        JSONArray arr = json.getArray();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);
            int price = o.getInt("priceAmount");
            int seats = o.getInt("seats");

            if (seats > 0 && price < 90) {
                Runtime.getRuntime().exec(new String[] {
                        "osascript",
                        "-e",
                        "display notification \""+seats+" places à "+price+"€\" with title \"Places dispos !\" subtitle \"\" sound name \"Basso\"" });
            }
        }
    }
}
