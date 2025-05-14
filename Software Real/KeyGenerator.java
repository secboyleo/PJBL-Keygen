import java.util.Base64;

public class KeyGenerator {
    public static String generateKey(String userName) {
        String rawData = userName + ":" + System.currentTimeMillis();
        String signature = signData(rawData);
        return Base64.getEncoder().encodeToString((rawData + ":" + signature).getBytes());
    }

    private static String signData(String data) {
            return data;
        
    }
}