package co.leathr.app.data;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

public class Fonts {

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
	
	/* TypeFace constructor */
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