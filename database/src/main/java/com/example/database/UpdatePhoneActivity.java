package com.example.database;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.database.data.Phone;

public class UpdatePhoneActivity extends AppCompatActivity {
    private EditText manufacturer_field, model_field, androidVersion_field, webSite_field;
    public static final String PHONE_OUT_KEY = "com.example.database.PHONE_OUT_KEY";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_phone_activity);

        Button button_Cancel = findViewById(R.id.button_Cancel);
        Button button_Update = findViewById(R.id.button_save);
        Button button_www = findViewById(R.id.button_WebSite);

        manufacturer_field = findViewById(R.id.editTextManufacturer);
        model_field = findViewById(R.id.editTextModel);
        androidVersion_field = findViewById(R.id.editTextAndroidVersion);
        webSite_field = findViewById(R.id.editTextWebSite);

        Intent intentIN = getIntent();
        Phone phone = intentIN.getParcelableExtra(MainActivity.PHONE_KEY);

        manufacturer_field.setText(phone.mProducent);
        model_field.setText(phone.mModel);
        androidVersion_field.setText(phone.mAndroidVersion);
        webSite_field.setText(phone.mStronaWWW);

        button_Cancel.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        button_www.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(phone.mStronaWWW))));

        button_Update.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            if (!manufacturer_field.getText().toString().isEmpty() && !model_field.getText().toString().isEmpty() && !androidVersion_field.getText().toString().isEmpty() && !webSite_field.getText().toString().isEmpty()) {
                if (!URLUtil.isValidUrl(webSite_field.getText().toString())) {
                    Toast.makeText(getApplicationContext(), getString(R.string.validUrl), Toast.LENGTH_LONG).show();
                } else {

                    Phone phoneOut = new Phone(phone.mPhone_id,
                            manufacturer_field.getText().toString(),
                            model_field.getText().toString(),
                            androidVersion_field.getText().toString(),
                            webSite_field.getText().toString());
                    bundle.putParcelable(PHONE_OUT_KEY, phoneOut);

                    Intent intentOut = new Intent();
                    intentOut.putExtras(bundle);
                    setResult(RESULT_FIRST_USER + 1, intentOut);
                    finish();
                }
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.emptyFields), Toast.LENGTH_LONG).show();
            }
        });

    }

}
