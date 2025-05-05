import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] nValues = {500000, 1000000, 2000000}; // Different tree sizes
        int[] kValues = {10, 100, 1000, 10000, 100000}; // Different k values
        int iterations = 50;

        for (int n : nValues) {
            System.out.println("Testing with n = " + n);
            ArrayList<String> stockIds = new ArrayList<>();
            ArrayList<Float> prices = new ArrayList<>();
            ArrayList<Long> timestamps = new ArrayList<>();

            // Generate stocks
            for (int i = 0; i < n; i++) {
                stockIds.add("STOCK" + i);
                prices.add((float) (Math.random() * 1000));
                timestamps.add(1708647300000L + i * 3600000L);
            }

            // Sort prices for range selection
            Collections.sort(prices);

            StockManager stockManager = new StockManager();
            stockManager.initStocks();

            // Insert stocks into StockManager
            for (int i = 0; i < n; i++) {
                stockManager.addStock(stockIds.get(i), timestamps.get(i), prices.get(i));
            }

            for (int k : kValues) {
                if (k > n) continue; // Skip cases where k > n

                Float price1 = prices.get(n / 2 - k / 2);
                Float price2 = prices.get(n / 2 + k / 2 - 1);

                // Measure time for multiple iterations
                long totalTime = 0;
                for (int i = 0; i < iterations; i++) {
                    long startTime = System.nanoTime();
                    String[] stocksInRange = stockManager.getStocksInPriceRange(price1, price2);
                    long endTime = System.nanoTime();
                    totalTime += (endTime - startTime);
                }

                long averageTime = totalTime / iterations;
                System.out.println("For k = " + k + ", Average time taken: " + averageTime + " ns");
            }
        }
    }
}