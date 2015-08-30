package pack.wolf.com.pifi.validator;

import android.widget.EditText;

public class PasswordValidator {

    private EditText passwordEditText;
    private EditText retypePasswordEditText;

    public PasswordValidator(EditText passwordEditText, EditText retypePasswordEditText) {
        this.passwordEditText = passwordEditText;
        this.retypePasswordEditText = retypePasswordEditText;
    }

    public boolean validate() {

        if (passwordEditText.getText() != null) {
            //set error on 1st password input if length is less than 6 characters
            String password = passwordEditText.getText().toString();
            if (password.length() < 6) {
                passwordEditText.setError("Password must be at least 6 characters.");
                return false;
            } else {
                passwordEditText.setError(null);
            }

            //set error on retype field if not null and don't match
            if (retypePasswordEditText.getText() != null) {
                String retypePassword = retypePasswordEditText.getText().toString();
                if (!retypePassword.equals(password) && !retypePassword.equals("")) {
                    retypePasswordEditText.setError("Password does not match.");
                    return false;
                } else {
                    retypePasswordEditText.setError(null);
                    return true;
                }
            }
        }

        return false;
    }
}
