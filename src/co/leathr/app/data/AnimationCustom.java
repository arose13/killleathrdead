package co.leathr.app.data;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

public class AnimationCustom {

	private long secondsToMilis(long seconds) {
		return (seconds*1000);
	}
	
	protected Animation repeatAnimation(Animation inputAnimation) {
		inputAnimation.setRepeatMode(Animation.RESTART);
		inputAnimation.setRepeatCount(Animation.INFINITE);
		return inputAnimation;
	}
	
	protected Animation fromRightToLeftAnimation(long seconds) {
		Animation fromRightToLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		fromRightToLeft.setDuration(secondsToMilis(seconds));
		fromRightToLeft.setInterpolator(new LinearInterpolator());
		return fromRightToLeft;
	}
	
	protected Animation fromLeftToRightAnimation(long seconds) {
		Animation fromLeftToRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f, 
				Animation.RELATIVE_TO_PARENT, +1.0f, 
				Animation.RELATIVE_TO_PARENT, 0.0f, 
				Animation.RELATIVE_TO_PARENT, 0.0f);
		fromLeftToRight.setDuration(secondsToMilis(seconds));
		fromLeftToRight.setInterpolator(new LinearInterpolator());
		return fromLeftToRight;
	}
}
