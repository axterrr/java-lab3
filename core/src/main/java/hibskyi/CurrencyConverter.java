package hibskyi;

import java.util.Map;

public class CurrencyConverter {

    public static double convert(String fromCurrency, String toCurrency, double amount) throws Exception {

        Map<String, Double> rates = ExchangeRateLoader.loadRates(fromCurrency);
        if (!rates.containsKey(toCurrency))
            throw new IllegalArgumentException("Unknown currency");
        return amount * rates.get(toCurrency);
    }
}
