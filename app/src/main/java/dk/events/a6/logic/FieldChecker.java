package dk.events.a6.logic;

import android.app.Activity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FieldChecker {
    private String firstName, lastName, birthDate, email, password, gender;
    private EditText editText_password;
    private int selected;
    private RadioButton radioButton;
    private Activity activity;

    Boolean status = false;

    public FieldChecker(Activity activity) {
        this.activity = activity;
    }

    public FieldChecker() {
    }

    public Boolean allFieldsEmpty(Activity activity, EditText firstName, EditText lastName, EditText birthDate,
                                  EditText email, EditText password){
        this.editText_password = password;
        this.firstName = firstName.getText().toString();
        this.lastName = lastName.getText().toString();
        this.birthDate = birthDate.getText().toString();
        this.email = email.getText().toString();
        this.password = password.getText().toString();
        if (
                this.firstName.isEmpty() &&
                this.lastName.isEmpty() &&
                this.birthDate.isEmpty() &&
                this.email.isEmpty() &&
                this.password.isEmpty()
        ){
            firstName.setError("First name is empty!");
            lastName.setError("Last name is empty!");
            birthDate.setError("Birth date is empty!");
            email.setError("Email is empty!");
            password.setError("Password is empty!");
            Toast.makeText(activity, "All fields seem to be empty!", Toast.LENGTH_SHORT).show();
            return true;
        }else return false;
    }

    public Boolean someFieldsEmpty(Activity activity, EditText firstName, EditText lastName, EditText birthDate,
                          EditText email, EditText password){
        this.editText_password = password;
        if (firstName.getText().toString().isEmpty())
            firstName.setError("First name is empty!");
        if (lastName.getText().toString().isEmpty())
            lastName.setError("Last name is empty!");
        if (birthDate.getText().toString().isEmpty())
            birthDate.setError("Birth date is empty!");
        if (email.getText().toString().isEmpty())
            email.setError("Email address is empty!");
        if (password.getText().toString().isEmpty())
            password.setError("Password is empty!");

        if (
                firstName.getText().toString().isEmpty() ||
                lastName.getText().toString().isEmpty() ||
                birthDate.getText().toString().isEmpty() ||
                email.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty()
        ){
            Toast.makeText(activity, "Some field/s are empty!", Toast.LENGTH_SHORT).show();
            return true;
        } else return false;
    }

    public Boolean passwordCheck(Activity activity, EditText password){
        this.password = password.getText().toString();
        if (password.length() <6){
            this.editText_password.setError("Password cannot be less than 6 characters");
            return true;
        }else if (password.length() > 10){
            this.editText_password.setError("Password cannot be greater than 10 characters");
            return true;
        }else return false;
    }

    public Boolean genderCheck(RadioGroup radioGroup){
        this.selected = radioGroup.getCheckedRadioButtonId();
        if (selected == -1){
            Toast.makeText(activity, "Gender not selected", 0).show();
            return true;
        } else {
            this.radioButton = activity.findViewById(selected);
            this.gender = radioButton.getText().toString();
            return false;
        }
    }

    public Boolean checkEmailAndPassword(Activity activity, EditText email, EditText password){
        if (email.getText().toString().isEmpty())
            email.setError("Email address is empty!");
        if (password.getText().toString().isEmpty())
            password.setError("Password is empty!");
        if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
            Toast.makeText(activity, "All fields seem to be empty!", Toast.LENGTH_SHORT).show();
            return true;
        }else if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(activity, "Some field/s are empty!", Toast.LENGTH_SHORT).show();
            return true;
        }else return false;
    }



    public Boolean isEmpty(EditText [] fields, String [] errorMessage){
            for (int ii = 0; ii < fields.length; ii++) {
                if (fields[ii].getText().toString().isEmpty()) {
                    fields[ii].setError(errorMessage[ii]);
                }
            }
            for (int iii = 0; iii < fields.length; iii++) {
                if (fields[iii].getText().toString().isEmpty()) {
                    Toast.makeText(this.activity, "Some field/s are empty", 0).show();
                    status = true;
                    return true;
                }else status = false;
            }
        return status;
    }

    public String getGender(){
        return this.gender;
    }

}
