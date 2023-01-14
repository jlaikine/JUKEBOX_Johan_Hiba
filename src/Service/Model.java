package Service;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entite.Address;
import entite.Album;
import entite.AlbumChanteur;
import entite.Chanteur;
import entite.ChanteurMusic;
import entite.Company;
import entite.ImageAlbum;

public class Model {
	
	private String URL = "jdbc:derby:jukebox;create=true";
    private String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private String LOGIN = "";
    private String PWD = "";
    private Connection connection;
    private Statement st;
    
    private WebService webService;
    
	@SuppressWarnings("unchecked")
	public void insereDonneesTable() throws ClassNotFoundException, SQLException {
		
		Class.forName(DRIVER);
		connection = DriverManager.getConnection(URL, LOGIN, PWD);
		
		st = connection.createStatement();
		DatabaseMetaData databaseMetadata = DriverManager.getConnection(URL, LOGIN, PWD).getMetaData();
		
		ResultSet resultSetChanteur = databaseMetadata.getTables(null, null, "CHANTEUR", null);
		ResultSet resultSetAlbum = databaseMetadata.getTables(null, null, "ALBUM", null);
		ResultSet resultSetImage = databaseMetadata.getTables(null, null, "IMAGE", null);
		ResultSet resultSetAdresse = databaseMetadata.getTables(null, null, "ADDRESS", null);
		ResultSet resultSetCompany = databaseMetadata.getTables(null, null, "COMPANY", null);
		
		this.webService = new WebService();
		ArrayList<Chanteur> listChanteur = (ArrayList<Chanteur>) webService.getListChanteur().get(0);
		ArrayList<Address> listAddress = (ArrayList<Address>) webService.getListChanteur().get(1);
		ArrayList<Company> listCompany = (ArrayList<Company>) webService.getListChanteur().get(2);
		
		ArrayList<Album> listAlbum = webService.getListAlbum();
		ArrayList<ImageAlbum> listImageAlbum = webService.getListPhoto();
		
		//CHANTEUR
		if (resultSetChanteur.next()) {
			System.out.println("Table CHANTEUR existe déjà");
		} else {
			st.execute("CREATE TABLE chanteur (id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), nom VARCHAR(45), prenom VARCHAR(45), email VARCHAR(45))");
			System.out.println("Création table CHANTEUR");
			
			for (int i=0;i<listChanteur.size();i++) {
				st.executeUpdate("INSERT INTO chanteur VALUES (DEFAULT , '" + listChanteur.get(i).getNom() + "', '" + listChanteur.get(i).getPrenom() + "', '" + listChanteur.get(i).getEmail() + "')");
			}
			System.out.println("Insertion donnée table CHANTEUR");
			
		}
		//ALBUM
		if (resultSetAlbum.next()) {
			System.out.println("Table ALBUM existe déjà");
		} else {
			st.execute("CREATE TABLE album (id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), userId INTEGER, title VARCHAR(200))");
			System.out.println("Création table ALBUM");
			
			for (int i=0;i<listAlbum.size();i++) {
				st.executeUpdate("INSERT INTO album VALUES (DEFAULT, " + listAlbum.get(i).getIdUser() + ", '" + listAlbum.get(i).getTitle() + "')");
			}
			System.out.println("Insertion donnée table ALBUM");
		}
		//IMAGE
		if (resultSetImage.next()) {
			System.out.println("Table IMAGE existe déjà");
		} else {
			st.execute("CREATE TABLE image (id INTEGER PRIMARY KEY, idAlbum INTEGER, title VARCHAR(200), url VARCHAR(200), thumbnail VARCHAR(200))");
			System.out.println("Création table IMAGE");
			
			for (int i=0;i<listImageAlbum.size();i++) {
				st.executeUpdate("INSERT INTO image VALUES (" + listImageAlbum.get(i).getId() + ", " + listImageAlbum.get(i).getIdAlbum() + ", '" + listImageAlbum.get(i).getTitle() + "', '" + listImageAlbum.get(i).getUrl() + "', '" + listImageAlbum.get(i).getThumbnail() + "')");
			}
			System.out.println("Insertion donnée table IMAGE");
		}
		//ADRESSE
		if (resultSetAdresse.next()) {
			System.out.println("Table ADDRESS existe déjà");
		} else {
			st.execute("CREATE TABLE address (id INT PRIMARY KEY, street VARCHAR(45), suite VARCHAR(45), city VARCHAR(45), zipcode VARCHAR(45))");
			System.out.println("Création table ADDRESS");
			
			for (int i=0;i<listAddress.size();i++) {
				st.executeUpdate("INSERT INTO address VALUES (" + listAddress.get(i).getId() + ", '" + listAddress.get(i).getStreet() + "', '" + listAddress.get(i).getSuite() + "', '" + listAddress.get(i).getCity() + "', '" + listAddress.get(i).getZipcode() + "')");
			}
			System.out.println("Insertion donnée table ADDRESS");
		}
		
		//COMPANY
		if (resultSetCompany.next()) {
			System.out.println("Table COMPANY existe déjà");
		} else {
			st.execute("CREATE TABLE company (id INT PRIMARY KEY, name VARCHAR(45), catchPhrase VARCHAR(45), bs VARCHAR(45))");
			System.out.println("Création table COMPANY");
			
			for (int i=0;i<listCompany.size();i++) {
				st.executeUpdate("INSERT INTO company VALUES (" + listCompany.get(i).getId() + ", '" + listCompany.get(i).getName() + "', '" + listCompany.get(i).getCatchPhrase() + "', '" + listCompany.get(i).getBs() + "')");
			}
			System.out.println("Insertion donnée table COMPANY");
		}
	}
	
