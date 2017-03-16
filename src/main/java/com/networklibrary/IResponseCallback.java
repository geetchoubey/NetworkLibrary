package com.networklibrary;

import com.android.volley.VolleyError;

public interface IResponseCallback<T> {

    void onResponse(T response);
    void onError(VolleyError volleyError);

}
