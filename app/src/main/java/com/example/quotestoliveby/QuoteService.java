package com.example.quotestoliveby;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface QuoteService {

	@GET("/jokes/random")
	Call<Quote> createQuote(@Query("category") String category);

}
