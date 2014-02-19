package co.leathr.app.activities;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public abstract class StreamActivity extends BaseActivity {
	
	protected String userFullName;
	
	protected ListView streamListView;
	
	/* ViewHolder hold all the views accessible 
	 * for the StreamAdapter */
	public static class StreamViewHolder {
		
		//text card views
		public TextView commentDate;
		public TextView commentContent;
		public ImageView commentEmotion;
		
		//link card views
		public TextView linkDate;
		public TextView linkContent;
		public TextView linkEmotion;
		
		//quote card views
		public TextView quoteDate;
		public TextView quoteContent;
		public TextView quoteSource;
		
		//overflow item
		public ImageView overflowBtn;
		
	}
	
	/* Stream Adapter Class, fills the listView with
	 *  all the user content! like a boss!!! */
	public class StreamAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			StreamViewHolder holder = new StreamViewHolder();
			convertView = null;
			
			return null;
		}
		
	}
	
}
