package me.shouheng.references.view.intro;

import android.os.Bundle;

import me.shouheng.references.R;


public class IntroSlide4 extends IntroFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		background.setBackgroundResource(R.color.intro_color_4);
		title.setText("intro_4_title");
		image.setImageResource(R.drawable.ic_slide4);
		description.setText("intro_4_description");
	}
}