package me.shouheng.libraries;

import org.junit.Test;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RxJavaUnitTest {
    @Test
    public void testJust() throws InterruptedException {
        // subscribe
        Observable.just(1,2,3,4).subscribe(integer -> System.out.print(integer + " "));
        System.out.print("\n\n");

        // all match
        Observable.just(1,2,3,4).all(integer -> integer != null && integer > 2)
                .subscribe(aBoolean -> System.out.print(aBoolean + " "));
        System.out.print("\n\n");

        // any match
        Observable.just(1,2,3,4).any(integer -> integer != null && integer > 2)
                .subscribe(aBoolean -> System.out.print(aBoolean + " "));
        System.out.print("\n\n");

        // map
        Observable.just(1,2,3,4).map(integer -> integer + "->")
                .subscribe(s -> System.out.print(s + " "));
        System.out.print("\n\n");

        // filter
        Observable.fromIterable(Arrays.asList(1,2,3,4)).filter(integer -> integer != null && integer > 2)
                .subscribe(integer -> System.out.print(integer + " "));
        System.out.print("\n\n");

        // thread
        System.out.print(Thread.currentThread() + " ");
        new Thread(() ->
                Observable.fromArray(1,2,3,4,5).map(integer -> {
                    if (integer == 1) System.out.print(Thread.currentThread() + " ");
                    return String.valueOf(integer);
                }).subscribe(s -> {
                    if (s.equals("1")) System.out.print(Thread.currentThread() + " \n\n");
                })
        ).start();
        Thread.sleep(1000);

        // schedulers
        System.out.print(Thread.currentThread() + " ");
        Observable.fromArray(1,2,3,4)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(integer -> {
                    if (integer == 1) System.out.print(Thread.currentThread() + " ");
                    return String.valueOf(integer);
                })
                .subscribe(s -> {
                    if (s.equals("1")) System.out.print(Thread.currentThread() + " \n\n");
                });
        Thread.sleep(1000);



        assertEquals(4, 2 + 2);
    }
}