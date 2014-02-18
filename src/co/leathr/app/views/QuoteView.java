package co.leathr.app.views;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.model.people.PersonBuffer;

import co.leathr.app.R;
import co.leathr.app.activities.BaseActivity;
import co.leathr.app.data.AppData;

public class QuoteView extends BaseActivity {
	
	EditText quoteEditText;
	EditText quoteSourceEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quote_view);
		
		/* Start view */
		quoteEditText = (EditText) findViewById(R.id.quoteText);
		quoteSourceEditText = (EditText) findViewById(R.id.quoteSourceText);
		typeFaceConstructor(quoteEditText, AppData.Fonts.Roboto.REGULAR);
		typeFaceConstructor(quoteSourceEditText, AppData.Fonts.Roboto.REGULAR);
	}
	
	@Override
	public void onPeopleLoaded(ConnectionResult status, PersonBuffer personBuffer, String nextPageToken) {
		assignUserID(status, personBuffer);
	}

}
