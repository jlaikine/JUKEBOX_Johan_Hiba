package entite;

public class Album {
	private int id;
	private int idUser;
	private String title;

	public Album(int id, int idUser, String title) {
	    this.id=id;
	    this.idUser=idUser;
	    this.title=title;
	}
	public int getId() {
	    return id;
	}
	public void setId(int id) {
	    this.id=id;
	}
	
	public int getIdUser() {
	    return idUser;
	}
	public void setIdUser(int idUser) {
	    this.idUser=idUser;
	}
	
	public String getTitle() {
	    return title;
	}
	public void setTitle(String title) {
	    this.title=title;
	}
}
