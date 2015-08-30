package pack.wolf.com.pifi.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import pack.wolf.com.pifi.application.AppConstants;
import pack.wolf.com.pifi.model.AccessToken;
import pack.wolf.com.pifi.util.SharedPreferenceUtil;

public class GsonRequest<T> extends Request<T> {

    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private String jsonBody;
    private Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final String contentType;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(String contentType, int method, String url, Class<T> clazz, Map<String, String> headers, String jsonBody,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.jsonBody = jsonBody;
        this.headers = headers;
        this.listener = listener;
        this.contentType = contentType;
        setRetryPolicy(new DefaultRetryPolicy(AppConstants.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        addAuthorizationToHeader();
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {

        if (jsonBody != null) {
            return jsonBody.getBytes();
        } else {
            return super.getBody();
        }
    }

    @Override
    public String getBodyContentType () {

        return contentType != null ? contentType : "application/json";
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if (response.statusCode == 401 || response.statusCode == 403 || response.statusCode == 500 || response.statusCode == 400) {
                return Response.error(new VolleyError(response));
            } else {
                return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    private void addAuthorizationToHeader() {
        if (headers == null) {
            headers = new HashMap<String, String>();
        }

        if (jsonBody == null) {
            String bodyContentType = contentType != null ? contentType : "application/json";
            headers.put("Content-Type", bodyContentType);
        }

        AccessToken token = SharedPreferenceUtil.getAccessToken();
        if (token != null) {
            headers.put("Authorization", "Bearer " + token.getAccessToken());
        }
    }
}