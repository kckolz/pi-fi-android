package pack.wolf.com.pifi.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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
import pack.wolf.com.pifi.network.GsonFormEncodedRequest;
import pack.wolf.com.pifi.network.GsonRequest;
import pack.wolf.com.pifi.network.VolleyManager;

public class SearchResultAdapter extends ArrayAdapter<Track> {

    private int resource;
    private Activity activity;
    private Context context;
    private List<Track> data;

    public SearchResultAdapter(Activity activity, int resource, List<Track> items) {
        super(activity, resource, items);
        this.resource = resource;
        this.activity = activity;
        this.context = activity;
        this.data = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UserHolder holder = null;
        View row = convertView;

        Track track = data.get(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(resource, parent, false);

            TextView userNameText = (TextView) row.findViewById(R.id.usersName);

            userNameText.setText(track.getArtists().get(0) + " - " + track.getName());

            holder = new UserHolder();
            holder.usersName = (TextView) row.findViewById(R.id.usersName);

            row.setTag(holder);

        } else {
            holder = (UserHolder) row.getTag();
        }

        row.setOnClickListener(new UsernameOnClickListener(track));

        return row;
    }

    static class UserHolder {
        TextView usersName;
    }

    public class UsernameOnClickListener implements View.OnClickListener {

        Track track;

        public UsernameOnClickListener(Track track) {
            this.track = track;
        }

        @Override
        public void onClick(View view) {

                Map<String, String> params = new HashMap<>();
            params.put("track", track.getId());
            params.put("id", SharedPreferenceUtil.getUser().getId());
                String url = AppConstants.SERVER_PATH + "track";

                GsonFormEncodedRequest<Object> request = new GsonFormEncodedRequest<> (AppConstants.FORM_CONTENT_TYPE, GsonRequest.Method.PUT,
                        url, Object.class, null, params, new Response.Listener<Object> () {
                    @Override
                    public void onResponse (Object object) {

                        //cool. back to main screen
                        ContextUtil.finish(context);

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
    }
