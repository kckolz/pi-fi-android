package pack.wolf.com.pifi.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import pack.wolf.com.pifi.model.User;
import pack.wolf.com.pifi.model.UserRequest;
import pack.wolf.com.pifi.service.AuthenticationServiceFactory;
import pack.wolf.com.pifi.service.api.UserService;
import pack.wolf.com.pifi.service.impl.UserServiceImpl;
import pack.wolf.com.pifi.util.DialogUtil;
import pack.wolf.com.pifi.util.SharedPreferenceUtil;

public class SignUpFragment extends Fragment {

    private static View rootView;
    private Context context;
    private UserRequest userRequest;
    private User user;

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SignUpFragment() {
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        } catch (InflateException e) {
        }

        // set title
        BaseActionBarActivity.setTitle(getString(R.string.signup));

        // get context
        context = inflater.getContext();

        // get user creds
        final EditText username_box = (EditText) rootView.findViewById(R.id.email);
        final EditText password_box = (EditText) rootView.findViewById(R.id.password);
        final EditText password_confirm_box = (EditText) rootView.findViewById(R.id.password_confirm);

        // sign up
        final ProgressDialog dialog = DialogUtil.getProgressDialog(context,getString(R.string.signing_up));
        Button button_signup = (Button) rootView.findViewById(R.id.signup_button);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                final String username = username_box.getText().toString();
                final String password = password_box.getText().toString();
                final String password_confirm = password_confirm_box.getText().toString();

                // create user
                userRequest = new UserRequest();
                userRequest.setEmailAddress(username);
                userRequest.setPassword(password);
                userRequest.setAdmin(true);

                UserService userService = new UserServiceImpl();
                userService.createUser(context, userRequest, dialog, new Response.Listener<User>() {
                    @Override
                    public void onResponse(User response) {
                        AuthenticationServiceFactory.getInstance().login(username, password, context, new Response.Listener<AccessToken>() {

                            @Override
                            public void onResponse(AccessToken response) {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                SharedPreferenceUtil.saveAccessToken(response);
                                SharedPreferenceUtil.setLoggedIn(true);
                                navToMain();
                            }
                        },dialog);
                    }
                });
            }
        });

        return rootView;

    }

    protected void navToMain() {

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack(AppConstants.FRAGMENT_MAIN
                )
                .commit();

    }

}
