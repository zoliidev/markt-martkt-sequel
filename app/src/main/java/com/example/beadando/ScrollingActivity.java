package com.example.beadando;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.beadando.kollekcio.WebRequest;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beadando.databinding.ActivityScrollingBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ScrollingActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String prodId = getIntent().getStringExtra("prodId");

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HashMap<String, String> webRequest = new HashMap<>();
        webRequest.put("id", prodId);
        webRequest.put("token", "0");
        String result = WebRequest.sendWebRequest("https://oldal.vaganyzoltan.hu/api/product.php", webRequest);

        JSONObject obj = null;
        try {

            obj = new JSONObject(result);

            Log.d("My App", obj.toString());

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
        }

        String prodName = null;
        String prodPrice = null;
        String prodDesc = null;
        String prodIMG = null;
        try {
            prodName = obj.get("name").toString();
            prodPrice = obj.get("price").toString();
            prodDesc = obj.get("description").toString();
            prodIMG = obj.get("img").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        toolBarLayout.setTitle(prodName);

        TextView desc = findViewById(R.id.prod);
        String teszt = prodPrice + "\n" + prodDesc;
        desc.setText(teszt);



        try {
            ImageView i = (ImageView)findViewById(R.id.tesztkep);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL("https://oldal.vaganyzoltan.hu/prod-img/" + prodIMG).getContent());
            i.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}