package com.vehiclerentalservices.Jwt;


import android.util.Base64;
import org.json.JSONObject;

public class JwtUtils {

    public static String extractFromToken(String token) {

        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("JWT does not have 3 parts");
        }

        // Decode the payload (second part)
        String payload = parts[1];
        String decodedPayload = new String(Base64.decode(payload, Base64.URL_SAFE));

        // Parse the payload as a JSON object
        try {
            JSONObject jsonObject = new JSONObject(decodedPayload);


            String username = jsonObject.getString("sub");
            return username;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

