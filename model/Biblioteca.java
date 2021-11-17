package model;

public class Biblioteca {
	private int bibliotecaid;
	private String denumire;
	private String adresa;
	
	public Biblioteca(int bibliotecaid, String denumire, String adresa) {
		super();
		this.bibliotecaid = bibliotecaid;
		this.denumire = denumire;
		this.adresa = adresa;
	}

	public Biblioteca(String denumire, String adresa) {
		super();
		this.denumire = denumire;
		this.adresa = adresa;
	}

	public int getBibliotecaid() {
		return bibliotecaid;
	}

	public void setBibliotecaid(int bibliotecaid) {
		this.bibliotecaid = bibliotecaid;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	
	
}
