package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText firstnameField, lastnameField, gradesCountField;
    private Button gradesButton, resultButton;
    public static final String GRADES_COUNT_KEY =
            "com.example.w4_two_activities_and.GRADES_COUNT_KEY";
    public static final String FIRSTNAMEFIELD_KEY =
            "com.example.w4_two_activities_and.FIRSTNAMEFIELD_KEY";
    public static final String LASTNAMEFIELD_KEY =
            "com.example.w4_two_activities_and.LASTNAMEFIELD_KEY";
    public static final String GRADESCOUNTFIELD_KEY =
            "com.example.w4_two_activities_and.GRADESCOUNTFIELD_KEY";
    public static final String MEANBUTTON_KEY =
            "com.example.w4_two_activities_and.MEANBUTTON_KEY";
    public static final String MEANBUTTONVISIBILITY_KEY =
            "com.example.w4_two_activities_and.MEANBUTTONVISIBILITY_KEY";


    private ActivityResultLauncher<Intent> mActivityResultLauncher;


    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String firstNameInput = firstnameField.getText().toString();
            String lastNameInput = lastnameField.getText().toString();
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

        setUpFields();

        gradesButton.setOnClickListener(v -> startGradesActivity());

        mActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if ( result.getResultCode() == RESULT_OK && result.getData() != null){
                        Bundle bundle = result.getData().getExtras();
                        String srednia = bundle.getString(GradesActivity.MEAN_KEY);

                        resultButton.setVisibility(View.VISIBLE);
                        String resultString = (Double.parseDouble(srednia) >= 3.0) ? getString(R.string.nice) : getString(R.string.glhf);
                        resultButton.setText(resultString);
                        resultButton.setOnClickListener(view -> {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle(R.string.app_name);
                            builder.setIcon(R.mipmap.ic_launcher);
                            String res = (Double.parseDouble(srednia) >= 3.0) ? getString(R.string.congrats) : getString(R.string.seeYouNextYear);
                            builder.setMessage(res)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", (dialog, id) -> finish());
                            AlertDialog alert = builder.create();
                            alert.show();
                        });

                    }
                }
        );


    }

    private void startGradesActivity() {
        Intent intent = new Intent(this, GradesActivity.class);
        intent.putExtra(GRADES_COUNT_KEY, gradesCountField.getText().toString());
        mActivityResultLauncher.launch(intent);

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(FIRSTNAMEFIELD_KEY, firstnameField.getText().toString());
        outState.putString(LASTNAMEFIELD_KEY, lastnameField.getText().toString());
        outState.putString(GRADESCOUNTFIELD_KEY, gradesCountField.getText().toString());
        outState.putString(MEANBUTTON_KEY, resultButton.getText().toString());
        outState.putInt(MEANBUTTONVISIBILITY_KEY, resultButton.getVisibility());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        firstnameField.setText(savedInstanceState.getString(FIRSTNAMEFIELD_KEY));
        lastnameField.setText(savedInstanceState.getString(LASTNAMEFIELD_KEY));
        gradesCountField.setText(savedInstanceState.getString(GRADESCOUNTFIELD_KEY));
        resultButton.setText(savedInstanceState.getString(MEANBUTTON_KEY));
        resultButton.setVisibility(savedInstanceState.getInt(MEANBUTTONVISIBILITY_KEY));
    }

    private void setUpFields() {
        firstnameField = findViewById(R.id.editTextTextPersonName1);
        lastnameField = findViewById(R.id.editTextTextPersonName2);
        gradesCountField = findViewById(R.id.editTextNumberSigned3);
        gradesButton = findViewById(R.id.button);
        resultButton = findViewById(R.id.resultButton);

        firstnameField.addTextChangedListener(textWatcher);
        lastnameField.addTextChangedListener(textWatcher);
        gradesCountField.addTextChangedListener(textWatcher);

        firstnameField.setOnFocusChangeListener((view, hasFocus) -> checkField(hasFocus,getString(R.string.firstNameEmpty),firstnameField));
        lastnameField.setOnFocusChangeListener((view, hasFocus) -> checkField(hasFocus,getString(R.string.lastNameEmpty),lastnameField));
        gradesCountField.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus){
                if(gradesCountField.getText().toString().isEmpty()){
                    String msg = getString(R.string.gradesCountEmpty);
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    gradesCountField.setError(msg);
                }
                else{
                    if(Integer.parseInt(gradesCountField.getText().toString()) < 5 || Integer.parseInt(gradesCountField.getText().toString()) > 15){
                        String msg = getString(R.string.gradesCountRange);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        gradesCountField.setError(msg);
                    }
                }
            }
        });
    }

    private void checkField(boolean hasFocus, String msg, EditText textField) {
        if(!hasFocus){
            if(textField.getText().toString().isEmpty()){
                textField.setError(msg);
                Toast.makeText(MainActivity.this,
                        msg,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}