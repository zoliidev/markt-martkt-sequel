package com.example.beadando;

import static com.example.beadando.kollekcio.WebRequest.sendWebRequest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button Bt;
    private String st;

    //private JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bt = findViewById(R.id.loginButton);
        EditText username = findViewById(R.id.textBox);
        EditText pass = findViewById(R.id.passBox);
        Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", username.getText().toString());
                params.put("password", pass.getText().toString());

                String result = sendWebRequest("https://oldal.vaganyzoltan.hu/api/login.php", params);
                if (result == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
                    builder.setPositiveButton(R.string.ok, null);
                    AlertDialog dialog = builder.create();

                    dialog.show();
                }else
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
    }
}
