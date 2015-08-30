package pack.wolf.com.pifi.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;

import pack.wolf.com.pifi.R;
import pack.wolf.com.pifi.activity.BaseActionBarActivity;
import pack.wolf.com.pifi.application.AppConstants;
import pack.wolf.com.pifi.model.AccessToken;
import pack.wolf.com.pifi.service.BluetoothService;
import pack.wolf.com.pifi.service.api.AuthenticationService;
import pack.wolf.com.pifi.service.impl.AuthenticationServiceImpl;
import pack.wolf.com.pifi.util.BluetoothUtil;
import pack.wolf.com.pifi.util.DialogUtil;
import pack.wolf.com.pifi.util.SharedPreferenceUtil;

public class SignInFragment extends Fragment {


     ProgressDialog dialog;

    private static View rootView;
    private Context context;

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SignInFragment() {
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_signin, container, false);
        } catch (InflateException e) {
        }

        // set title
        BaseActionBarActivity.setTitle(getString(R.string.signin));

        // get context
        context = inflater.getContext();

        dialog = DialogUtil.getProgressDialog(context,getString(R.string.signing_in));
        Log.e("blah", "\n\n" + BluetoothUtil.getBlueToothAddress(getActivity()) + "\n\n");

        // get user creds
        final EditText username_box = (EditText) rootView.findViewById(R.id.email);
        final EditText password_box = (EditText) rootView.findViewById(R.id.password);

        // sign in
        Button button_signin = (Button) rootView.findViewById(R.id.signin_button);
        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                String username = username_box.getText().toString();
                String password = password_box.getText().toString();

                AuthenticationService auth = new AuthenticationServiceImpl();
                auth.login(username,password,context,new SignInListener(context),dialog);
            }
        });

        getActivity().startService(new Intent(getActivity(), BluetoothService.class));

        return rootView;

    }

    protected void navToMain() {
        dialog.dismiss();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack(AppConstants.FRAGMENT_MAIN
                )
                .commit();

    }

    // sign in response listener
    private class SignInListener implements Response.Listener<AccessToken> {

        Context context;

        SignInListener(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(AccessToken response) {
            dialog.dismiss();
            SharedPreferenceUtil.saveAccessToken(response);
            SharedPreferenceUtil.setLoggedIn(true);
            BaseActionBarActivity.hideKeyboard(context,rootView.findViewById(R.id.signin_button));
            navToMain();
        }
    }


}
