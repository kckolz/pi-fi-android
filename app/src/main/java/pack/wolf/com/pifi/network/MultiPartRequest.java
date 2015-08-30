package pack.wolf.com.pifi.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import pack.wolf.com.pifi.application.AppConstants;
import pack.wolf.com.pifi.model.AccessToken;
import pack.wolf.com.pifi.util.SharedPreferenceUtil;

public class MultiPartRequest extends Request<String> {

    private MultipartEntity entity = new MultipartEntity();

    private Map<String, String> headers;

    private static final String FILE_PART_NAME = "file";
    private static final String STRING_PART_NAME = "text";

    private final Response.Listener<String> mListener;
    private final InputStream mFilePart;
    private final String mStringPart;

    public MultiPartRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener, InputStream stream, String stringPart) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePart = stream;
        mStringPart = stringPart;
        buildMultipartEntity();
        setRetryPolicy(new DefaultRetryPolicy(AppConstants.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void buildMultipartEntity() {

        entity.addPart(FILE_PART_NAME, new InputStreamBody(mFilePart, "image/jpeg"));
        if (mStringPart != null) {
            try {
                entity.addPart(STRING_PART_NAME, new StringBody(mStringPart));
            } catch (UnsupportedEncodingException e) {
                VolleyLog.e("UnsupportedEncodingException");
            }
        }
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return Response.success(new String(response.data), null);
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        addAuthorizationToHeader();
        return headers != null ? headers : super.getHeaders();
    }

    private void addAuthorizationToHeader() {

        if (headers == null) {
            headers = new HashMap<String, String>();
        }

        AccessToken token = SharedPreferenceUtil.getAccessToken();
        if (token != null) {
            headers.put("Authorization", "Bearer " + token.getAccessToken());
        }
    }
}