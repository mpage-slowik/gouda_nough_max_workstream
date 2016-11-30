package com.nough.gouda.goudanough.beans;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Evan on 11/21/2016.
 */
public class Restaurant implements Parcelable {

    private int id;//
    private String name;//
    private String url;//
    private String cuisine;//
    private String phone_numbers;//
    private int price_range;//
    private double latitude;//
    private double longitude;//
    private String featured_image;//
    private String rating;
    private List<Comment> comments;

    /**
     * Default Constructor.
     */
    public Restaurant(){}

    public Restaurant(String name, String url, String cuisine,String phone_numbers, int price_range, double latitude, double longitude, String img_src){
        this.name = name;
        this.url = url;
        this.cuisine = cuisine;
        this.phone_numbers = phone_numbers;
        this.price_range = price_range;
        this.latitude = latitude;
        this.longitude = longitude;
        this.featured_image = img_src;
    }

    public String getPhone_numbers() {
        return phone_numbers;
    }

    public void setPhone_numbers(String phone_numbers) {
        this.phone_numbers = phone_numbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public int getPrice_range() {
        return price_range;
    }

    public void setPrice_range(int price_range) {
        this.price_range = price_range;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFeatured_image() {
        return featured_image;
    }

    public void setFeatured_image(String featured_image) {
        this.featured_image = featured_image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    protected Restaurant(Parcel in) {
        name = in.readString();
        url = in.readString();
        cuisine = in.readString();
        phone_numbers = in.readString();
        price_range = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        featured_image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(cuisine);
        dest.writeString(phone_numbers);
        dest.writeInt(price_range);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(featured_image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}