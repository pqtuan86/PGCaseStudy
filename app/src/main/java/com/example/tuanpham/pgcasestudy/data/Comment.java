package com.example.tuanpham.pgcasestudy.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tuanpham on 11/14/17.
 */

public class Comment implements Serializable, Parcelable{

    /* Sample
    {
      "by" : "norvig",
      "id" : 2921983,
      "kids" : [ 2922097, 2922429, 2924562, 2922709, 2922573, 2922140, 2922141 ],
      "parent" : 2921506,
      "text" : "Aw shucks, guys ... you make me blush with your compliments.<p>Tell you what, Ill make a deal: I'll keep writing if you keep reading. K?",
      "time" : 1314211127,
      "type" : "comment"
    }
     */

    private static final long serialVersionUID = 1L;
    @SerializedName("by")
    private String by;
    @SerializedName("id")
    private int id;
    @SerializedName("kids")
    private ArrayList<Integer> kids;
    @SerializedName("parent")
    private int parent;
    @SerializedName("text")
    private String text;
    @SerializedName("time")
    private long time;
    @SerializedName("type")
    private String type;

    private ArrayList<Comment> replies;

    public Comment(int commentID) {
        this.id = commentID;
        replies = new ArrayList<>();
    }

    public Comment(Comment comment) {
        this.id = comment.getId();
        this.by = comment.getBy();
        this.kids = new ArrayList<>();
        if (comment.getKids() != null) {
            this.kids.addAll(comment.getKids());
            replies = new ArrayList<>();
        }
        this.parent = comment.getParent();
        this.text = comment.getText();
        this.time = comment.getTime();
        this.type = comment.getType();
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Comment> getReplies() {
        return replies;
    }

    public void setReplies(ArrayList<Comment> replies) {
        this.replies = replies;
    }

    public void addReply(Comment reply) {
        replies.add(reply);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Comment) {
            Comment comment = (Comment) obj;
            if (this.id == comment.getId()) {
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
        dest.writeInt(id);
        if (kids == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(kids);
        }
        dest.writeInt(parent);
        dest.writeString(text);
        dest.writeLong(time);
        dest.writeString(type);
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
