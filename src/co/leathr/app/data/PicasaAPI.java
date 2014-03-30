package co.leathr.app.data;

public class PicasaAPI {

	public static final String ALBUMTYPE = "InstantUpload";
	public static final String GIF = ".gif";
	public static final String MP4 = ".mp4";
	public static final String JPG = ".jpg";
	
	public static final class MaxResults {
		public static final int VERY_LARGE = 500;
		public static final int LARGE = 300;
		public static final int SMALL = 150;
	}
	
	public static final class CallBackListner {
		public static final String GET_INSTANTUPLOAD_IMAGES = "picasaInstantUploadCallBack";
		public static final String GET_ALL_IMAGES_FROM_URL = "picasaGetImagesFromUrlCallBack";
		public static final String GET_ALBUMID = "picasagetAlbumID";
	}
	
	/* All URLs GET requests */
	public static String getAllAlbumsURL(String userID, String oauthKey) {
		String albumURL = "http://picasaweb.google.com/data/feed/api/user/" + userID
				+ "?v=2&access_token=" + oauthKey;
		return albumURL;
	}
	
	public static String getPhotosURLs(String userID, String oauthKey, int maxResults) {
		String photosURLs = "https://picasaweb.google.com/data/feed/api/user/" + userID
				+ "?kind=photo&max-results=" + String.valueOf(maxResults)
				+ "&access_token=" + oauthKey;
		return photosURLs;
	}
	
	public static String getPhotosFromAlbum(String userID, String albumID, String oauthKey, int maxResults) {
		String photosURL = "https://picasaweb.google.com/data/feed/api/user/" + userID
				+ "/albumid/" + albumID
				+ "?max-results=" + String.valueOf(maxResults)
				+ "&access_token=" + oauthKey;
		return photosURL;
	}
	
	public static String getThumbnailURL(String contentURL, String imageTitle) {
		imageTitle = FileTypeCheck.gifmp4CheckModifier(imageTitle);
		String composedURL = contentURL.replace(imageTitle, "");
		composedURL = composedURL + "s144/" + imageTitle;
		return composedURL;
	}
	
	/* File Checker class */
	public static class FileTypeCheck {
		
		// Checks for currently invalid types
		public static boolean gifmp4Checker(String imageTitle) {
			if ( (imageTitle.contains(GIF))||(imageTitle.contains(MP4)) ) {
				return true;
			} else {
				return false;
			}
		}
		
		// Modifies GIF and MP4 to JPGs
		public static String gifmp4CheckModifier(String imageTitle) {
			String imageTitleString;
			if (imageTitle.contains(GIF)) {
				imageTitleString = imageTitle.replace(GIF, JPG);
			} else if (imageTitle.contains(MP4)) {
				imageTitleString = imageTitle.replace(MP4, JPG);
			} else {
				imageTitleString = imageTitle;
			}
			return imageTitleString;
		}
		
	}
	
	/* Image Object For Easy Handling */
	public static class Entry {
		public final String id;
		public final String title;
		public final String published;
		public final String timeStamp;
		public final String content;
		
		private Entry(String id, String title, String published, String timeStamp, String content) {
			this.id = id;
			this.title = title;
			this.published = published;
			this.timeStamp = timeStamp;
			this.content = content;
		}
	}
	
}
