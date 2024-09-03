package com.vehiclerentalservices.Jwt;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;


import com.android.volley.AuthFailureError;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthenticatedRequest extends JsonObjectRequest {

    private final Map<String, String> headers;
    private final JSONObject requestBody;

    public AuthenticatedRequest(String url, JSONObject requestBody, String token, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, requestBody, listener, errorListener);
        this.headers = new HashMap<>();
        this.headers.put("Authorization", "Bearer " + token);
        this.requestBody = requestBody;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

    /*@Override
    public byte[] getBody() throws AuthFailureError {
        return requestBody.toString().getBytes();
    }*/
}
