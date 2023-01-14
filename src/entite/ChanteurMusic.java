package entite;

public class ChanteurMusic {
	private int idUser;
	private String title;
	private String chemin;
	
	public ChanteurMusic(int idUser, String title, String chemin) {
		this.idUser = idUser;
		this.title = title;
		this.chemin = chemin;
	}
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getChemin() {
		return chemin;
	}
	public void setChemin(String chemin) {
		this.chemin = chemin;
	}
}
