package me.shouheng.references.view.intro;

import android.os.Bundle;

import me.shouheng.references.R;


/**
 * Created by Wang Shouheng on 2017/12/6. */
public class IntroSlide1 extends IntroFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        background.setBackgroundResource(R.color.intro_color_5);
        title.setText("intro_1_title");
        image.setImageResource(R.drawable.ic_slide1);
        description.setText("intro_1_description");
    }
}
