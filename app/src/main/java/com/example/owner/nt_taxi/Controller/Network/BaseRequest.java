package com.example.owner.nt_taxi.Controller.Network;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.owner.nt_taxi.R;

import java.util.HashMap;
import java.util.Map;


public class BaseRequest {

    public static void DoGet(final HashMap<String,String> params, final RequestCallback requestCallback,
                             String Url, Context context){

//        final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Loading....");
//        progressDialog.show();

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        dialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        requestCallback.Success(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.hide();
                        requestCallback.Error(error);

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    public static void DoPost(final HashMap<String,String> params, final RequestCallback requestCallback,
                              String Url, Context context){

//        final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Loading....");
//        progressDialog.show();

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        requestCallback.Success(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.hide();
                        requestCallback.Error(error);

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;

            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
