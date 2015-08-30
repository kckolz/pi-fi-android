package pack.wolf.com.pifi.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

import pack.wolf.com.pifi.R;
import pack.wolf.com.pifi.activity.BaseActionBarActivity;
import pack.wolf.com.pifi.activity.UserSearchActivity;
import pack.wolf.com.pifi.model.Track;
import pack.wolf.com.pifi.model.User;
import pack.wolf.com.pifi.service.TrackServiceFactory;
import pack.wolf.com.pifi.service.UserServiceFactory;
import pack.wolf.com.pifi.service.api.UserService;
import pack.wolf.com.pifi.util.DialogUtil;
import pack.wolf.com.pifi.util.SharedPreferenceUtil;

public class MainFragment extends Fragment {

    private static View rootView;
    private Context context;
    ProgressDialog dialog;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
        } catch (InflateException e) {
        }

        final UserService userService = UserServiceFactory.getInstance();

        // get context
        context = inflater.getContext();
        dialog = DialogUtil.getProgressDialog(context, getString(R.string.signing_in));

        UserServiceFactory.getInstance().getCurrentUser(context, new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                SharedPreferenceUtil.saveUser(response);

                userService.saveBlueToothAddress(context, response, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object response) {
                        Log.e("blah", "saved bluetooth");
                    }
                });

                if (StringUtils.isNotBlank(response.getDefaultTrack())) {
                    TrackServiceFactory.getInstance().getTrack(response.getDefaultTrack(), context, new Response.Listener<Track>() {
                        @Override
                        public void onResponse(Track response) {
                            //populate it now, hide dummy text
                            LinearLayout bottomText = (LinearLayout) rootView.findViewById(R.id.bottomText);
                            LinearLayout trackDetails = (LinearLayout) rootView.findViewById(R.id.trackDetails);
                            bottomText.setVisibility(View.GONE);
                            trackDetails.setVisibility(View.VISIBLE);

                            // change icon
                            Button edit = (Button) rootView.findViewById(R.id.startButton);
                            edit.setBackground(getResources().getDrawable(R.drawable.edit));

                            // add track info
                            ImageView album = (ImageView) rootView.findViewById(R.id.album);
                            TextView artist = (TextView) rootView.findViewById(R.id.artist);
                            TextView track = (TextView) rootView.findViewById(R.id.track);
                            artist.setText(response.getArtists().toString());
                            track.setText(response.getName().toString());

                            // set background
                            if (response.getAlbum().getImages().size()>0) {
                                new DownloadImageTask(album).execute(response.getAlbum().getImages().get(0).getUrl());
                            }
                        }
                    }, dialog);
                }
            }
        }, dialog);


        // set title
        BaseActionBarActivity.setTitle(getString(R.string.home));

        // get context
        context = inflater.getContext();

        // get button, bring to front
        Button startButton = (Button) rootView.findViewById(R.id.startButton);
        startButton.bringToFront();
        startButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent = new Intent(context, UserSearchActivity.class);
                                               startActivity(intent);
                                           }
                                       }
        );

        return rootView;



    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            bmImage.setImageBitmap(result);
        }
    }
}