	public ArrayList<AlbumChanteur> affichage() throws SQLException{
		
		ArrayList<AlbumChanteur> listAffichage = new ArrayList<>();
		ArrayList<String> memoryList = new ArrayList<>();
		
		ResultSet rs = st.executeQuery("SELECT a.*, c.nom as userNom, c.prenom as userPrenom, i.thumbnail as thumbnail FROM album a"
				+ " LEFT JOIN chanteur c"
				+ " ON a.userId = c.id"
				+ " LEFT JOIN image i"
				+ " ON a.id = i.idAlbum");
		
		while(rs.next()) {
			if (!memoryList.contains(rs.getString("id"))) {
				if (rs.getString("thumbnail") != null) {
					AlbumChanteur ac = new AlbumChanteur(rs.getInt("id"),rs.getString("title"),rs.getString("userNom"),rs.getString("userPrenom"),rs.getString("thumbnail"));
					listAffichage.add(ac);
					memoryList.add(rs.getString("id"));
				} else {
					AlbumChanteur ac = new AlbumChanteur(rs.getInt("id"),rs.getString("title"),rs.getString("userNom"),rs.getString("userPrenom"),"https://via.placeholder.com/150/92c952.png");
					listAffichage.add(ac);
					memoryList.add(rs.getString("id"));
				}
			}
		}
		return listAffichage;
	}
	
	public ArrayList<Chanteur> getChanteur() throws SQLException {
		ArrayList<Chanteur> chanteur = new ArrayList<>();
		
		ResultSet rs = st.executeQuery("SELECT * FROM chanteur");
		while (rs.next()) {
			Chanteur c = new Chanteur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"));
			chanteur.add(c);
		}
		
		return chanteur;
	}
	
