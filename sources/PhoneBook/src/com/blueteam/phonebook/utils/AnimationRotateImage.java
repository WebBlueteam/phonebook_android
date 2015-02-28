/*
 *
 */
package com.blueteam.phonebook.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

// TODO: Auto-generated Javadoc
/**
 * The Class AnimationRotateImage.
 */
public class AnimationRotateImage{

	/**
	 * Action animation rotate image.
	 *
	 * @param v the v
	 * @param duration the duration
	 */
	public static void actionAnimationRotateImage(View v, int duration){
		// Step1 : create the RotateAnimation object
		RotateAnimation anim = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// Step 2: Set the Animation properties
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(duration);

		// Step 3: Start animating the image
		v.startAnimation(anim);
	}
}
