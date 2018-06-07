package me.shouheng.references.view.intro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

public class AppIntroActivity extends AppIntro {

    public static void launch(Context context) {
        context.startActivity(new Intent(context.getApplicationContext(), AppIntroActivity.class));
    }

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(new IntroSlide1(), getApplicationContext());
        addSlide(new IntroSlide2(), getApplicationContext());
        addSlide(new IntroSlide3(), getApplicationContext());
        addSlide(new IntroSlide4(), getApplicationContext());
    }

    @Override
    public void onSkipPressed() {

    }

    @Override
    public void onDonePressed() {
        finish();
    }

    @Override
    public void onBackPressed() {}
}
