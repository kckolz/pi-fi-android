package pack.wolf.com.pifi.validator;

import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

public class SimpleTextValidator {

    private EditText editText;

    public SimpleTextValidator(EditText editText) {
        this.editText = editText;
    }

    public Boolean validate() {
        String text = editText.getText().toString();
        if (StringUtils.isNotBlank(text)) {
            editText.setError(null);
            return true;
        } else {
            editText.setError("This field is required.");
            return false;
        }
    }
}