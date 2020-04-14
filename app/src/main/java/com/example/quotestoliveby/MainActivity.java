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

        getSupportActionBar().hide();

        //////////////
        String[] COUNTRIES = new String[]{
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
				"travel"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        COUNTRIES);

        AutoCompleteTextView editTextFilledExposedDropdown =
                findViewById(R.id.spinner);
        editTextFilledExposedDropdown.setAdapter(adapter);
		editTextFilledExposedDropdown.setHint("choose one");
		editTextFilledExposedDropdown.setInputType(0);

		editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				category = adapterView.getItemAtPosition(i).toString();
			}
		});




        //editTextFilledExposedDropdown.setText("dev");
		///////////////


        mBtnShowQuote = findViewById(R.id.btnShowQuote);
        mBtnCopy = findViewById(R.id.btnCopy);
        mTxtQuote = findViewById(R.id.txtQuote);


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        QuoteService quoteService = retrofit.create(QuoteService.class);



        mBtnShowQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				Call<Quote> quoteCall = quoteService.createQuote(category);
				YoYo.with(Techniques.FadeOutDown).duration(200).playOn(mTxtQuote);
                mTxtQuote.setText("patience is a virtue...");
                YoYo.with(Techniques.FadeInDown).duration(200).playOn(mTxtQuote);
                mBtnShowQuote.setText("loading...");
                quoteCall.clone().enqueue(new Callback<Quote>() {
                    @Override
                    public void onResponse(Call<Quote> call, Response<Quote> response) {
                        quoteString = response.body().getValue().toLowerCase();
                        mTxtQuote.setText(quoteString);
                        YoYo.with(Techniques.FadeInDown).duration(200).playOn(mTxtQuote);
                        mBtnShowQuote.setText("tap for more");
                    }

                    @Override
                    public void onFailure(Call<Quote> call, Throwable t) {
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
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", quoteString);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(MainActivity.this, "Copied!", Toast.LENGTH_LONG).show();

                }
            }
        });


    }


    private void deserialize() {
        String userJson = "{\"categories\":[\"dev\"],\"created_at\":\"2020-01-05 13:42:19.324003\",\"icon_url\":\"https://assets.chucknorris.host/img/avatar/chuck-norris.png\",\"id\":\"jfbsb24mtawqb-s5zlx8mg\",\"updated_at\":\"2020-01-05 13:42:19.324003\",\"url\":\"https://api.chucknorris.io/jokes/jfbsb24mtawqb-s5zlx8mg\",\"value\":\"Chuck Norris does not code in cycles, he codes in strikes.\"}";
        Gson gson = new Gson();
        Quote quote = gson.fromJson(userJson, Quote.class);

    }
}
//	https://api.chucknorris.io/jokes/random?category=dev