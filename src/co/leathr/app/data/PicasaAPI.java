package co.leathr.app.data;

public class PicasaAPI {

	public static final String ALBUMTYPE = "InstantUpload";
	public static final String GIF = ".gif";
	public static final String MP4 = ".mp4";
	
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
	
	public static class FileTypeCheck {
		public boolean gifmp4Checker(String imageTitle) {
			if ( (imageTitle.contains(GIF))||(imageTitle.contains(MP4)) ) {
				return true;
			} else {
				return false;
			}
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
