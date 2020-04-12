package com.example.quotestoliveby;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

	ExtendedFloatingActionButton mBtnShowQuote;
	TextView mTxtQuote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnShowQuote = findViewById(R.id.btnShowQuote);
		mTxtQuote = findViewById(R.id.txtQuote);


		Retrofit.Builder builder = new Retrofit.Builder()
				.baseUrl("https://api.chucknorris.io/")
				.addConverterFactory(GsonConverterFactory.create());

		Retrofit retrofit = builder.build();

		QuoteService quoteService = retrofit.create(QuoteService.class);
		Call<Quote> quoteCall = quoteService.createQuote();

		mBtnShowQuote.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				mTxtQuote.setText("Patience is a virtue...");
				mBtnShowQuote.setText("LOADING");
				quoteCall.clone().enqueue(new Callback<Quote>() {
					@Override
					public void onResponse(Call<Quote> call, Response<Quote> response) {
						String quoteString = response.body().getValue();
						mTxtQuote.setText(quoteString);
						mBtnShowQuote.setText("ANOTHER ONE");

					}

					@Override
					public void onFailure(Call<Quote> call, Throwable t) {
						Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
					}
				});
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