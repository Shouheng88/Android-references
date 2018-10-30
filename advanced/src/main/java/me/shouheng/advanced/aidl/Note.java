package me.shouheng.advanced.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author shouh
 * @version $Id: Note, v 0.1 2018/10/22 22:27 shouh Exp$
 */
public class Note implements Parcelable {

    public final long id;

    public final String name;

    public Note(long id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Note(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public String toString() {
        return "Note{" +
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
