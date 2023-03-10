package entite;

public class Address {
	private int id;
	private String street;
	private String suite;
	private String city;
	private String zipcode;
	
	public Address(int id, String street, String suite, String city, String zipcode) {
		this.id=id;
		this.street=street;
		this.suite=suite;
		this.city=city;
		this.zipcode=zipcode;
	}
	
	public int getId() {
	    return id;
	}
	public void setId(int id) {
	    this.id=id;
	}
	
	public String getStreet() {
	    return street;
	}
	public void setStreet(String street) {
	    this.street=street;
	}
	
	public String getSuite() {
	    return suite;
	}
	public void setSuite(String suite) {
	    this.suite=suite;
	}
	
	public String getCity() {
	    return city;
	}
	public void setCity(String city) {
	    this.city=city;
	}
	
	public String getZipcode() {
	    return zipcode;
	}
	public void setZipcode(String zipcode) {
	    this.zipcode=zipcode;
	}
}
