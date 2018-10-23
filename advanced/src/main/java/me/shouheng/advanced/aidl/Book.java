package me.shouheng.advanced.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author shouh
 * @version $Id: Book, v 0.1 2018/10/22 22:27 shouh Exp$
 */
public class Book implements Parcelable {

    public final long id;

    public final String name;

    public Book(long id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Book(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }
}
