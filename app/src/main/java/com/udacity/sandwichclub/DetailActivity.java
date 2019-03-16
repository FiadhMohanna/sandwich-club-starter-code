package com.udacity.sandwichclub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    public Sandwich sandwich = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            closeOnError();
            return;
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        Uri uri;
        ImageView imageView = findViewById(R.id.image_iv);
        TextView lblAKA = findViewById(R.id.also_known_lbl),
                 mAKA = findViewById(R.id.also_known_tv),
                 lblPOO = findViewById(R.id.origin_lbl),
                 mPOO = findViewById(R.id.origin_tv),
                 lblDescription = findViewById(R.id.description_lbl),
                 mDescription = findViewById(R.id.description_tv),
                 lblIngredients = findViewById(R.id.ingredients_lbl),
                 mIngredients = findViewById(R.id.ingredients_tv);
        mAKA.setText("");
        mIngredients.setText("");
        uri = Uri.parse(sandwich.getImage());
        imageView.setImageURI(uri);
        setFieldList(lblAKA, mAKA, sandwich.getAlsoKnownAs());
        setFieldText(lblPOO, mPOO, sandwich.getPlaceOfOrigin());
        setFieldText(lblDescription, mDescription, sandwich.getDescription());
        setFieldList(lblIngredients, mIngredients, sandwich.getIngredients());
    }
    public void setFieldText(TextView label, TextView field, String text){
        label.setVisibility(View.VISIBLE);
        field.setVisibility(View.VISIBLE);
        if (text == null || text.equals("")){
            label.setVisibility(View.GONE);
            field.setVisibility(View.GONE);
        } else
            field.setText(text);
    }
    public void setFieldList(TextView label, TextView field, List<String> list){
        field.setText("");
        label.setVisibility(View.VISIBLE);
        field.setVisibility(View.VISIBLE);
        if (list == null || list.size() == 0){
            label.setVisibility(View.GONE);
            field.setVisibility(View.GONE);
        } else
            for (String text : list)
                field.append(" - " + text + "\n");
    }
}
