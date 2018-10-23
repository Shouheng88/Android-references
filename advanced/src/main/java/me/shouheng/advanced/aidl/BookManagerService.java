package me.shouheng.advanced.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Observable;

/**
 * @author shouh
 * @version $Id: BookManagerService, v 0.1 2018/10/23 21:20 shouh Exp$
 */
public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";

    private CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<>();

    private Binder binder = new IBookManager.Stub() {
        @Override
        public Book getBook(long id) {
            return Observable.fromIterable(books).filter(book -> book.id == id).singleOrError().blockingGet();
        }

        @Override
        public void addBook(long id, String name) {
            books.add(new Book(id, name));
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        books.add(new Book(100, "Book 100"));
        books.add(new Book(101, "Book 101"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
