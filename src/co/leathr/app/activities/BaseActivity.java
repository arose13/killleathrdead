package co.leathr.app.activities;

import co.leathr.app.R;
import co.leathr.app.data.AppData;
import co.leathr.app.views.HomeView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.OnPeopleLoadedListener;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public abstract class BaseActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, OnPeopleLoadedListener {
	
	protected Context contextActivity;
	protected PlusClient mPlusClient;
	protected Person mPlusPerson;
	protected ProgressDialog mConnectionProgressDialog;
	protected ConnectionResult mConnectionResult;
	
	protected String userPlusID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPlusClient = new PlusClient.Builder(this, this, this)
			.setScopes(AppData.GPLusConstants.SCOPES)
			.setActions(AppData.GPLusConstants.VISABLE_ACTIVITIES)
			.build();
		// Progress bar to be displayed if the connection failure is not resolved
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage(getString(R.string.signingin));
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		mPlusClient.connect();
		Log.d(AppData.ViewNames.LOGIN_VIEW, "Plus Client connected");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mPlusClient.disconnect();
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO The user clicked the sign-in button already
		if (mConnectionProgressDialog.isShowing()) {
			if (result.hasResolution()) {
				try {
					result.startResolutionForResult(this, AppData.GPLusConstants.REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					mPlusClient.connect();
				}
			}
		}
		// Save the intent so that we can start an activity when the user clicks the signin button
		mConnectionResult = result;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if ((requestCode == AppData.GPLusConstants.REQUEST_CODE_RESOLVE_ERR) && (resultCode == RESULT_OK)) {
			mConnectionResult = null;
			mPlusClient.connect();
		}
	}
	
	@Override
	public void onConnected(Bundle connectionHint) {
		mPlusClient.loadPeople(this, "me");
		if (mConnectionProgressDialog.isShowing()) {
			mConnectionProgressDialog.dismiss();
		}
		Log.i(AppData.ViewNames.LOGIN_VIEW, "User is connected");
	}

	@Override
	public void onDisconnected() {
		Log.i(AppData.ViewNames.LOGIN_VIEW, "gplus disconnected");
	}
	
	/* Custom Methods */
	protected void typeFaceConstructor(TextView textView, String fontPath) {
		Typeface customTypeface = Typeface.createFromAsset(getAssets(), fontPath);
		textView.setTypeface(customTypeface);
	}
	
	protected void gotoHomeView(Context classDotThis) {
		Intent homeViewIntent = new Intent(classDotThis, HomeView.class);
		startActivity(homeViewIntent);
	}
	
	public void assignUserID(ConnectionResult status, PersonBuffer personBuffer) {
		if (status.getErrorCode() == ConnectionResult.SUCCESS) {
			mPlusPerson = personBuffer.get(0);
			userPlusID = mPlusPerson.getId();
		}
	}

}
