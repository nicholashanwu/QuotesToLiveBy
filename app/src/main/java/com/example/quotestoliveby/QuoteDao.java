package com.example.quotestoliveby;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuoteDao {
	@Query("SELECT * FROM quote")
	public List<Quote> getAll();

	@Query("SELECT * FROM quote WHERE id LIKE :quoteid")
	public List<Quote> findQuoteById(String quoteid);

	@Insert
	void insertAll(Quote... quotes);

	@Delete
	void deleteQuote(Quote quote);
}
