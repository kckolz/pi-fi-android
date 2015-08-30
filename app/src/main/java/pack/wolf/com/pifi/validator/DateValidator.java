package pack.wolf.com.pifi.validator;

import android.widget.EditText;

public class DateValidator {

    private static final String DATE_REGEX = "^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$";

    private EditText date;

    public DateValidator(EditText editText) {
        date = editText;
    }

    public boolean validate() {

        String text = date.getText().toString();

        if (!isValidEmail(text) && text != "") {
            date.setError("DOB must be in format yyyy-mm-dd");
            return false;
        } else {
            date.setError(null);
            return true;
        }
    }

    private boolean isValidEmail(String s) {
        return s.matches(DATE_REGEX);
    }
}