	public void supprimeAlbumData(int id) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM album WHERE id =" + id);
	    statement.executeUpdate();
		
	}
	
	public boolean verifDoublonsAlbum(String nomAlbum, int userId, String nom, String prenom) throws SQLException {
		ArrayList<AlbumChanteur> ar = affichage();
		
		boolean b = true;
		for (int i=0; i<ar.size(); i++) {
			if (ar.get(i).getUserNom() != null && ar.get(i).getUserPrenom() != null) {
				if (ar.get(i).getUserNom().equals(nom) && ar.get(i).getUserPrenom().equals(prenom) && ar.get(i).getTitle().equals(nomAlbum)) {
					b=false;
					System.out.println("doublons");
				}
				else if (ar.get(i).getTitle().equals(nomAlbum)) {
					b=false;
					System.out.println("Meme nom d'album");
				}
			}
		}
		return b;
	}
	
	public boolean verifDoublonsAlbumEdit(String nomAlbum, int userId, String nom, String prenom) throws SQLException {
		ArrayList<AlbumChanteur> ar = affichage();
		
		boolean b = true;
		for (int i=0; i<ar.size(); i++) {
			if (ar.get(i).getUserNom() != null && ar.get(i).getUserPrenom() != null) {
				if (ar.get(i).getUserNom().equals(nom) && ar.get(i).getUserPrenom().equals(prenom) && ar.get(i).getTitle().equals(nomAlbum)) {
					b=false;
					System.out.println("doublons");
				}
			}
		}
		return b;
	}
	
	public AlbumChanteur addAlbum(String nomAlbum, int userId, String nom, String prenom) throws SQLException {
		AlbumChanteur ac = null;
		if (verifDoublonsAlbum(nomAlbum, userId, nom, prenom)) {
			int verif = st.executeUpdate("INSERT INTO album (id, userId, title) VALUES (DEFAULT, " + userId + ", '"+ nomAlbum + "')");
			System.out.println("Ajout d'un album");
			
			if (verif == 1) {
				ResultSet rs = st.executeQuery("SELECT a.*, c.nom as userNom, c.prenom as userPrenom FROM album a"
						+ " JOIN chanteur c"
						+ " ON a.userId = c.id"
						+ " ORDER BY a.id DESC FETCH FIRST 1 ROWS ONLY");
				while (rs.next()) {
					ac = new AlbumChanteur(rs.getInt("id"),rs.getString("title"),rs.getString("userNom"),rs.getString("userPrenom"),"../image/new.jpg");
				}
			}
		}
		return ac;
	}
	
	public boolean addChanteur(String nom, String prenom, String email) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT nom, prenom FROM chanteur WHERE nom='" + nom + "' AND prenom='" + prenom + "'");
		int compteur = 0;
		boolean b = false;
		
		while(rs.next()) {
			compteur +=1;
		}
		
		if (compteur == 0) {
			st.executeUpdate("INSERT INTO chanteur (id, nom, prenom, email) VALUES (default, '" + nom + "', '" + prenom + "', '" + email + "')");
			System.out.println("Ajout d'un chanteur");
			b = true;
		}
		return b;
	}

	public void supprimeChanteur(String nom, String prenom) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = connection.prepareStatement("DELETE FROM chanteur WHERE nom='" + nom + "' AND prenom='" + prenom +"'");
		ps.executeUpdate();
	}
	
	public boolean editAlbum(String album, String nouveauAlbum, String nom, String prenom) throws SQLException {
		// TODO Auto-generated method stub
		boolean b = false;
		int idUser = 0;
		ResultSet rs = st.executeQuery("SELECT id FROM chanteur WHERE nom='" + nom + "' AND prenom='" + prenom + "'");
		while(rs.next()) {
			idUser = rs.getInt("id");
		}
		
		if (idUser != 0 && verifDoublonsAlbumEdit(nouveauAlbum,idUser,nom,prenom)) {
			PreparedStatement ps = connection.prepareStatement("UPDATE album SET title='" + nouveauAlbum + "', userId=" + idUser + " WHERE title='" + album + "'");
			ps.executeUpdate();
			return true;
		} else {
			return false;
		}
		
	}

	public void editChanteur(String ancienNom, String nom, String ancienPrenom, String prenom, String ancienEmail, String email) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = connection.prepareStatement("UPDATE chanteur SET nom='" + nom + "', prenom='" + prenom + "', email='" + email + "' WHERE nom='" + ancienNom + "' AND prenom='" + ancienPrenom + "' AND email='" + ancienEmail + "'");
		ps.executeUpdate();
	}
	
	public ArrayList<String> getImage() throws SQLException {
		ArrayList<String> listImage = new ArrayList<>();
		
		ResultSet rs = st.executeQuery("SELECT thumbnail FROM image");
		while (rs.next()) {
			listImage.add(rs.getString("thumbnail"));
		}
		return listImage;
	}
	
	public void exportCSV() throws SQLException {
		FileWriter file = null;
		ArrayList<AlbumChanteur> exportList = affichage();
		try {
			file = new FileWriter("jukebox.csv");
	        
			for(int i=0; i<exportList.size(); i++) {
				if (exportList.get(i).getUserNom() != null && exportList.get(i).getUserPrenom() != null) {
					String chanteur = exportList.get(i).getUserNom() + " " + exportList.get(i).getUserPrenom();
					file.append(chanteur + " ; " + exportList.get(i).getTitle() + "\n");
				}
			}
	        
	        file.close();
	        
	        Desktop d = Desktop.getDesktop();
	        File f = new File("jukebox.csv");
	        if(f.exists()) 
	            d.open(f);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ChanteurMusic> musicData(){
		
		ArrayList<ChanteurMusic> list = new ArrayList<>();
		ChanteurMusic NoMusic = new ChanteurMusic(0, "No music", "");
		ChanteurMusic DreamerMusic = new ChanteurMusic(5, "Dreamer", "src/music/Dreamer.wav");
		ChanteurMusic Save_your_tearsMusic = new ChanteurMusic(3, "Save Your Tears", "src/music/Save_your_tears.wav");
		ChanteurMusic See_you_againMusic = new ChanteurMusic(6, "See You Again", "src/music/See_you_again.wav");
		
		list.add(NoMusic);
		list.add(See_you_againMusic);
		list.add(Save_your_tearsMusic);
		list.add(DreamerMusic);
		
		
		return list;
	}
	
	public ChanteurMusic musicData(String nom, String prenom) throws SQLException {
		
		int idUser = 0;
		ResultSet rs = st.executeQuery("SELECT id FROM chanteur WHERE nom='" + nom + "' AND prenom='" + prenom + "'");
		while(rs.next()) {
			idUser = rs.getInt("id");
		}
		
		ArrayList<ChanteurMusic> list = musicData();
		
		for(int i=0; i<list.size(); i++) {
			if (idUser == list.get(i).getIdUser()) {
				return list.get(i);
			}
		}
		return list.get(0);
	}
	
	public String getMusic(String title) {
		
		String chemin = null;
		ArrayList<ChanteurMusic> list = musicData();
		
		for (int i=0; i<list.size(); i++) {
			if (title.equals(list.get(i).getTitle())) {
				chemin = list.get(i).getChemin();
			}
		}
		
		return chemin;
	}
}
