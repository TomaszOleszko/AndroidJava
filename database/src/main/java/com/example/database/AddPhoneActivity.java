package com.example.database;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddPhoneActivity extends AppCompatActivity {

    private Button button_Cancel, button_Save, button_www;
    private EditText manufacturer_field, model_field, androidVersion_field, webSite_field;
    public static final String MANUFACTURER_KEY = "com.example.database.MANUFACTURER_KEY";
    public static final String MODEL_KEY = "com.example.database.MODEL_KEY";
    public static final String ANDROID_VERSION_KEY = "com.example.database.ANDROID_VERSION_KEY";
    public static final String WEB_SITE_KEY = "com.example.database.WEB_SITE_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_phone_activity);

        fields_setup();

    }

    private void fields_setup() {
        button_Cancel = findViewById(R.id.button_Cancel);
        button_Save = findViewById(R.id.button_save);
        button_www = findViewById(R.id.button_WebSite);

        manufacturer_field = findViewById(R.id.editTextManufacturer);
        model_field = findViewById(R.id.editTextModel);
        androidVersion_field = findViewById(R.id.editTextAndroidVersion);
        webSite_field = findViewById(R.id.editTextWebSite);

        button_Cancel.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        button_Save.setOnClickListener(view -> {

            if (!manufacturer_field.getText().toString().isEmpty() && !model_field.getText().toString().isEmpty() && !androidVersion_field.getText().toString().isEmpty() && !webSite_field.getText().toString().isEmpty()) {
                if (!URLUtil.isValidUrl(webSite_field.getText().toString())) {
                    Toast.makeText(getApplicationContext(), getString(R.string.validUrl), Toast.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(MANUFACTURER_KEY, manufacturer_field.getText().toString());
                    bundle.putString(MODEL_KEY, model_field.getText().toString());
                    bundle.putString(ANDROID_VERSION_KEY, androidVersion_field.getText().toString());
                    bundle.putString(WEB_SITE_KEY, webSite_field.getText().toString());

                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.emptyFields), Toast.LENGTH_LONG).show();
            }
        });
    }
}
