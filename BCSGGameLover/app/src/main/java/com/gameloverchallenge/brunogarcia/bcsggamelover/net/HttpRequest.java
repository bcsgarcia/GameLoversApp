package com.gameloverchallenge.brunogarcia.bcsggamelover.net;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.gameloverchallenge.brunogarcia.bcsggamelover.R;
import com.gameloverchallenge.brunogarcia.bcsggamelover.util.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HttpRequest {

    private String URL = "https://api-endpoint.igdb.com/";
    private static Context CTX;
    private String TAG = "PostHttpVolley";
    private static HttpRequest INSTANCE;

    public static HttpRequest getInstance(Context ctx) {
        if (INSTANCE == null)  INSTANCE = new HttpRequest();
        CTX = ctx;
        return INSTANCE;
    }

    public void getRequest(Response.Listener response, Response.ErrorListener error, String endpoint){


        RequestQueue queue = Volley.newRequestQueue(CTX);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL + endpoint, null, response, error ) {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                    JSONArray result = null;
                    if (jsonString != null && jsonString.length() > 0) result = new JSONArray(jsonString);

                    return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));

                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user-key", Helper.getResourceByName(CTX,"api_key"));
                params.put("Access", "application/json");
                return params;
            }
        };



        queue.add(request);
    }

    public JSONArray getRequestSync(String endpoint){

        RequestQueue queue = Volley.newRequestQueue(CTX);
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(URL + endpoint, future, future){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user-key", Helper.getResourceByName(CTX,"api_key"));
                params.put("Access", "application/json");
                return params;
            }
        };
        queue.add(request);

        JSONArray ret = null;
        try {
            ret = future.get(20, TimeUnit.SECONDS); // this will block
        } catch (InterruptedException e) {
            // exception handling
            e.getStackTrace();

        } catch (ExecutionException e) {
            // exception handling
            e.getStackTrace();

        } catch (TimeoutException e) {
            e.printStackTrace();

        }

        return ret;
    }

}
