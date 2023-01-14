package Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import entite.Address;
import entite.Company;
import entite.Album;
import entite.Chanteur;
import entite.ImageAlbum;


public class WebService {
	
	public ArrayList<Object> getListChanteur() {
		ArrayList<Chanteur> listChanteur = new ArrayList<Chanteur>();
		ArrayList<Address> listAddress = new ArrayList<Address>();
		ArrayList<Company> listCompany = new ArrayList<Company>();
		
		ArrayList<Object> list = new ArrayList<>();
		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/users");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("GET");
			
			StringBuffer sb = new StringBuffer();
			if(connection.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while((line = in.readLine()) != null) {
					sb.append(line);
				}
				JsonElement jElement = new JsonParser().parse(sb.toString());
				JsonArray jArray = jElement.getAsJsonArray();
				
				for(JsonElement el: jArray) {
					
					JsonObject jobject = el.getAsJsonObject();
					
					//Chanteur
					int id = jobject.get("id").getAsInt();
					String name = jobject.get("name").getAsString();
					String username = jobject.get("username").getAsString();
					String email = jobject.get("email").getAsString();
					
					Chanteur chanteur = new Chanteur(id, name, username, email);
					
					listChanteur.add(chanteur);
					
					//Address
					JsonObject jobjectAddress = jobject.get("address").getAsJsonObject();
					
					String street = jobjectAddress.get("street").getAsString();
					String suite = jobjectAddress.get("suite").getAsString();
					String city = jobjectAddress.get("city").getAsString();
					String zipcode = jobjectAddress.get("zipcode").getAsString();
					
					Address address = new Address(id, street,suite,city,zipcode);
					
					listAddress.add(address);
					
					//Company
					JsonObject jobjectCompany = jobject.get("company").getAsJsonObject();
					
					String nameCompany = jobjectCompany.get("name").getAsString();
					String catchPhrase = jobjectCompany.get("catchPhrase").getAsString();
					String bs = jobjectCompany.get("bs").getAsString();
					
					Company company = new Company(id, nameCompany, catchPhrase, bs);
					
					listCompany.add(company);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		list.add(listChanteur);
		list.add(listAddress);
		list.add(listCompany);
		return list;
	}
	
	public ArrayList<Album> getListAlbum() {
		ArrayList<Album> listAlbum = new ArrayList<Album>();
		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/albums");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("GET");
			
			StringBuffer sb = new StringBuffer();
			if(connection.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while((line = in.readLine()) != null) {
					sb.append(line);
				}
				JsonElement jElement = new JsonParser().parse(sb.toString());
				JsonArray jArray = jElement.getAsJsonArray();
				for(JsonElement el: jArray) {
					JsonObject jobject = el.getAsJsonObject();
					
					int id = jobject.get("id").getAsInt();
					int idUser = jobject.get("userId").getAsInt();	
					String title = jobject.get("title").getAsString();
					
					Album a = new Album(id, idUser, title);
					
					listAlbum.add(a);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listAlbum;
	}
	
	public ArrayList<ImageAlbum> getListPhoto() {
		ArrayList<ImageAlbum> listImageAlbum = new ArrayList<ImageAlbum>();
		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/photos");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("GET");
			
			StringBuffer sb = new StringBuffer();
			if(connection.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while((line = in.readLine()) != null) {
					sb.append(line);
				}
				JsonElement jElement = new JsonParser().parse(sb.toString());
				JsonArray jArray = jElement.getAsJsonArray();
				for(JsonElement el: jArray) {
					JsonObject jobject = el.getAsJsonObject();
					
					int id = jobject.get("id").getAsInt();
					int idAlbum = jobject.get("albumId").getAsInt();
					String title = jobject.get("title").getAsString();
					String urlPhoto = jobject.get("url").getAsString() + ".png";
					String thumbnail = jobject.get("thumbnailUrl").getAsString() + ".png";
					ImageAlbum ia = new ImageAlbum(id, idAlbum, title, urlPhoto, thumbnail);
					
					listImageAlbum.add(ia);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listImageAlbum;
	}
}
