package pack.wolf.com.pifi.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import pack.wolf.com.pifi.application.AppConstants;
import pack.wolf.com.pifi.util.TokenUtil;

public class VolleyManager {

    private static VolleyManager mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private BitmapLRUCache mCache;

    private VolleyManager(Context context, int cacheSize) {

        mRequestQueue = Volley.newRequestQueue(context);
        mCache = new BitmapLRUCache(cacheSize);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {

            public void putBitmap(String url, Bitmap bitmap) {

                mCache.put (url, bitmap);
            }

            public Bitmap getBitmap(String url) {

                return mCache.get (url);
            }
        });
    }

    public static VolleyManager getInstance() {

        if (mInstance == null) {
            throw new IllegalStateException ("Did you call VolleyManager.initialize()?");
        }
        return mInstance;
    }

    public static void initialize(Context context, int cacheSize) {

        if (mInstance == null) {
            mInstance = new VolleyManager(context, cacheSize);
        }
        else {
            throw new IllegalStateException("You already called VolleyManager.initialize()!");
        }
    }

    public RequestQueue getRequestQueue() {

        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader() {

        return this.mImageLoader;
    }

    public void clearImageCache() {

        if (mCache != null) {
            mCache.evictAll();
            System.gc();
        }
    }

    public void addToRequestQueue(final Request request, final Context context) {

        if (TokenUtil.isTokenExpired()) {
//
//            RefreshTokenRequest tokenRequest = new RefreshTokenRequest();
//            tokenRequest.setRefreshToken(SharedPreferenceUtil.getAccessToken().getRefreshToken());
//            Gson gson = new Gson();
//            String jsonRequest = gson.toJson(tokenRequest);
////            Map<String, String> headers = new HashMap<>();
////            headers.put("Authorization", tokenRequest.getRefreshToken());
//
//            Map<String, String> params = new HashMap<>();
//            params.put(AppConstants.REQUEST_PARAM_GRANT, AppConstants.REQUEST_VALUE_REFRESH);
//            params.put(AppConstants.REQUEST_PARAM_REFRESH, tokenRequest.getRefreshToken());
//            String url = AppConstants.SERVER_PATH + AppConstants.METHOD_TOKENS;
//
//            GsonFormEncodedRequest<AccessToken> refreshRequest = new GsonFormEncodedRequest<> (AppConstants.FORM_CONTENT_TYPE, GsonRequest.Method.POST,
//                    url, AccessToken.class, null, params, new Response.Listener<AccessToken> () {
//                @Override
//                public void onResponse (AccessToken response) {
//                    Log.d(AppConstants.LOG_TAG, "Refresh token call success.");
//                    SharedPreferenceUtil.saveAccessToken(response);
//                    getRequestQueue().add(request);
//                }
//            }, new Response.ErrorListener () {
//                @Override
//                public void onErrorResponse (VolleyError error) {
//                    ErrorResponse errorResponse = ErrorUtil.getErrorMessage(error, context);
//                    String toastMessage;
//                    if (errorResponse != null && StringUtils.isNotBlank(errorResponse.getErrorMessage())) {
//                        toastMessage = errorResponse.getErrorMessage();
//                    } else {
//                        toastMessage = context.getString(R.string.error_login);
//                    }
//                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            getRequestQueue().add(refreshRequest);
        } else {
            getRequestQueue().add(request);
            Log.d(AppConstants.LOG_TAG, "Enqueued request to: " + request.getUrl());
        }
    }
}
