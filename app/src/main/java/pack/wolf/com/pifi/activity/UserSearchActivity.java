package pack.wolf.com.pifi.activity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pack.wolf.com.pifi.R;
import pack.wolf.com.pifi.application.AppConstants;
import pack.wolf.com.pifi.model.ErrorResponse;
import pack.wolf.com.pifi.model.Track;
import pack.wolf.com.pifi.model.TrackWrapper;
import pack.wolf.com.pifi.network.GsonFormEncodedRequest;
import pack.wolf.com.pifi.network.GsonRequest;
import pack.wolf.com.pifi.network.VolleyManager;
import pack.wolf.com.pifi.util.BluetoothUtil;
import pack.wolf.com.pifi.util.ErrorUtil;
import pack.wolf.com.pifi.util.SearchResultAdapter;

public class UserSearchActivity extends BaseActionBarActivity2 {


    private Dialog dialog;
    private Context context;
    private SearchResultAdapter searchResultAdapter;
    private ListView searchResultListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        this.context = this;
        searchResultListView = (ListView) findViewById(R.id.searchResultListView);

        enableSearchBar(true);
        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.enableSearchBar(true);
        return super.onCreateOptionsMenu(menu);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            Log.d(AppConstants.LOG_TAG, "Search was submitted: " + query);
            makeUserSearchRequest(query);
        }
    }


    private void makeUserSearchRequest(String title) {


        String btAddy = BluetoothUtil.getBlueToothAddress(context);
        Map<String, String> params = new HashMap<>();
        String url = AppConstants.SERVER_PATH + "spotify/search?query=" + title +"&type=track";

        GsonFormEncodedRequest<TrackWrapper> request = new GsonFormEncodedRequest<> (AppConstants.FORM_CONTENT_TYPE, GsonRequest.Method.GET,
                url, TrackWrapper.class, null, params, new Response.Listener<TrackWrapper> () {
            @Override
            public void onResponse (TrackWrapper object) {


                List<Track> result = object;
                if (result == null || result.isEmpty()) {

                } else {
                    searchResultAdapter = new SearchResultAdapter(UserSearchActivity.this, R.layout.user_search_layout,result);
                    searchResultListView.setAdapter(searchResultAdapter);
                    searchResultAdapter.notifyDataSetChanged();
                }




            }
        }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse (VolleyError error) {
                ErrorResponse errorResponse = ErrorUtil.getErrorMessage(error, context);
                String toastMessage;
                if (errorResponse != null && StringUtils.isNotBlank(errorResponse.getErrorMessage())) {
                    toastMessage = errorResponse.getErrorMessage();
                } else {
                    toastMessage = "bad!";
                }
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        VolleyManager.getInstance().getRequestQueue().add(request);
        }
}