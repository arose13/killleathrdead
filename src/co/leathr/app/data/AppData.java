package co.leathr.app.data;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;
import co.leathr.app.R;

import com.google.android.gms.common.Scopes;

public class AppData {
	
	public static final int GET_PICTURE_RESULT = 1000;
	
	public final static class GPLusConstants {
			
			public static final int REQUEST_CODE_RESOLVE_ERR = 9000;
			
			public static final String[] VISABLE_ACTIVITIES = new String[] {
				"http://schemas.google.com/AddActivity", 
				"http://schemas.google.com/CheckInActivity", 
				"http://schemas.google.com/CommentActivity",
				"http://schemas.google.com/CreateActivity",
			};
			
			public static String[] SCOPES = new String[] {
				Scopes.PLUS_LOGIN, //Gplus login scope
				Scopes.PLUS_PROFILE, //Gplus profile scope
				"http://picasaweb.google.com/data/", //Picasa web album scope
				"https://www.googleapis.com/auth/drive.appdata" //Drive scope
			};
			
		}
	
	public static class DBConstants {
		/* Text not null with comma */
		public static final String TNN = " TEXT NOT NULL, ";
		
		/* Database types
		 * NEVER EVER EVER modify */
		public static class Emotion {
			public static final int NONE = 0;
			public static final int LOVE = 1;
			public static final int VERYHAPPY = 2;
			public static final int HAPPY = 3;
			public static final int SAD = 4;
			public static final int ANGRY = 5;
		}
		
		public static class TypeOfContent {
			public static final int NULLFORMAT = 0;
			public static final int TEXT = 1;
			public static final int LINK = 2;
			public static final int QUOTE = 3;
			public static final int PICTURE = 4;
		}
		
		public static class Hide {
			public static final int VISABLE = 0;
			public static final int HIDE = 1;
		}
	}
	
	public static class Fonts {
		
		private static final int MAX_STRING_LENGHT_FONTSIZE = 40;
		private static final String FRONT = "fonts/";
		private static final String BACK = ".ttf";
		
		public static final String APPNAME_FONT = FRONT + "GrandHotel-Regular" + BACK;
		
		public static final class Roboto {
			private static final String ROBOTOGROUP = "Roboto-";
			
			public static final String THIN = FRONT + ROBOTOGROUP + "Thin" + BACK;
			public static final String LIGHT = FRONT + ROBOTOGROUP + "Light" + BACK;
			public static final String REGULAR = FRONT + ROBOTOGROUP + "Regular" + BACK;
		}
		
		/* Typeface constructor */
		public void typeFaceConstructor(TextView textView, String fontPath, AssetManager assets) {
			Typeface customTypeface = Typeface.createFromAsset(assets, fontPath);
			textView.setTypeface(customTypeface);
		}
		
		/* Detects whether the font size should be modified to a larger one */
		public boolean fontSizeChangeCheck(String string) {
			if (string.length() < MAX_STRING_LENGHT_FONTSIZE) {
				return true;
			} else {
				return false;
			}
		}
		
		/* Changes the font size based on the lenght of the string */
		public float fontSizeModifier(String string) {
			int returnedInteger = 0;
			int xlarge = 26;
			int large = 24;
			int medium = 22;
			
			if (string.length() >= MAX_STRING_LENGHT_FONTSIZE) {
				//Do Nothing
			} else if (string.length() < MAX_STRING_LENGHT_FONTSIZE && string.length() >= 20) {
				returnedInteger = medium;
			} else if (string.length() < 20 && string.length() > 10) {
				returnedInteger = large;
			} else if (string.length() <= 10) {
				returnedInteger = xlarge;
			}
			
			return returnedInteger;
		}
		
	}
	
	public static class RayMenuConstants {
		public static final int[] ITEM_DRAWABLES = {
			R.drawable.ic_leathr_image_icon_round,
			R.drawable.ic_leathr_comment_icon_round,
			R.drawable.ic_leathr_quote_icon_round
		};
		
		public static final int IMAGE_BTN = 0;
		public static final int COMMENT_BTN = 1;
		public static final int QUOTE_BTN = 2;
	}
	
	public static class MimiTypes {
		public static final String TEXT_PLAIN = "text/plain";
	}
	
	public final static class ViewNames {
		public static final String LOGIN_VIEW = "loginView";
		public static final String HOME_VIEW = "homeView";
		public static final String COMMENT_VIEW = "commentView";
		public static final String QUOTE_VIEW = "quoteView";
	}
	
}
