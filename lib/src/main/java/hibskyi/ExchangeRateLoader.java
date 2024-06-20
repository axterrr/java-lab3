package hibskyi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.util.Scanner;

public class ExchangeRateLoader {

    public static String loadRatesJSON(String currency) throws Exception {
        String apiKey = readApiKey();
        String url = "https://v6.exchangerate-api.com/v6/"+apiKey+"/latest/"+currency;
        return getExchangeRates(url);
    }

    private static String readApiKey() {
        ClassLoader classLoader = ExchangeRateLoader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("exchangeRateApiKey.txt");

        if (inputStream == null)
            throw new RuntimeException("Resources not found");

        try (Scanner scan = new Scanner(inputStream)) {
            return scan.nextLine();
        }
    }

    private static String getExchangeRates(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() == 404)
            throw new IllegalArgumentException("Unknown currency");
        if (response.getStatusLine().getStatusCode() != 200)
            throw new RuntimeException("Server error");

        String jsonResponse = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResponse);
        JsonNode ratesNode = jsonNode.get("conversion_rates");

        httpClient.close();
        return ratesNode.toString();
    }
}
