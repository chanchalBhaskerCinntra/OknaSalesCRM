package com.cinntra.okana.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateFavourites implements Parcelable {


    @SerializedName("U_FAV")
    @Expose
    String U_FAV;
    @SerializedName("id")
    @Expose
    String id;

    public UpdateFavourites() {

    }

    public String getU_FAV() {
        return U_FAV;
    }

    public void setU_FAV(String u_FAV) {
        U_FAV = u_FAV;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UpdateFavourites(Parcel in) {

        this.U_FAV = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeValue(U_FAV);
        dest.writeValue(id);


    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UpdateFavourites> CREATOR = new Creator<UpdateFavourites>() {
        @Override
        public UpdateFavourites createFromParcel(Parcel in) {
            return new UpdateFavourites(in);
        }

        @Override
        public UpdateFavourites[] newArray(int size) {
            return new UpdateFavourites[size];
        }
    };
}
