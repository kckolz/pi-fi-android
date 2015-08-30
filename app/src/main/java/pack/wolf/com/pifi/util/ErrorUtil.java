package pack.wolf.com.pifi.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import pack.wolf.com.pifi.application.AppConstants;
import pack.wolf.com.pifi.model.ErrorResponse;

public class ErrorUtil {

    public static ErrorResponse getErrorMessage(VolleyError volleyError, Context context) {
        Log.e(AppConstants.LOG_TAG, "Parsing error response...");

        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage("An API error occurred.");
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            Log.e(AppConstants.LOG_TAG, "Error caught... json: " + volleyError.networkResponse.data);

        }
        Log.e(AppConstants.LOG_TAG, "Null or empty error response...");

        return response;
    }
}
