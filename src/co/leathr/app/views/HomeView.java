package co.leathr.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.capricorn.RayMenu;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import co.leathr.app.R;
import co.leathr.app.activities.StreamActivity;
import co.leathr.app.data.AppData;

public class HomeView extends StreamActivity {
	
	private RayMenu rayMenu;
	private StreamAdapter listAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);
		contextActivity = getApplicationContext();
		
		/* Start ArcMenu */
		createArcMenu();
		
		/* Start ListView for the stream */
		streamListView();
	}

	private void streamListView() {
		streamListView = (ListView) findViewById(R.id.streamListView);
		listAdapter = new StreamAdapter();
		streamListView.setAdapter(listAdapter);
	}

	@Override
	public void onPeopleLoaded(ConnectionResult status, PersonBuffer personBuffer, String nextPageToken) {
		// TODO null for now
		if (status.getErrorCode() == ConnectionResult.SUCCESS) {
			//Get the person
			Person person = personBuffer.get(0);
			//User's name and userId
			userFullName = person.getName().getGivenName() + " " + person.getName().getFamilyName();
			userPlusID = person.getId();
			Log.i(AppData.ViewNames.HOME_VIEW, person.getName().getGivenName() + " " + person.getName().getFamilyName());
		}
	}

	private void createArcMenu() {
		rayMenu = (RayMenu) findViewById(R.id.rayMenu);
		for (int i = 0; i < AppData.RayMenuConstants.ITEM_DRAWABLES.length; i++) {
			ImageView item = new ImageView(this);
			item.setImageResource(AppData.RayMenuConstants.ITEM_DRAWABLES[i]);
			final int position = i;
			rayMenu.addItem(item, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					menuSelectedItemHandler(position);
				}
			});
			
		}
	}

	private void menuSelectedItemHandler(int item) {
		switch (item) {
		case AppData.RayMenuConstants.IMAGE_BTN:
			Intent getimageIntent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			getimageIntent.setType("image/*");
			startActivityForResult(Intent.createChooser(getimageIntent, "Select Picture"), AppData.GET_PICTURE_RESULT);
			break;
			
		case AppData.RayMenuConstants.COMMENT_BTN:
			Intent commentIntent = new Intent(HomeView.this, CommentView.class);
			startActivity(commentIntent);
			break;
		
		case AppData.RayMenuConstants.QUOTE_BTN:
			Intent quoteIntent = new Intent(HomeView.this, QuoteView.class);
			startActivity(quoteIntent);
			break;

		default:
			break;
		}
	}
	
}
