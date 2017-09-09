
package com.example.slapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KosaKata implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("link")
    @Expose
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.link);
    }

    public KosaKata() {
    }

    protected KosaKata(Parcel in) {
        this.id = in.readString();
        this.nama = in.readString();
        this.link = in.readString();
    }

    public static final Parcelable.Creator<KosaKata> CREATOR = new Parcelable.Creator<KosaKata>() {
        @Override
        public KosaKata createFromParcel(Parcel source) {
            return new KosaKata(source);
        }

        @Override
        public KosaKata[] newArray(int size) {
            return new KosaKata[size];
        }
    };
}
