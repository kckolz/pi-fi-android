package pack.wolf.com.pifi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pack.wolf.com.pifi.R;
import pack.wolf.com.pifi.activity.BaseActionBarActivity;
import pack.wolf.com.pifi.application.AppConstants;
import pack.wolf.com.pifi.util.SharedPreferenceUtil;

public class BaseFragment extends Fragment {

    private static View rootView;
    private Context context;

    public static BaseFragment newInstance() {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public BaseFragment() {
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_base, container, false);
        } catch (InflateException e) {
        }

        // check if logged in, go to main screen if logged in
        if (SharedPreferenceUtil.isLoggedIn()) {
            BaseActionBarActivity.fragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .addToBackStack(AppConstants.FRAGMENT_MAIN)
                    .commit();
        } // else continue

        // get context
        context = inflater.getContext();

        // get buttons
        Button signupButton = (Button) rootView.findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new BaseActionBarActivity.FragmentOnClickListener(AppConstants.FRAGMENT_SIGNUP,SignUpFragment.newInstance()));
        Button signinButton = (Button) rootView.findViewById(R.id.signin_button);
        signinButton.setOnClickListener(new BaseActionBarActivity.FragmentOnClickListener(AppConstants.FRAGMENT_SIGNIN,SignInFragment.newInstance()));

        return rootView;

    }




}
