package hibskyi;

import java.util.Scanner;

public class CurrencyConverterApp {
    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("What currency you want to get? ");
            String toCurrency = scan.next().toUpperCase();
            System.out.print("What currency you want to exchange? ");
            String fromCurrency = scan.next().toUpperCase();
            System.out.print("How much? ");
            int amount = Integer.parseInt(scan.next());

            double result = CurrencyConverter.convert(fromCurrency, toCurrency, amount);
            System.out.printf("You get %.2f " + toCurrency, result);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
