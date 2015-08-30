package pack.wolf.com.pifi.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;

import pack.wolf.com.pifi.R;
import pack.wolf.com.pifi.activity.BaseActionBarActivity;
import pack.wolf.com.pifi.service.api.AuthenticationService;
import pack.wolf.com.pifi.service.api.TrackService;
import pack.wolf.com.pifi.service.impl.AuthenticationServiceImpl;
import pack.wolf.com.pifi.service.impl.TrackServiceImpl;
import pack.wolf.com.pifi.util.DialogUtil;

public class TrackFragment extends Fragment {

    private static View rootView;
    private Context context;
    private String track_id;
    private String title;
    private String artist;
    private String album;

    public static TrackFragment newInstance(String track_id) {
        TrackFragment fragment = new TrackFragment();
        Bundle args = new Bundle();
        args.putString("track_id",track_id);
        fragment.setArguments(args);
        return fragment;
    }

    public TrackFragment() {
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_track, container, false);
        } catch (InflateException e) {
        }

        // get args
        Bundle bundle = new Bundle();
        track_id = bundle.getString("track_id");

        // get track info
        ProgressDialog dialog = DialogUtil.getProgressDialog(context, getString(R.string.track_info));
        TrackService trackService = new TrackServiceImpl();
        //trackService.getTrack(context,new GetTrackListener(context),dialog);

        // get context
        context = inflater.getContext();

        return rootView;

    }

    // get track listener
    private static class GetTrackListener implements Response.Listener {

        Context context;

        GetTrackListener(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(Object response) {
            Object status = response;

            // set title
            //BaseActionBarActivity.setTitle(title + " by " + artist);

        }

    }

}
