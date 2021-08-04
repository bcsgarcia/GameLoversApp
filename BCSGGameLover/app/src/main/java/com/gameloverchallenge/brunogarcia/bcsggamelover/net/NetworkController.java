package com.gameloverchallenge.brunogarcia.bcsggamelover.net;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gameloverchallenge.brunogarcia.bcsggamelover.util.Helper;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class NetworkController {

    private static final String TAG = NetworkController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private static NetworkController mInstance;

    public static synchronized NetworkController getInstance(Context ctx) {
        if (mInstance == null) mInstance = new NetworkController();
        mCtx = ctx;
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void fetchData(String endpoint, final DataCallback callback) {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest (Request.Method.GET,
                Helper.getResourceByName(mCtx,"api_url") + endpoint,
                null,
                (JSONArray response) -> callback.onSuccess(response),
                (VolleyError error) -> VolleyLog.d(TAG, "Error: " + error.getMessage()) )
                {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<>();
                        params.put("user-key", Helper.getResourceByName(mCtx,"api_key"));
                        params.put("Access", "application/json");
                        return params;
                    }
                };

        NetworkController.getInstance(mCtx).addToRequestQueue(jsonObjectRequest);
    }


}