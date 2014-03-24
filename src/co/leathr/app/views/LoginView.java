package co.leathr.app.views;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.model.people.PersonBuffer;

import co.leathr.app.R;
import co.leathr.app.activities.BaseActivity;
import co.leathr.app.data.AnimationCustom;
import co.leathr.app.data.AppData;
import co.leathr.app.data.Fonts;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

public class LoginView extends BaseActivity implements OnClickListener {
	
	private AnimationCustom mAnimation = new AnimationCustom();
	private Animation leathrTitleAnimation;
	private Animation signinButtonAnimation;
	private TextView leathrTitleView;
	private View signInButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_view);
		contextActivity = getApplicationContext();
		
		/* Initialize Views */
		signInButton = findViewById(R.id.signInButton);
		signInButton.setVisibility(View.INVISIBLE);
		signInButton.setOnClickListener(this);
		Log.d(AppData.ViewNames.LOGIN_VIEW, "LoginView page loaded");
		
		leathrTitleView = (TextView) findViewById(R.id.leathrTitleTextView);
		mFont.typeFaceConstructor(leathrTitleView, Fonts.APPNAME_FONT, getAssets());		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		/* Start Animations on views */
		leathrTitleAnimation = mAnimation.inFromBottomAnimation(660, new OvershootInterpolator(1.5f));
		leathrTitleAnimation.setStartOffset(250);
		leathrTitleView.startAnimation(leathrTitleAnimation);
		
		signinButtonAnimation = mAnimation.inFromTopAnimation(660, new OvershootInterpolator(1.5f));
		signinButtonAnimation.setStartOffset(425);
		signInButton.startAnimation(signinButtonAnimation);
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.signInButton && !mPlusClient.isConnected()) {
			if (mConnectionResult == null) {
				mConnectionProgressDialog.show();
				findViewById(R.id.signInButton).setVisibility(View.VISIBLE);
			} else {
				try {
					mConnectionResult.startResolutionForResult(this, AppData.GPLusConstants.REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					// Try connecting again
					mConnectionResult = null;
					mPlusClient.connect();
				}
			}
		}
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		super.onConnectionFailed(result);
		findViewById(R.id.signInButton).setVisibility(View.VISIBLE);
		// The user clicked the sign-in button already
		if (mConnectionProgressDialog.isShowing()) {
			if (result.hasResolution()) {
				try {
					result.startResolutionForResult(this, AppData.GPLusConstants.REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					mPlusClient.connect();
				}
			}
		}
		mConnectionResult = result;
	}

	@Override
	public void onPeopleLoaded(ConnectionResult status, PersonBuffer personBuffer, String nextPageToken) {
		if (status.getErrorCode() == ConnectionResult.SUCCESS) {
			mPerson = personBuffer.get(0);
			Intent homeViewIntent = new Intent(LoginView.this, HomeView.class);
			gotoView(homeViewIntent);
			activityTransitionAnimation_fromRight();
			finish();
		} else if (status.getErrorCode() == ConnectionResult.NETWORK_ERROR) {
			Toast.makeText(this, R.string.networkerror, Toast.LENGTH_SHORT).show();
		}
	}


}
