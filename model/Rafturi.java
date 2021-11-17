package model;

public class Rafturi {
	private int raftid;
	private Carti carte;
	private Biblioteca biblioteca;
	private String codraft;
	
	
	public Rafturi(int raftid, Carti carte, Biblioteca biblioteca, String codraft) {
		super();
		this.raftid = raftid;
		this.carte = carte;
		this.biblioteca = biblioteca;
		this.codraft = codraft;
	}


	public Rafturi(Carti carte, Biblioteca biblioteca, String codraft) {
		super();
		this.carte = carte;
		this.biblioteca = biblioteca;
		this.codraft = codraft;
	}


	public int getRaftid() {
		return raftid;
	}


	public void setRaftid(int raftid) {
		this.raftid = raftid;
	}


	public Carti getCarte() {
		return carte;
	}


	public void setCarte(Carti carte) {
		this.carte = carte;
	}


	public Biblioteca getBiblioteca() {
		return biblioteca;
	}


	public void setBiblioteca(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}


	public String getCodraft() {
		return codraft;
	}


	public void setCodraft(String codraft) {
		this.codraft = codraft;
	}
	
	
	
	
}
