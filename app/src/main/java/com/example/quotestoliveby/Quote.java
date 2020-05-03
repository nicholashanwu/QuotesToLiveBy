package com.example.quotestoliveby;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Quote {


	@SerializedName("created_at")
	@Expose
	private String createdAt;
	@SerializedName("icon_url")
	@Expose
	private String iconUrl;
	@PrimaryKey
	@NonNull
	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("updated_at")
	@Expose
	private String updatedAt;
	@SerializedName("url")
	@Expose
	private String url;
	@ColumnInfo(name = "quote_string")
	@SerializedName("value")
	@Expose
	private String value;



	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}