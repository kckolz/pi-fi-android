package pack.wolf.com.pifi.service.api;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;

import pack.wolf.com.pifi.model.AccessToken;

public interface AuthenticationService {

    void login(final String userName, final String password, final Context context,
               final Response.Listener<AccessToken> response, Dialog dialog);

    void logout(final Context context, FragmentManager fragmentManager);
}
