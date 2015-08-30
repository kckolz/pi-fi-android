package pack.wolf.com.pifi.service.impl;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import pack.wolf.com.pifi.application.AppConstants;
import pack.wolf.com.pifi.model.SearchType;
import pack.wolf.com.pifi.model.Track;
import pack.wolf.com.pifi.network.GsonRequest;
import pack.wolf.com.pifi.network.VolleyManager;
import pack.wolf.com.pifi.service.api.TrackService;

/**
 * Created by Whitney Champion on 8/29/15.
 */
public class TrackServiceImpl implements TrackService {

    @Override
    public void getTrack(String track_id, final Context context, final Response.Listener<Track> response, final Dialog dialog) {
        String url = AppConstants.SERVER_PATH + AppConstants.METHOD_TRACK + "/" + track_id;
        GsonRequest<Track> request = new GsonRequest<>(AppConstants.JSON_CONTENT_TYPE, GsonRequest.Method.GET, url, Track.class, null, "", new Response.Listener<Track>() {
            @Override
            public void onResponse(Track tresponse) {
                dialog.dismiss();
                Log.d(AppConstants.LOG_TAG, "Get track call success...");
                response.onResponse(tresponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.e(AppConstants.LOG_TAG, "Error occurred during get track call: " + error.getMessage());
            }
        });

        VolleyManager.getInstance().getRequestQueue().add(request);

    }

    @Override
    public void searchTracks(String query, SearchType searchType, Context context, Response.Listener<Track> response, Dialog dialog){

    } //stub

}