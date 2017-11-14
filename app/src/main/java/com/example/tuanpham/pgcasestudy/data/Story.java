package com.example.tuanpham.pgcasestudy.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tuanpham on 11/4/17.
 */

public class Story implements Serializable, Parcelable {
    /*
    "by" : "dhouston",
            "descendants" : 71,
            "id" : 8863,
            "kids" : [ 8952, 9224, 8917, 8884, 8887, 8943, 8869, 8958, 9005, 9671, 8940, 9067, 8908, 9055, 8865, 8881, 8872, 8873, 8955, 10403, 8903, 8928, 9125, 8998, 8901, 8902, 8907, 8894, 8878, 8870, 8980, 8934, 8876 ],
            "score" : 111,
            "time" : 1175714200,
            "title" : "My YC app: Dropbox - Throw away your USB drive",
            "type" : "story",
            "url" : "http://www.getdropbox.com/u/2/screencast.html"
    */

    private static final long serialVersionUID = 1L;
    @SerializedName("by")
    private String by;
    @SerializedName("descendants")
    private int descendants;
    @SerializedName("id")
    private int id;
    @SerializedName("kids")
    private ArrayList<Integer> kids;
    @SerializedName("score")
    private int score;
    @SerializedName("time")
    private long time;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;
    @SerializedName("url")
    private String url;

    public Story(int id) {
        this.id = id;
    }

    public Story(Parcel in) {
        by = in.readString();
        descendants = in.readInt();
        id = in.readInt();
        if (in.readByte() == 0x01) {
            kids = new ArrayList<>();
            in.readList(kids, String.class.getClassLoader());
        } else {
            kids = null;
        }
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Integer getDescendants() {
        return descendants;
    }

    public void setDescendants(Integer descendants) {
        this.descendants = descendants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getKids() {
        return kids;
    }

    public void setKids(ArrayList<Integer> kids) {
        this.kids = kids;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Story) {
            Story story = (Story) obj;
            if (this.id == story.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(by);
        dest.writeInt(descendants);
        dest.writeInt(id);
        if (kids == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(kids);
        }
        dest.writeInt(score);
        dest.writeLong(time);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<Story> CREATOR = new Parcelable.Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };
}
