package co.leathr.app.data;

import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

public class AnimationCustom {
		
	public long secondsToMilis(long seconds) {
		return (seconds*1000);
	}
	
	public Animation delayAnimation(long delayMilis, Animation inputAnimation) {
		inputAnimation.setStartOffset(delayMilis);
		return inputAnimation;
	}
	
	public Animation repeatAnimation(Animation inputAnimation) {
		inputAnimation.setRepeatMode(Animation.RESTART);
		inputAnimation.setRepeatCount(Animation.INFINITE);
		return inputAnimation;
	}
	
	public Animation fromRightToLeftAnimation(long milis, Interpolator animationInterpolator) {
		Animation fromRightToLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		fromRightToLeft.setDuration(milis);
		fromRightToLeft.setInterpolator(animationInterpolator);
		return fromRightToLeft;
	}
	
	public Animation fromLeftToRightAnimation(long milis, Interpolator animationInterpolator) {
		Animation fromLeftToRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f, 
				Animation.RELATIVE_TO_PARENT, +1.0f, 
				Animation.RELATIVE_TO_PARENT, 0.0f, 
				Animation.RELATIVE_TO_PARENT, 0.0f);
		fromLeftToRight.setDuration(milis);
		fromLeftToRight.setInterpolator(new LinearInterpolator());
		return fromLeftToRight;
	}
	
	public Animation inFromTopAnimation(long milis, Interpolator animationInterpolator) {
		Animation inFromTop = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f, 
				Animation.RELATIVE_TO_PARENT, 0.0f, 
				Animation.RELATIVE_TO_PARENT, -1.0f, 
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromTop.setDuration(milis);
		inFromTop.setInterpolator(animationInterpolator);
		return inFromTop;
	}
	
	public Animation inFromBottomAnimation(long milis, Interpolator animationInterpolator) {
		Animation inFromBottom = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromBottom.setDuration(milis);
		inFromBottom.setInterpolator(animationInterpolator);
		return inFromBottom;
	}
}
