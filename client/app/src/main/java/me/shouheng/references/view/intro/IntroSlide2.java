package me.shouheng.references.view.intro;

import android.os.Bundle;

import me.shouheng.references.R;


public class IntroSlide2 extends IntroFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		background.setBackgroundResource(R.color.intro_color_2);
		title.setText("intro_2_title");
		image.setImageResource(R.drawable.ic_slide2);
		description.setText("intro_2_description");
	}
}