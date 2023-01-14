package entite;

public class ImageAlbum {
	
	private int id;
	private int idAlbum;
	private String title;
	private String url;
	private String thumbnail;
	
	public ImageAlbum(int id, int idAlbum, String title, String url, String thumbnail) {
		this.id = id;
		this.idAlbum = idAlbum;
		this.title = title;
		this.url = url;
		this.thumbnail = thumbnail;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdAlbum() {
		return idAlbum;
	}
	public void setIdAlbum(int idAlbum) {
		this.idAlbum = idAlbum;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
