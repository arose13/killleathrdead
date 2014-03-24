package co.leathr.app.views;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.model.people.PersonBuffer;

import co.leathr.app.R;
import co.leathr.app.activities.BaseActivity;
import co.leathr.app.data.AppData;
import co.leathr.app.data.Fonts.Roboto;
import co.leathr.app.data.SQLiteStreamDB;
import co.leathr.app.data.TimeStampHanlder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class CommentView extends BaseActivity {
	
	private Intent externalIntent;
	private String externalIntentAction;
	private String externalIntentType;
	
	private EditText commentEditText;
	private TimeStampHanlder timeStampHanlder = new TimeStampHanlder();
	
	private int typeEmotion = 0;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_view);
		
		/* Start Views */
		commentEditText = (EditText) findViewById(R.id.commentText);
		mFont.typeFaceConstructor(commentEditText, Roboto.REGULAR, getAssets());
		
		/* Deal with incoming share intent */
		handleExternalIntent();
	}
	
	/* This modifies the actionBar button */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comment_view, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/* This is an OnClick listener for the actionBar */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.comment_action_post:
			if (addCommentToDB()) {
				//GoToHomescreen
				finish();
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onPeopleLoaded(ConnectionResult status, PersonBuffer personBuffer, String nextPageToken) {
		assignUserID(status, personBuffer);
	}
	
	/* This handles shared intents */
	private void handleExternalIntent() {
		externalIntent = getIntent();
		externalIntentAction = externalIntent.getAction();
		externalIntentType = externalIntent.getType();
		
		if (Intent.ACTION_SEND.equals(externalIntentAction) && externalIntentType != null) {
			if (externalIntentAction.equals(Intent.ACTION_SEND) && externalIntentType.equals(AppData.MimiTypes.TEXT_PLAIN)) {
				String sharedText = externalIntent.getStringExtra(Intent.EXTRA_TEXT);
				String sharedSubject = externalIntent.getStringExtra(Intent.EXTRA_SUBJECT);
				if ((sharedText != null) && (sharedSubject != null)) {
					commentEditText.setText(sharedSubject + " " + sharedText);
				} else if (sharedText != null) {
					commentEditText.setText(sharedText);
				}
			}
		}
	}


	/* This adds the Comment to the Database */
	private boolean addCommentToDB() {
		int commentType;
		if (containsLinkCheck()) {
			commentType = AppData.DBConstants.TypeOfContent.LINK;
		} else {
			commentType = AppData.DBConstants.TypeOfContent.TEXT;
		}
		
		String commentText = commentEditText.getText().toString();
		long unixTimeLong = timeStampHanlder.getCurrentUnixTime_returnLong();
		String timeDay = timeStampHanlder.getDay(unixTimeLong);
		String timeMonth = timeStampHanlder.getMonth(unixTimeLong);
		String timeYear = timeStampHanlder.getYear(unixTimeLong);
		if ((!commentText.equals("")) && (commentText != null)) {
			/* There is text to save */
			SQLiteStreamDB textStreamDB = new SQLiteStreamDB(getApplicationContext());
			textStreamDB.open();
			textStreamDB.putEntry(commentType, commentText, "", String.valueOf(unixTimeLong), timeDay, timeMonth, timeYear, typeEmotion, userPlusID);
			textStreamDB.close();
			return true;
		} else {
			/* There wasn't test to save therefore tell the user */
			Toast.makeText(getApplicationContext(), R.string.commentEmptyToast, Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	/* Checks if a String contains a link */
	private boolean containsLinkCheck() {
		String inputText = commentEditText.getText().toString();
		if (inputText.contains("http://") || inputText.contains("www.") || inputText.contains("https://")) {
			return true;
		} else {
			return false;
		}
	}
	
}
