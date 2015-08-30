package pack.wolf.com.pifi.validator;

import android.widget.EditText;

public class EmailValidator {

    private EditText email;

    public EmailValidator(EditText editText) {
        email = editText;
    }

    public boolean validate() {

        String text = email.getText().toString();

        if (!isValidEmail(text) && text != "") {
            email.setError("Please enter a valid email address.");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean isValidEmail(String s) {
        String emailRegex = "^[_A-Za-z0-9-+.$]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return s.matches(emailRegex);
    }
}
