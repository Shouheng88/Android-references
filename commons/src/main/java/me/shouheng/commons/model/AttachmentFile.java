package me.shouheng.commons.model;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by WngShhng on 2018/6/7.*/
public class AttachmentFile implements Serializable {

    private Uri uri;

    private String path;

    private String name;

    private long size;

    private long length;

    private String mineType;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getMineType() {
        return mineType;
    }

    public void setMineType(String mineType) {
        this.mineType = mineType;
    }
}
