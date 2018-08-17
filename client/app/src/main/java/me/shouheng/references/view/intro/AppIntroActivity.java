package me.shouheng.references.view.intro;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.paolorotolo.appintro.AppIntro;

import me.shouheng.commons.config.BaseConstants;

@Route(path = BaseConstants.INTRO)
public class AppIntroActivity extends AppIntro {

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
