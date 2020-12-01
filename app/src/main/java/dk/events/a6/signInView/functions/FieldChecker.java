package dk.events.a6.signInView.functions;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import dk.events.a6.R;
import dk.events.a6.signInView.Sign_Up;

public class FieldChecker {
    private String firstName, lastName, birthDate, email, password, toString;
    private Boolean checker = true;

    private String gender;
    private int selected;
    private RadioButton radioButton;
    private Activity activity;
    private Context context;
    public FieldChecker() {
    }

    public Boolean allFieldsEmpty(Activity activity, EditText firstName, EditText lastName, EditText birthDate,
                           EditText email, EditText password){
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
            this.checker = true;
            return true;
        }else return false;
    }

    public Boolean someFieldsEmpty(Activity activity, EditText firstName, EditText lastName, EditText birthDate,
                          EditText email, EditText password){
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
        }
        else return false;
    }

    public Boolean genderCheck(Activity activity, RadioGroup radioGroup, String gender){
        this.selected = radioGroup.getCheckedRadioButtonId();
        if (selected == -1){
            Toast.makeText(activity, "Gender not selected", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            this.radioButton = activity.findViewById(selected);
            this.gender = radioButton.getText().toString();
            return false;
        }
    }
}
