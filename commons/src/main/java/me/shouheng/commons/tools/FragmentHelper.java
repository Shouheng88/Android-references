package me.shouheng.commons.tools;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by WngShhng on 2018/6/7.*/
public class FragmentHelper {

    public static void replace(AppCompatActivity activity, Fragment fragment, @IdRes int containerId) {
        replace(activity, fragment, containerId, false);
    }

    public static void replaceWithCallback(AppCompatActivity activity, Fragment fragment, @IdRes int containerId) {
        replace(activity, fragment, containerId, true);
    }

    public static void replace(AppCompatActivity activity, android.app.Fragment fragment, @IdRes int containerId) {
        replace(activity, fragment, containerId, false);
    }

    public static void replaceWithCallback(AppCompatActivity activity, android.app.Fragment fragment, @IdRes int containerId) {
        replace(activity, fragment, containerId, true);
    }

    private static void replace(AppCompatActivity activity, android.app.Fragment fragment, @IdRes int containerId, boolean backStack) {
        if (activity.isFinishing()) return;
        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (backStack) transaction.addToBackStack(null);
        transaction.replace(containerId, fragment).commit();
    }

    private static void replace(AppCompatActivity activity, Fragment fragment, @IdRes int containerId, boolean backStack) {
        if (activity.isFinishing()) return;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (backStack) transaction.addToBackStack(null);
        transaction.replace(containerId, fragment).commit();
    }
}
