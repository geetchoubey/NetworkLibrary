package com.networklibrary;

import android.app.Dialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by quadandroid-1 on 15/3/17.
 */

public class Request implements Response.ErrorListener, Response.Listener<JSONObject> {

    private static final int DEFAULT_METHOD = 0;
    private static final String DEFAULT_LOADING_MESSAGE = "Please wait. Loading data.";
    private static final boolean DEFAULT_SHOULD_SHOW_DIALOG = true;

    private Context context;
    private int method;
    private String toUrl;
    private JSONObject requestData;
    private Class responseClass;
    private IResponseCallback callback;
    private Dialog dialog;

    private Map<String, String> headers = new HashMap<>();

    private String loadingMessage;
    private boolean shouldShowProgressDialog;

    public Request(Context context, int method, String toUrl, JSONObject requestData, Class responseClass, IResponseCallback callback) {
        this.context = context;
        this.method = method;
        this.toUrl = toUrl;
        this.requestData = requestData;
        this.responseClass = responseClass;
        this.callback = callback;

        loadingMessage = DEFAULT_LOADING_MESSAGE;
        shouldShowProgressDialog = DEFAULT_SHOULD_SHOW_DIALOG;
        headers.put("Content-Type", "application/x-www-form-urlencoded");
    }

    public Request(Context context, String toUrl, JSONObject requestData, Class responseClass, IResponseCallback callback) {
        this(context, DEFAULT_METHOD, toUrl, requestData, responseClass, callback);
    }

    public void execute() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, toUrl, requestData, this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        showDialog();
    }

    private void showDialog() {
        if (!shouldShowProgressDialog)
            return;
        dialog = new Dialog(context);
        dialog.setTitle(loadingMessage);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void dismissDialog() {
        if (null != dialog && dialog.isShowing())
            dialog.dismiss();
    }

    public Request setLoadingMessage(String loadingMessage) {
        this.loadingMessage = loadingMessage;
        return this;
    }

    public Request setLoadingMessage(int loadingMessageStringId) {
        this.loadingMessage = context.getString(loadingMessageStringId);
        return this;
    }

    public Request setShouldShowProgressDialog(boolean shouldShowProgressDialog) {
        this.shouldShowProgressDialog = shouldShowProgressDialog;
        return this;
    }

    public Request addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public Request addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        dismissDialog();
        callback.onError(volleyError);
    }

    @Override
    public void onResponse(JSONObject response) {
        dismissDialog();
        if (String.class == responseClass)
            callback.onResponse(response);
        else {
            //Object finalResponse = new Gson().fromJson(response.toString(), responseClass);
            ObjectMapper mapper = new ObjectMapper();
            Object finalResponse = null;
            try {
                finalResponse = mapper.readValue(response.toString(), responseClass);
                callback.onResponse(finalResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
