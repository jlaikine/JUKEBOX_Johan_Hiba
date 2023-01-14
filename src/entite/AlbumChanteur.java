package entite;

public class AlbumChanteur {
	private int id;
	private String title;
	private String userNom;
	private String userPrenom;
	private String thumbnail;
	
	public AlbumChanteur(int id, String title, String userNom, String userPrenom, String thumbnail) {
		this.id=id;
		this.title=title;
		this.userNom=userNom;
		this.userPrenom=userPrenom;
		this.thumbnail=thumbnail;
	}
	
	public int getId() {
	    return id;
	}
	public void setId(int id) {
	    this.id=id;
	}
	
	public String getTitle() {
	    return title;
	}
	public void setTitle(String title) {
	    this.title=title;
	}
	
	public String getUserNom() {
	    return userNom;
	}
	public void setUserNom(String userNom) {
	    this.userNom=userNom;
	}
	
	public String getUserPrenom() {
	    return userPrenom;
	}
	public void setUserPrenom(String userPrenom) {
	    this.userPrenom=userPrenom;
	}
	
	public String getThumbnail() {
	    return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
	    this.thumbnail=thumbnail;
	}
}
