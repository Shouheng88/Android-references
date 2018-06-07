package me.shouheng.references.view.intro;

import android.os.Bundle;

import me.shouheng.references.R;


public class IntroSlide3 extends IntroFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		background.setBackgroundResource(R.color.intro_color_3);
		title.setText("intro_3_title");
		image.setImageResource(R.drawable.ic_slide3);
		description.setText("intro_3_description");
	}
}