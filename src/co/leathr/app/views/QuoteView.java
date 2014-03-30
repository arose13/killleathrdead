package co.leathr.app.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.model.people.PersonBuffer;

import co.leathr.app.R;
import co.leathr.app.activities.BaseActivity;
import co.leathr.app.data.AppData.DBConstants.Emotion;
import co.leathr.app.data.AppData.DBConstants.TypeOfContent;
import co.leathr.app.data.Fonts.Roboto;
import co.leathr.app.data.SQLiteStreamDB;
import co.leathr.app.data.TimeStampHanlder;

public class QuoteView extends BaseActivity {
	
	private EditText quoteEditText;
	private EditText quoteSourceEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quote_view);
		
		/* Start view */
		initViews();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comment_view, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.comment_action_post:
			if (addQuoteToDB()) {
				//GoToHomescreen
				finish();
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initViews() {
		quoteEditText = (EditText) findViewById(R.id.quoteText);
		quoteSourceEditText = (EditText) findViewById(R.id.quoteSourceText);
		mFont.typeFaceConstructor(quoteEditText, Roboto.REGULAR, getAssets());
		mFont.typeFaceConstructor(quoteSourceEditText, Roboto.REGULAR, getAssets());
	}
	
	@Override
	public void onPeopleLoaded(ConnectionResult status, PersonBuffer personBuffer, String nextPageToken) {
		assignUserID(status, personBuffer);
	}
	
	/* Adds quote to database */
	private boolean addQuoteToDB() {
		String quoteText = quoteEditText.getText().toString();
		String quoteSourceText = quoteSourceEditText.getText().toString();
		long unixTimeLong = TimeStampHanlder.getCurrentUnixTime_returnLong();
		String timeDay = TimeStampHanlder.getDay(unixTimeLong);
		String timeMonth = TimeStampHanlder.getMonth(unixTimeLong);
		String timeYear = TimeStampHanlder.getYear(unixTimeLong);
		if ((!quoteText.equals("")) && (quoteText != null)) {
			/* There is a quote to save */			
			quoteSourceText = quoteSourceText_isEmptyCheck(quoteSourceText);
			
			/* Add to DB*/
			SQLiteStreamDB quoteStreamDB = new SQLiteStreamDB(getApplicationContext());
			quoteStreamDB.open();
			quoteStreamDB.putEntry(
					TypeOfContent.QUOTE, //Type of DB entry
					quoteText, // Quote itself
					quoteSourceText, // Quote Source
					String.valueOf(unixTimeLong), //Time of creation
					timeDay, //Day
					timeMonth, //Month
					timeYear, //Year
					Emotion.NONE, //Emotion type
					userPlusID //UserID
					);
			quoteStreamDB.close();
			return true;
		} else {
			/* There wasn't a quote to save therefore tell the user */
			Toast.makeText(getApplicationContext(), R.string.quoteEmptyToast, Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	private String quoteSourceText_isEmptyCheck(String quoteSourceText) {
		if ((!quoteSourceText.equals("")) && (quoteSourceText != null)) { // Is there a Source for the quote?
			/* There is so all's good ^_^ */
		} else {
			/* There is not anything else empty the string */
			quoteSourceText = "";
		}
		return quoteSourceText;
	}
	
}
