package com.networklibrary;

import com.android.volley.VolleyError;

/**
 * Created by quadandroid-1 on 15/3/17.
 */

public interface IResponseCallback<T> {

    void onResponse(T response);
    void onError(VolleyError volleyError);

}
