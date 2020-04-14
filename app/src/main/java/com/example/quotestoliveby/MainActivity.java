package com.example.quotestoliveby;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ExtendedFloatingActionButton mBtnShowQuote;
    FloatingActionButton mBtnCopy;
    TextView mTxtQuote;
    String quoteString = "";
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnShowQuote = findViewById(R.id.btnShowQuote);
        mBtnCopy = findViewById(R.id.btnCopy);
        mTxtQuote = findViewById(R.id.txtQuote);

        //hiding the action bar for a cleaner look
        getSupportActionBar().hide();

        //an array of all the joke categories offered by the API
        String[] categories = new String[]{
                "animal",
                "career",
                "celebrity",
                "dev",
                "explicit",
                "fashion",
                "food",
                "history",
                "money",
                "movie",
                "music",
                "political",
                "religion",
                "science",
                "sport",
                "travel"
        };

        //creating an arrayAdapter to adapt the array above to the dropdown menu
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, categories);

        //setting the adapter, the hint, and disabling text input
        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.spinner);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setHint("choose one");
        editTextFilledExposedDropdown.setInputType(0);

        //changing the String value stored in category
        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                category = adapterView.getItemAtPosition(i).toString();
            }
        });

        //initializing Retrofit by supplying it a baseUrl to work with and specifying that it will use Gson
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        QuoteService quoteService = retrofit.create(QuoteService.class);


        mBtnShowQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //passes the category selected by the spinner to the Call
                Call<Quote> quoteCall = quoteService.createQuote(category);

                //fancy animations
                YoYo.with(Techniques.FadeOutDown).duration(200).playOn(mTxtQuote);
                mTxtQuote.setText("patience is a virtue...");
                YoYo.with(Techniques.FadeInDown).duration(200).playOn(mTxtQuote);
                mBtnShowQuote.setText("loading...");

                // .clone() allows for the service to be called more than once, i.e. repeatedly
                quoteCall.clone().enqueue(new Callback<Quote>() {
                    @Override
                    public void onResponse(Call<Quote> call, Response<Quote> response) {

                        //converts it to lowercase for #AESTHETICS
                        quoteString = response.body().getValue().toLowerCase();
                        mTxtQuote.setText(quoteString);
                        YoYo.with(Techniques.FadeInDown).duration(200).playOn(mTxtQuote);
                        mBtnShowQuote.setText("tap for more");
                    }

                    @Override
                    public void onFailure(Call<Quote> call, Throwable t) {
                        //notifies the user in case of error
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        mBtnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quoteString.equals("")) {
                    Toast.makeText(MainActivity.this, "Nothing copied! Press the button first", Toast.LENGTH_LONG).show();
                } else {
                    //copies the quote to the clipboard if it exists
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", quoteString);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(MainActivity.this, "Copied!", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}