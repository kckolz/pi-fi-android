package pack.wolf.com.pifi.service.api;

import android.app.Dialog;
import android.content.Context;

import com.android.volley.Response;

import java.io.ByteArrayOutputStream;

import pack.wolf.com.pifi.model.User;
import pack.wolf.com.pifi.model.UserRequest;

public interface UserService {

    void getCurrentUser(Context context, Response.Listener<User> response, Dialog dialog);

    void createUser(final Context context, final UserRequest user, Dialog dialog, Response.Listener<User> responseListener);

    void updateUser(final Context context, final User user, Dialog dialog, Response.Listener<User> responseListener);

    void saveProfilePicture(Context context, ByteArrayOutputStream imageStream, User user, Response.Listener<User> response);

    void saveBlueToothAddress(Context context, User user, Response.Listener<Object> response);

}

