package co.leathr.app.activities;

import java.util.ArrayList;
import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

import co.leathr.app.R;
import co.leathr.app.data.AppObjects.StreamObject;
import co.leathr.app.data.AppData;
import co.leathr.app.data.SQLiteStreamDB;
import co.leathr.app.data.TimeStampHanlder;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public abstract class StreamActivity extends BaseActivity {
	
	protected String userFullName;
	
	protected ListView streamListView;
	protected SQLiteStreamDB sqlStream;
	protected AQuery aq;
	protected ImageOptions photoImageOptions;
	protected PrettyTime prettyTime;
	
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
		
		TimeStampHanlder stampHandler = new TimeStampHanlder();
		long currentUnixtimeLong = (System.currentTimeMillis())/1000;
		String currentDay = stampHandler.getDay(currentUnixtimeLong);
		String currentMonth = stampHandler.getMonth(currentUnixtimeLong);
		String currentYear = stampHandler.getYear(currentUnixtimeLong);
		String selectionClause = sqlStream.selectBy(currentDay, currentMonth, currentYear); 
		ArrayList<StreamObject> streamArrayList = sqlStream.getEntries(selectionClause);
		
		@Override
		public int getCount() {
			return streamArrayList.size();
		}

		@Override
		public Object getItem(int position) {
			return streamArrayList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			StreamViewHolder holder = new StreamViewHolder();
			convertView = null;
			prettyTime = new PrettyTime();
			photoImageOptions.fileCache = true;
			photoImageOptions.memCache = true;
			photoImageOptions.animation = AQuery.FADE_IN;
			
			if (convertView == null) {				
				/* Which type of card to use for the data */
				switch (streamArrayList.get(position).type) {
				case AppData.DBConstants.TypeOfContent.TEXT:
					/* Setting up text card elements the textBottom is not used in this card */
					convertView = getLayoutInflater().inflate(R.layout.list_text, parent, false);
					holder.commentContent = (TextView) convertView.findViewById(R.id.textTop);
					holder.commentDate = (TextView) convertView.findViewById(R.id.textDate);
					convertView.setTag(holder);
					break;
				
				case AppData.DBConstants.TypeOfContent.QUOTE:
					/* Setting up quote card elements textBottom is made visible */
					convertView = getLayoutInflater().inflate(R.layout.list_text, parent, false);
					holder.quoteContent = (TextView) convertView.findViewById(R.id.textTop);
					holder.quoteSource = (TextView) convertView.findViewById(R.id.textBottom);
					holder.quoteSource.setVisibility(View.VISIBLE);
					convertView.setTag(holder);
					break;
				
				case AppData.DBConstants.TypeOfContent.LINK:
					/* Setting up link card elements the textBottom is not used in this card */
					convertView = getLayoutInflater().inflate(R.layout.list_text, parent, false);
					holder.linkContent = (TextView) convertView.findViewById(R.id.textTop);
					holder.linkDate = (TextView) convertView.findViewById(R.id.textDate);
					convertView.setTag(holder);
					break;
					
				case AppData.DBConstants.TypeOfContent.PICTURE:
					break;

				default:
					break;
				}
			}
			
			switch (streamArrayList.get(position).type) {
			
			case AppData.DBConstants.TypeOfContent.TEXT:
				long unixtimeText = Long.parseLong(streamArrayList.get(position).unixtime);
				holder.commentContent.setText(streamArrayList.get(position).content);
				typeFaceConstructor(holder.commentContent, AppData.Fonts.Roboto.LIGHT);
				holder.commentDate.setText( prettyTime.format(new Date(unixtimeText*1000)) );
				//TODO Modify text size
				break;
				
			case AppData.DBConstants.TypeOfContent.QUOTE:
				long unixtimeQuote = Long.parseLong(streamArrayList.get(position).unixtime);
				holder.quoteContent.setText(streamArrayList.get(position).content);
				holder.quoteSource.setText("-" + streamArrayList.get(position).thumbnail);
				typeFaceConstructor(holder.quoteContent, AppData.Fonts.Roboto.LIGHT);
				typeFaceConstructor(holder.quoteSource, AppData.Fonts.Roboto.REGULAR);
				holder.quoteDate.setText(prettyTime.format(new Date(unixtimeQuote*1000)));
				break;
				
			case AppData.DBConstants.TypeOfContent.LINK:
				long unixtimeLink = Long.parseLong( streamArrayList.get(position).unixtime );
				holder.linkContent.setText(streamArrayList.get(position).content);
				typeFaceConstructor(holder.linkContent, AppData.Fonts.Roboto.LIGHT);
				holder.linkDate.setText( prettyTime.format(new Date(unixtimeLink*1000)) );
				break;
				
			case AppData.DBConstants.TypeOfContent.PICTURE:
				break;

			default:
				break;
			}
			
			return convertView;
		}
		
	}
	
}
