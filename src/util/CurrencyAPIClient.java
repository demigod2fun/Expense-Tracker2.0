package util;

import org.json.JSONObject;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * REST API Client for fetching real-time currency exchange rates
 */
public class CurrencyAPIClient {
    
    private static final String API_BASE_URL = "https://api.exchangerate-api.com/v4/latest/";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Map<String, Long> cacheTimestamps = new HashMap<>();
    private static final Map<String, Map<String, Double>> rateCache = new HashMap<>();
    private static final long CACHE_DURATION_MS = 3600000; // 1 hour
    
    public static double getExchangeRate(String fromCurrency, String toCurrency) throws Exception {
        String cacheKey = fromCurrency.toUpperCase();
        
        if (isCacheValid(cacheKey) && rateCache.containsKey(cacheKey)) {
            return rateCache.get(cacheKey).getOrDefault(toCurrency.toUpperCase(), 1.0);
        }
        
        String url = API_BASE_URL + fromCurrency.toUpperCase();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .timeout(java.time.Duration.ofSeconds(10))
                .build();
        
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                JSONObject json = new JSONObject(response.body());
                JSONObject rates = json.getJSONObject("rates");
                
                Map<String, Double> rateMap = new HashMap<>();
                rates.keys().forEachRemaining(key -> rateMap.put(key, rates.getDouble(key)));
                
                rateCache.put(cacheKey, rateMap);
                cacheTimestamps.put(cacheKey, System.currentTimeMillis());
                
                return rateMap.get(toCurrency.toUpperCase());
            }
        } catch (Exception e) {
            if (rateCache.containsKey(cacheKey)) {
                return rateCache.get(cacheKey).getOrDefault(toCurrency.toUpperCase(), 1.0);
            }
            throw e;
        }
        
        return 1.0;
    }
    
    public static double convertCurrency(double amount, String fromCurrency, String toCurrency) throws Exception {
        if (fromCurrency.equalsIgnoreCase(toCurrency)) {
            return amount;
        }
        
        double rate = getExchangeRate(fromCurrency, toCurrency);
        return Math.round(amount * rate * 100.0) / 100.0;
    }
    
    private static boolean isCacheValid(String key) {
        if (!cacheTimestamps.containsKey(key)) {
            return false;
        }
        return System.currentTimeMillis() - cacheTimestamps.get(key) < CACHE_DURATION_MS;
    }
    
    public static void clearCache() {
        rateCache.clear();
        cacheTimestamps.clear();
    }
}
