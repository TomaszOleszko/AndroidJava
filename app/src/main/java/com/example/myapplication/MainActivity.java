package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText firstnameField, lastnameField, gradesCountField;
    Button gradesButton;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String firstNameInput = (String) firstnameField.getText().toString();
            String lastNameInput = (String) lastnameField.getText().toString();
            String gradesCountInput = gradesCountField.getText().toString();

            if(!firstNameInput.isEmpty() && !lastNameInput.isEmpty() && gradesCountInput.length() > 0){
                if (Integer.parseUnsignedInt(gradesCountInput) >= 5 && Integer.parseUnsignedInt(gradesCountInput) <= 15) {
                    gradesButton.setVisibility(View.VISIBLE);
                }
            }else{
                gradesButton.setVisibility(View.INVISIBLE);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstnameField = (EditText) findViewById(R.id.editTextTextPersonName1);
        lastnameField = (EditText) findViewById(R.id.editTextTextPersonName2);
        gradesCountField = findViewById(R.id.editTextNumberSigned3);
        gradesButton = (Button) findViewById(R.id.button);

        firstnameField.addTextChangedListener(textWatcher);
        lastnameField.addTextChangedListener(textWatcher);
        gradesCountField.addTextChangedListener(textWatcher);

        firstnameField.setOnFocusChangeListener((view, hasFocus) -> checkField(hasFocus,"Imię nie może być puste",firstnameField));
        lastnameField.setOnFocusChangeListener((view, hasFocus) -> checkField(hasFocus,"Nazwisko nie może być puste",lastnameField));
        gradesCountField.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus){
                if(gradesCountField.getText().toString().isEmpty()){
                    String msg = "Liczba ocen nie może być pusta";
                    Toast.makeText(MainActivity.this,
                            msg,
                            Toast.LENGTH_SHORT).show();
                    gradesCountField.setError(msg);
                }
                else{
                    if(Integer.parseInt(gradesCountField.getText().toString()) < 5 || Integer.parseInt(gradesCountField.getText().toString()) > 15){
                        String msg = "Liczba ocen musi być z przedziału [5,15]";
                        Toast.makeText(MainActivity.this,
                                msg,
                                Toast.LENGTH_SHORT).show();
                        gradesCountField.setError(msg);
                    }
                }
            }
        });

    }

    private void checkField(boolean hasFocus, String msg, EditText textField) {
        if(!hasFocus){
            Toast.makeText(MainActivity.this,
                    msg,
                    Toast.LENGTH_SHORT).show();
            if(textField.getText().toString().isEmpty()){
                textField.setError(msg);
            }
        }
    }
}