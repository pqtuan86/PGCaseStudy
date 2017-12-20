package com.example.tuanpham.pgcasestudy.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tuanpham on 12/20/17.
 */

public class Item implements Serializable, Parcelable {

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
    @SerializedName("parent")
    private int parent;
    @SerializedName("text")
    private String text;

    private ArrayList<Item> replies;

    public Item(int id) {
        this.id = id;
        replies = new ArrayList<>();
    }

    public Item(Item item) {
        this.id = item.getId();
        this.by = item.getBy();
        this.kids = new ArrayList<>();
        if (item.getKids() != null) {
            this.kids.addAll(item.getKids());
            replies = new ArrayList<>();
        }
        this.parent = item.getParent();
        this.text = item.getText();
        this.time = item.getTime();
        this.type = item.getType();
    }

    public Item(Parcel in) {
        by = in.readString();
        descendants = in.readInt();
        id = in.readInt();
        if (in.readByte() == 0x01) {
            kids = new ArrayList<>();
            in.readList(kids, String.class.getClassLoader());
        } else {
            kids = null;
        }
        score = in.readInt();
        time = in.readLong();
        title = in.readString();
        type = in.readString();
        url = in.readString();
        parent = in.readInt();
        text = in.readString();
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

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Item item = (Item) obj;
            if (this.id == item.getId()) {
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
        dest.writeInt(parent);
        dest.writeString(text);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
