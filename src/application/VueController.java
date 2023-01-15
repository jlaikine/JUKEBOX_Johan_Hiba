package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Service.Model;
import entite.AlbumChanteur;
import entite.Chanteur;
import entite.ChanteurMusic;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class VueController implements Initializable{

	//ALBUM
	@FXML
	private Label labelListAlbum;
	@FXML
	private Label nbAlbum;
	@FXML
	private ListView<String> listAlbum;
	@FXML
	private Label labelNouveauAlbum;
	@FXML
	private Label labelNomAlbum;
	@FXML
	private TextField nomAlbum;
	@FXML
	private Label labelChanteur;
	@FXML
	private ChoiceBox<String> listChanteurAlbum;
	@FXML
	private Button boutonModifierAlbum;
	@FXML
	private Button boutonAjouterAlbum;
	
	@FXML
	private Button addAlbumOption;
	@FXML
	private Button editAlbumOption;
	
	
	//CHANTEUR
	@FXML
	private Label labelListChanteur;
	@FXML
	private Label nbChanteur;
	@FXML
	private ListView<String> listChanteur;
	@FXML
	private Label labelNomChanteur;
	@FXML
	private Label labelPrenomChanteur;
	@FXML
	private TextField nomChanteur;
	@FXML
	private TextField prenomChanteur;
	@FXML
	private Label labelMail;
	@FXML
	private TextField emailChanteur;
	@FXML
	private Button boutonAjouterChanteur;
	@FXML
	private Button boutonModifierChanteur;
	
	@FXML
	private Button addChanteurOption;
	@FXML
	private Button editChanteurOption;
	
	@FXML
	private Button addMoreInfoChanteur;
	
	//VIEW
	@FXML
	private ImageView imageView;
	@FXML
	private Label labelAlbumView;
	@FXML
	private Label labelChanteurView;
	@FXML
	private Menu labelFichier;
	@FXML
	private Menu labelLangues;
	@FXML
	private Label labelTitre;
	@FXML
	private MenuItem labelFrancais;
	@FXML
	private MenuItem labelAnglais;
		
	private Model model;
	
	//MUSIC
	@FXML
	private ImageView imagePlay;
	@FXML
	private ImageView imagePause;
	@FXML
	private Button buttonPlay;
	@FXML
	private Button buttonPause;
	@FXML
	private Label labelMusic;
	@FXML
	private Label labelChanteurMusic;
	
	private Clip clip;
	private boolean musicValue = false;
	
	//Change image
	private int imageIndex = 0;
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			addChanteur();
			addAlbum();
			this.model = new Model();
			
			model.insereDonneesTable();
			
			ArrayList<AlbumChanteur> modelListAlbum = model.affichage();
			ArrayList<Chanteur> modelListChanteur = model.getChanteur();
			
			int nbAlbumList = modelListAlbum.size();
			nbAlbum.setText("(" + nbAlbumList + ")");
			
			for (int i=0; i<modelListAlbum.size(); i++) {
				listAlbum.getItems().add(modelListAlbum.get(i).getTitle());
			}

			Image img = new Image(modelListAlbum.get(0).getThumbnail());
			imageView.setImage(img);
			if (modelListAlbum.get(0).getUserNom() != null && modelListAlbum.get(0).getUserPrenom() != null) {
				labelChanteurView.setText(modelListAlbum.get(0).getUserNom() + " " + modelListAlbum.get(0).getUserPrenom());
			} else {
				labelChanteurView.setText("---");
			}
			
			labelAlbumView.setText(modelListAlbum.get(0).getTitle());
			
			int nbChanteurList = modelListChanteur.size();
			nbChanteur.setText("(" + nbChanteurList + ")");
			
			for (int i=0;i<modelListChanteur.size(); i++) {
				listChanteur.getItems().add(modelListChanteur.get(i).getNom() + " " + modelListChanteur.get(i).getPrenom());
				listChanteurAlbum.getItems().add(modelListChanteur.get(i).getNom() + " " + modelListChanteur.get(i).getPrenom());
			}
			
			//music
			labelMusic.setStyle("-fx-text-fill: red");
			labelChanteurMusic.setText("---");
			labelMusic.setText("No music");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//VIEW
	public void changeAlbum(MouseEvent e) throws SQLException {
		
		for (AlbumChanteur list : model.affichage()) {
			if (list.getTitle().equals(listAlbum.getSelectionModel().getSelectedItem())) {
				Image img = new Image(list.getThumbnail());
				imageView.setImage(img);
				labelChanteurView.setText(list.getUserNom() + " " + list.getUserPrenom());
				labelAlbumView.setText(list.getTitle());
				if (list.getUserNom() == null && list.getUserPrenom() == null) {
					labelChanteurView.setText("---");
				} else {
					labelChanteurView.setText(list.getUserNom() + " " + list.getUserPrenom());
				}
			}
		}
		
		if (editAlbumOption.getStyle() == "-fx-background-color: #e43a51; ") {
			nomAlbum.setPromptText(listAlbum.getSelectionModel().getSelectedItem());
			for (AlbumChanteur list : model.affichage()) {
				if (list.getTitle().equals(listAlbum.getSelectionModel().getSelectedItem())) {
					if (list.getUserNom() == null & list.getUserPrenom() == null) {
						listChanteurAlbum.getSelectionModel().select(null);
					} else {
						listChanteurAlbum.getSelectionModel().select(list.getUserNom() + " " + list.getUserPrenom());
						nomAlbum.setStyle("-fx-border-width: 0;");
						listChanteurAlbum.setStyle("-fx-border-width: 0;");
					}
				}
			}
		}
	}
	
	//ALBUM OPTION
	public void addAlbum() {
		labelNouveauAlbum.setVisible(false);
		boutonModifierAlbum.setVisible(false);
		
		labelNomAlbum.setVisible(true);
		boutonAjouterAlbum.setVisible(true);
		
		addAlbumOption.setStyle("-fx-background-color: #e43a51; ");
		editAlbumOption.setStyle("");
		
		nomAlbum.setPromptText("");
		nomAlbum.setText("");
		listChanteurAlbum.getSelectionModel().select(null);
		
		nomAlbum.setStyle("-fx-border-width: 0;");
		listChanteurAlbum.setStyle("-fx-border-width: 0;");
		
	}
	
	public void editAlbum() throws SQLException {
		labelNouveauAlbum.setVisible(true);
		boutonModifierAlbum.setVisible(true);
		
		labelNomAlbum.setVisible(false);
		boutonAjouterAlbum.setVisible(false);
		
		addAlbumOption.setStyle("");
		editAlbumOption.setStyle("-fx-background-color: #e43a51; ");
		
		nomAlbum.setStyle("-fx-border-width: 0;");
		listChanteurAlbum.setStyle("-fx-border-width: 0;");
		
		nomAlbum.setText("");
		listChanteurAlbum.getSelectionModel().select(null);
		nomAlbum.setPromptText(listAlbum.getSelectionModel().getSelectedItem());
		for (AlbumChanteur list : model.affichage()) {
			if (list.getTitle().equals(listAlbum.getSelectionModel().getSelectedItem())) {
				if (list.getUserNom() == null & list.getUserPrenom() == null) {
					listChanteurAlbum.getSelectionModel().select(null);
				} else {
					listChanteurAlbum.getSelectionModel().select(list.getUserNom() + " " + list.getUserPrenom());
				}
			}
		}
	}

	public boolean deleteAlbum() throws SQLException {
	
		for (AlbumChanteur list : model.affichage()) {
			if (list.getTitle().equals(listAlbum.getSelectionModel().getSelectedItem())) {
				int index=listAlbum.getSelectionModel().getSelectedIndex();
				model.supprimeAlbumData(list.getId());
				listAlbum.getItems().remove(index);
				
				int nbAlbumList = listAlbum.getItems().size();
				nbAlbum.setText("(" + nbAlbumList + ")");
				return true;
			}
		}
		return false;
	}
	
	//ALBUM BOUTON
	public void modifierAlbum() throws SQLException {
		String titleAncien = nomAlbum.getPromptText();
		String chanteur = listChanteurAlbum.getSelectionModel().getSelectedItem();
		String title = nomAlbum.getText();
		if (titleAncien != ""  && chanteur != null) {	
			if (nomAlbum.getText() == "") {
				title = titleAncien;
			}
			chanteur = listChanteurAlbum.getSelectionModel().getSelectedItem();
			String nom = "";
			String prenom = "";
			
			int index = listAlbum.getSelectionModel().getSelectedIndex();
			
			for (Chanteur list : model.getChanteur()) {
				String s = list.getNom() + " " + list.getPrenom();
				if (s.equals(chanteur)) {
					nom = list.getNom();
					prenom = list.getPrenom();
				}
			}
			if (model.editAlbum(titleAncien, title, nom, prenom)) {
				listAlbum.getItems().set(index, title);
				labelAlbumView.setText(title);
				labelChanteurView.setText(chanteur);
				
				nomAlbum.setPromptText("");
				nomAlbum.setText("");
				listChanteurAlbum.getSelectionModel().select(null);
				
				nomAlbum.setStyle("-fx-border-width: 0;");
				listChanteurAlbum.setStyle("-fx-border-width: 0;");
			} else {
				nomAlbum.setStyle("-fx-border-color: red; -fx-border-width: 1;");
				listChanteurAlbum.setStyle("-fx-border-color: red; -fx-border-width: 1;");
			}
			
			
		} else {
			if (titleAncien == "" && title == "" || title =="") {
				nomAlbum.setStyle("-fx-border-color: red; -fx-border-width: 1;");
			} else {
				nomAlbum.setStyle("-fx-border-width: 0;");
			}
			if (chanteur == null) {
				listChanteurAlbum.setStyle("-fx-border-color: red; -fx-border-width: 1;");
			} else {
				listChanteurAlbum.setStyle("-fx-border-width: 0;");
			}
		}
	}
	
	public void ajouterAlbum() throws SQLException {
		 
		String title = nomAlbum.getText();
		String chanteur = listChanteurAlbum.getSelectionModel().getSelectedItem();
		if (title != "" && chanteur != null) {
			
			for (Chanteur list : model.getChanteur()) {
				String s = list.getNom() + " " + list.getPrenom();
				if (s.equals(chanteur)) {
					int id = list.getId();
					AlbumChanteur ac = model.addAlbum(title, id, list.getNom(), list.getPrenom());
					if (ac != null) {
						listAlbum.getItems().add(title);
						listAlbum.scrollTo(title);
						model.affichage();
						nomAlbum.setText("");
						listChanteurAlbum.getSelectionModel().select(null);
						
						nomAlbum.setStyle("-fx-border-width: 0;");
						listChanteurAlbum.setStyle("-fx-border-width: 0;");
						
						int nbAlbumList = listAlbum.getItems().size();
						nbAlbum.setText("(" + nbAlbumList + ")");
					} else {
						nomAlbum.setStyle("-fx-border-color: red; -fx-border-width: 1;");
						listChanteurAlbum.setStyle("-fx-border-color: red; -fx-border-width: 1;");
					}
				}
			}
			
		} else {
			if (title == "") {
				nomAlbum.setStyle("-fx-border-color: red; -fx-border-width: 1;");
			} else {
				nomAlbum.setStyle("-fx-border-width: 0;");
			}
			if (chanteur == null) {
				listChanteurAlbum.setStyle("-fx-border-color: red; -fx-border-width: 1;");
			} else {
				listChanteurAlbum.setStyle("-fx-border-width: 0;");
			}
		}
	}
	
	
	//CHANTEUR OPTION
	public void addChanteur() {
		boutonAjouterChanteur.setVisible(true);
		boutonModifierChanteur.setVisible(false);
		
		addChanteurOption.setStyle("-fx-background-color: #e43a51; ");
		editChanteurOption.setStyle("");
		
		nomChanteur.setPromptText("");
		prenomChanteur.setPromptText("");
		emailChanteur.setPromptText("");
		
		nomChanteur.setStyle("-fx-border-width: 0;");
		prenomChanteur.setStyle("-fx-border-width: 0;");
		emailChanteur.setStyle("-fx-border-width: 0;");
	}
		
	public void editChanteur() throws SQLException {
		boutonAjouterChanteur.setVisible(false);
		boutonModifierChanteur.setVisible(true);
		
		addChanteurOption.setStyle("");
		editChanteurOption.setStyle("-fx-background-color: #e43a51; ");
		
		nomChanteur.setStyle("-fx-border-width: 0;");
		prenomChanteur.setStyle("-fx-border-width: 0;");
		emailChanteur.setStyle("-fx-border-width: 0;");
		
		for (Chanteur list : model.getChanteur()) {
			String s = list.getNom() + " " + list.getPrenom();
			if (s.equals(listChanteur.getSelectionModel().getSelectedItem())) {
				nomChanteur.setPromptText(list.getNom());
				prenomChanteur.setPromptText(list.getPrenom());
				emailChanteur.setPromptText(list.getEmail());
			}
		}
	}

	public boolean deleteChanteur() throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		if (listChanteur.getSelectionModel().getSelectedItem() != null) {
			for (Chanteur list : model.getChanteur()) {
				String s = list.getNom() + " " + list.getPrenom();
				if (s.equals(listChanteur.getSelectionModel().getSelectedItem())) {
					model.supprimeChanteur(list.getNom(), list.getPrenom());
					int index = listChanteur.getSelectionModel().getSelectedIndex();
					listChanteur.getItems().remove(index);
					listChanteurAlbum.getItems().remove(index);
					listChanteur.getSelectionModel().select(null);
					
					labelMusic.setStyle("-fx-text-fill: red");
					labelChanteurMusic.setText("---");
					labelMusic.setText("No music");
					stopMusic();
					
					int nbChanteurList = listChanteur.getItems().size();
					nbChanteur.setText("(" + nbChanteurList + ")");
					return true;
				}
			}
		}
		return false;
	}
		
	//CHANTEUR BOUTON
	public void modifierChanteur() throws SQLException {
		
		String nom= nomChanteur.getText();
		String prenom = prenomChanteur.getText();
		String email = emailChanteur.getText();
		
		if (nom != "" && prenom != "" && email != "") {
			String ancienNom = nomChanteur.getPromptText();
			String ancienPrenom = prenomChanteur.getPromptText();
			String ancienEmail = emailChanteur.getPromptText();

			model.editChanteur(ancienNom, nom, ancienPrenom, prenom, ancienEmail, email);
			
			nomChanteur.setText("");
			prenomChanteur.setText("");
			emailChanteur.setText("");
			
			nomChanteur.setStyle("-fx-border-width: 0;");
			prenomChanteur.setStyle("-fx-border-width: 0;");
			emailChanteur.setStyle("-fx-border-width: 0;");
			
			String s = nom + " " + prenom;
			listChanteur.getItems().set(listChanteur.getSelectionModel().getSelectedIndex(), s);
			listChanteurAlbum.getItems().set(listChanteur.getSelectionModel().getSelectedIndex(), s);
			
			nomChanteur.setPromptText("");
			prenomChanteur.setPromptText("");
			emailChanteur.setPromptText("");
			
			labelChanteurMusic.setText(s);
		} else {
			if (nom == "") {
				nomChanteur.setStyle("-fx-border-color: red; -fx-border-width: 1;");
			} else {
				nomChanteur.setStyle("-fx-border-width: 0;");
			}
			if (prenom == "") {
				prenomChanteur.setStyle("-fx-border-color: red; -fx-border-width: 1;");
			} else {
				prenomChanteur.setStyle("-fx-border-width: 0;");
			}
			if (email == "") {
				emailChanteur.setStyle("-fx-border-color: red; -fx-border-width: 1;");
			} else {
				emailChanteur.setStyle("-fx-border-width: 0;");
			}
		}
	}
		
	public void ajouterChanteur() throws SQLException {
		String nom = nomChanteur.getText();
		String prenom = prenomChanteur.getText();
		String email = emailChanteur.getText();
		
		if (nom != "" && prenom != "" && email != "") {
			if (model.addChanteur(nom,prenom,email)) {
				String s = nom + " " + prenom;
				listChanteur.getItems().add(s);
				listChanteurAlbum.getItems().add(s);
				
				nomChanteur.setText("");
				prenomChanteur.setText("");
				emailChanteur.setText("");
				
				nomChanteur.setStyle("-fx-border-width: 0;");
				prenomChanteur.setStyle("-fx-border-width: 0;");
				emailChanteur.setStyle("-fx-border-width: 0;");
			} else {
				System.out.println("doublons chanteur");
				nomChanteur.setStyle("-fx-border-color: red; -fx-border-width: 1;");
				prenomChanteur.setStyle("-fx-border-color: red; -fx-border-width: 1;");
				emailChanteur.setStyle("-fx-border-color: red; -fx-border-width: 1;");
			}
			int nbChanteurList = listChanteur.getItems().size();
			nbChanteur.setText("(" + nbChanteurList + ")");
		} else {
			if (nom == "") {
				nomChanteur.setStyle("-fx-border-color: red; -fx-border-width: 1;");
			} else {
				nomChanteur.setStyle("-fx-border-width: 0;");
			}
			if (prenom == "") {
				prenomChanteur.setStyle("-fx-border-color: red; -fx-border-width: 1;");
			} else {
				prenomChanteur.setStyle("-fx-border-width: 0;");
			}
			if (email == "") {
				emailChanteur.setStyle("-fx-border-color: red; -fx-border-width: 1;");
			} else {
				emailChanteur.setStyle("-fx-border-width: 0;");
			}
		}
	}
	
	public void addMoreInfoChanteur() {
		addMoreInfoChanteur.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Parent rootFXML;
				try {
					rootFXML = FXMLLoader.load(getClass().getResource("VueInfoChanteur.fxml"));
					Scene sceneFXML = new Scene(rootFXML);
					
					Stage primaryStage = new Stage();
					primaryStage.setTitle("Info Chanteur");
					primaryStage.setScene(sceneFXML);

					primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	//MEDIA
	public void changeMusic(MouseEvent e) throws SQLException {
		if (listChanteur.getSelectionModel().getSelectedItem() != null) {
			String nom = null;
			String prenom = null;
			String s = "";
			for (Chanteur list : model.getChanteur()) {
				s = list.getNom() + " " + list.getPrenom();
				if (s.equals(listChanteur.getSelectionModel().getSelectedItem())) {
					nom = list.getNom();
					prenom = list.getPrenom();
					if (editChanteurOption.getStyle() == "-fx-background-color: #e43a51; ") {
						nomChanteur.setPromptText(list.getNom());
						prenomChanteur.setPromptText(list.getPrenom());
						emailChanteur.setPromptText(list.getEmail());
					}
				}
			} 
			ChanteurMusic music = model.musicData(nom, prenom);
			if ("No music".equals(music.getTitle())) {
				labelMusic.setText(music.getTitle());
				labelMusic.setStyle("-fx-text-fill: red");
			} else {
				labelMusic.setText(music.getTitle());
				labelMusic.setStyle("-fx-text-fill: black");
				labelMusic.setStyle("-fx-font-weight: bold");
			}
			labelChanteurMusic.setText(listChanteur.getSelectionModel().getSelectedItem());
			
			if (musicValue) {
				musicValue = false;
				clip.stop();
			}
			
		}
		imagePlay.setVisible(true);
		buttonPlay.setVisible(true);
		
		imagePause.setVisible(false);
		buttonPause.setVisible(false);
	}
	
	public void nextMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException, SQLException {
		listChanteur.getSelectionModel().selectNext();
		changeMusic(null);
		playMusic();
	}
	
	public void previousMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException, SQLException {
		listChanteur.getSelectionModel().selectPrevious();
		changeMusic(null);
		playMusic();
	}
	
	public void playMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		String title = labelMusic.getText();
		if (title != "" && title != "No music") { 
			if (!musicValue) {
				
				File f = new File(model.getMusic(title));
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(f);
				clip = AudioSystem.getClip();
				clip.open(audioStream);
				
				clip.start();
				musicValue = true;
			} else {
				clip.start();
			}
			
			imagePlay.setVisible(false);
			buttonPlay.setVisible(false);
			
			imagePause.setVisible(true);
			buttonPause.setVisible(true);
		}
	}
	
	public void stopMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		if (musicValue) {
			clip.stop();
			musicValue = false;
		}
		imagePlay.setVisible(true);
		buttonPlay.setVisible(true);
		
		imagePause.setVisible(false);
		buttonPause.setVisible(false);
	}
	
	public void pauseMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		if (musicValue) {
			clip.stop();
		}
		imagePlay.setVisible(true);
		buttonPlay.setVisible(true);
		
		imagePause.setVisible(false);
		buttonPause.setVisible(false);
	}
	
	//ADD IMAGE
	public void addImage() throws IOException, SQLException {
		ArrayList<String> listImage = model.getImage();
		int listImageSize = listImage.size();
		
		
		Image img = new Image(listImage.get(imageIndex));
		imageView.setImage(img);
		
		if (imageIndex == listImageSize - 1) {
			imageIndex = 0;
		} else {
			imageIndex +=1;
		}
	}
	
	//CSV
	public void exportCSV() throws SQLException {
		model.exportCSV();
	}
	
	
	
	//LANGUES
	public void changeLangueFrancais() {
		labelListAlbum.setText("Liste des albums");
		labelNouveauAlbum.setText("Nouveau nom de l'album");
		labelNomAlbum.setText("Nom de l'album");
		labelChanteur.setText("Chanteur");
		boutonModifierAlbum.setText("Modifier");
		boutonAjouterAlbum.setText("Ajouter");
		
		labelListChanteur.setText("Liste des chanteurs");
		labelNomChanteur.setText("Nom");
		labelPrenomChanteur.setText("Prénom");
		labelMail.setText("Adresse mail");
		boutonModifierChanteur.setText("Modifier");
		boutonAjouterChanteur.setText("Ajouter");
		
		labelFichier.setText("Fichier");
		labelLangues.setText("Langues");
		labelTitre.setText("S'évader et se divertir");
		labelFrancais.setText("Français");
		labelAnglais.setText("Anglais");
	}
	
	public void changeLangueAnglais() {
		labelListAlbum.setText("Album list");
		labelNouveauAlbum.setText("New album name");
		labelNomAlbum.setText("Album name");
		labelChanteur.setText("Singer");
		boutonModifierAlbum.setText("Edit");
		boutonAjouterAlbum.setText("Add");
		
		labelListChanteur.setText("Singer list");
		labelNomChanteur.setText("Name");
		labelPrenomChanteur.setText("Surname");
		labelMail.setText("Mail address");
		boutonModifierChanteur.setText("Edit");
		boutonAjouterChanteur.setText("Add");
		
		labelFichier.setText("File");
		labelLangues.setText("Languages");
		labelTitre.setText("Escape and have fun");
		labelFrancais.setText("French");
		labelAnglais.setText("English");
	}
}