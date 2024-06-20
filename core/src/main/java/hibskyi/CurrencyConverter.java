package hibskyi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class CurrencyConverter {

    public static double convert(String fromCurrency, String toCurrency, double amount) throws Exception {

        String ratesJSON = ExchangeRateLoader.loadRatesJSON(fromCurrency);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(ratesJSON);

        HashMap<String, Double> rates = new HashMap<>();
        jsonNode.fields().forEachRemaining(entry -> rates.put(entry.getKey(), entry.getValue().asDouble()));

        if (!rates.containsKey(toCurrency))
            throw new IllegalArgumentException("Unknown currency");
        return amount * rates.get(toCurrency);
    }
}
