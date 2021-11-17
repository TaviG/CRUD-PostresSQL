package model;

public class Volume {
	private int volumid;
	private Autori autor;
	private Carti carte;
	private int nrvolum;
	
	public Volume(int volumid, Autori autor, Carti carte, int nrvolum) {
		super();
		this.volumid = volumid;
		this.autor = autor;
		this.carte = carte;
		this.nrvolum = nrvolum;
	}

	public Volume(Autori autor, Carti carte, int nrvolum) {
		super();
		this.autor = autor;
		this.carte = carte;
		this.nrvolum = nrvolum;
	}

	public int getVolumid() {
		return volumid;
	}

	public void setVolumid(int volumid) {
		this.volumid = volumid;
	}

	public Autori getAutor() {
		return autor;
	}

	public void setAutor(Autori autor) {
		this.autor = autor;
	}

	public Carti getCarte() {
		return carte;
	}

	public void setCarte(Carti carte) {
		this.carte = carte;
	}

	public int getNrvolum() {
		return nrvolum;
	}

	public void setNrvolum(int nrvolum) {
		this.nrvolum = nrvolum;
	}
	
	
	
}
