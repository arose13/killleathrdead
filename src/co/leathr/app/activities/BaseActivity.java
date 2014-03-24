package co.leathr.app.activities;

import java.io.IOException;
import java.util.List;

import co.leathr.app.R;
import co.leathr.app.data.AppData;
import co.leathr.app.data.Fonts;
import co.leathr.app.data.PicasaAPI;
import co.leathr.app.data.SQLiteStreamDB;
import co.leathr.app.data.XmlTags;
import co.leathr.app.data.AppData.GPLusConstants;
import co.leathr.app.data.AppData.ViewNames;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

public abstract class BaseActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, OnPeopleLoadedListener {
	
	protected Fonts mFont = new Fonts();
	
	protected static boolean wifiConnected = false;
	protected static boolean mobileConnected = false;
	protected static boolean refreshDisplay = true;
	
	protected Context contextActivity;
	protected PlusClient mPlusClient;
	protected Person mPerson;
	protected ProgressDialog mConnectionProgressDialog;
	protected ConnectionResult mConnectionResult;
	protected AQuery aq;
	
	protected String userPlusID;
	protected String userFullName;
	protected String picasaToken;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aq = new AQuery(this);
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
		// Save the intent so that we can start an activity when the user clicks the signin button
		mConnectionResult = result;
	}
	
	@Override
	public void onPeopleLoaded(ConnectionResult status, PersonBuffer personBuffer, String nextPageToken) {

		if (status.getErrorCode() == ConnectionResult.SUCCESS) {
			//Get Person
			mPerson = personBuffer.get(0);
			//User's name and userId
			userFullName = mPerson.getName().getGivenName() + " " + mPerson.getName().getFamilyName();
			userPlusID = mPerson.getId();
			Log.i(ViewNames.BASE_ACTIVITY, userFullName);
			Log.i(ViewNames.BASE_ACTIVITY, userPlusID);
			//Get Picasa Token Via AsyncTask
			
		}
		
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
	protected void gotoView(Intent viewIntent) {
		startActivity(viewIntent);
	}
	
	public void assignUserID(ConnectionResult status, PersonBuffer personBuffer) {
		if (status.getErrorCode() == ConnectionResult.SUCCESS) {
			mPerson = personBuffer.get(0);
			userPlusID = mPerson.getId();
		}
	}
	
	/* Activity Animation */
	protected void getPicasaOAuthTokenASyncTask() {
		
		//Get userOAuthToken with AsyncTask
		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				String picasaReceivedToken = "";
				
				try {
					picasaReceivedToken = GoogleAuthUtil.getToken(BaseActivity.this, mPlusClient.getAccountName(), "oauth2:" + GPLusConstants.SCOPES[2]);
				} catch (IOException e) {
					// Network or Server error, try later: handle exception
					Log.e(ViewNames.BASE_ACTIVITY, e.toString());
				} catch (UserRecoverableAuthException e) {
					// Recover intent: handle exception
					Log.e(ViewNames.BASE_ACTIVITY, e.toString());
				} catch (GoogleAuthException e) {
					// Complete and epic fail!: handle exception
					Log.e(ViewNames.BASE_ACTIVITY, e.toString());
				}
				
				return picasaReceivedToken;
			}
			
			@Override
			protected void onPostExecute(String resultToken) {
				super.onPostExecute(resultToken);
				
				//Check Token status
				if (!resultToken.equals("")) {
					picasaToken = resultToken; // Token Successfully received
					getNewImages();
					Log.d(ViewNames.PICASA_API, picasaToken);
				} else {
					picasaToken = "";
					Log.d(ViewNames.PICASA_API, "picasa token not recieved");
				}
			}
			
		};
		
		task.execute(); // get Token class is now executing
		
	}
	
	/* Picasa Get Images Methods Are Below */
	private void getNewImages() {
		updateInternetConnectionFlags();
		// Check to see if we actually have the Picasa Token
		if (refreshDisplay && !picasaToken.equals("")) {
			
			// Check wifi or mobile is available
			if (wifiConnected || mobileConnected) {
				String albumURL = PicasaAPI.getAllAlbumsURL(mPlusClient.getCurrentPerson().getId(), picasaToken);
				aq.ajax(albumURL, XmlDom.class, this, PicasaAPI.CallBackListner.GET_INSTANTUPLOAD_IMAGES);
			} else {
				Log.d(ViewNames.HOME_VIEW, "Internet not available");
			}
			
		}
	}
	
	public void picasaInstantUploadCallBack(String url, XmlDom xml, AjaxStatus status) {
		List<XmlDom> entries = xml.tags(XmlTags.ENTRY);
		String albumID = "";
		for (XmlDom entry:entries) {
			if (entry.text(XmlTags.GPhoto.ABLUMTYPE).equals(PicasaAPI.ALBUMTYPE)) {
				albumID = entry.text(XmlTags.GPhoto.ID);
				String photosURL = PicasaAPI.getPhotosFromAlbum(mPlusClient.getCurrentPerson().getId(), albumID, picasaToken, PicasaAPI.MaxResults.LARGE);
				aq.ajax(photosURL, XmlDom.class, this, PicasaAPI.CallBackListner.GET_ALL_IMAGES_FROM_URL);
			}
		}
	}
	
	public void picasaGetImagesFromUrlCallBack(String photoListURL, XmlDom xml, AjaxStatus status) {
		List<XmlDom> imageEntries = xml.tags(XmlTags.ENTRY);
		
		// Setup the database connection
		SQLiteStreamDB streamDB = new SQLiteStreamDB(getApplicationContext());
		streamDB.open();
		
		// Start getting all the information from XML 
		for (XmlDom entry : imageEntries) {
			String id = entry.text(XmlTags.ImageTags.ID);
//			String title = entry.text(XmlTags.ImageTags.TITLE);
//			String published = entry.text(XmlTags.ImageTags.PUBLISHED);
//			String timestamp = entry.text(XmlTags.ImageTags.TIMESTAMP);
//			String content = entry.text(XmlTags.ImageTags.CONTENT);
			Log.d(ViewNames.PICASA_API, id);
		}
		streamDB.close();
	}
	
	private void updateInternetConnectionFlags() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
		if ( (activeInfo != null) && (activeInfo.isConnected()) ) {
			wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
			mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
		} else {
			wifiConnected = false;
			mobileConnected = false;
		}
	}

	
	protected void activityTransitionAnimation_bottomUp() {
		overridePendingTransition(R.anim.activity_bottomup_enter, R.anim.activity_bottomup_exit);
	}
	
	protected void activityTransitionAnimation_fromRight() {
		overridePendingTransition(R.anim.activity_fromright_enter, R.anim.activity_nomotion);
	}
	
	public class AnimationImplementor {
		
		public void animationStarter(View view, int animationId) {
			view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), animationId));
		}		
		
	}

}
