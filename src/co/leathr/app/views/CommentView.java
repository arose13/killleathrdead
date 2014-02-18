package co.leathr.app.views;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.model.people.PersonBuffer;

import co.leathr.app.R;
import co.leathr.app.activities.BaseActivity;
import co.leathr.app.data.AppData;
import co.leathr.app.data.SQLiteStreamDB;
import co.leathr.app.data.TimeStampHanlder;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class CommentView extends BaseActivity {
	
	private EditText commentEditText;
	private TimeStampHanlder timeStampHanlder = new TimeStampHanlder();
	
	private int typeEmotion = 0;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_view);
		
		/* Start Views */
		commentEditText = (EditText) findViewById(R.id.commentText);
		typeFaceConstructor(commentEditText, AppData.Fonts.Roboto.REGULAR);
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

	/* This adds the Comment to the Database */
	private boolean addCommentToDB() {
		// TODO add a link checker
		int commentType = AppData.DBConstants.TypeOfContent.TEXT;
		
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
	
}
