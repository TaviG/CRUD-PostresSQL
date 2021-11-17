package model;

public class Autori {
	private int autorid;
	private String numeautor;
	private String prenumeautor;
	private String taraorigine;
	
	
	public Autori(int autorid, String numeautor, String prenumeautor, String taraorigine) {
		super();
		this.autorid = autorid;
		this.numeautor = numeautor;
		this.prenumeautor = prenumeautor;
		this.taraorigine = taraorigine;
	}
	
	
	public Autori(String numeautor, String prenumeautor, String taraorigine) {
		super();
		this.numeautor = numeautor;
		this.prenumeautor = prenumeautor;
		this.taraorigine = taraorigine;
	}


	public int getAutorid() {
		return autorid;
	}
	public void setAutorid(int autorid) {
		this.autorid = autorid;
	}
	public String getNumeautor() {
		return numeautor;
	}
	public void setNumeautor(String numeautor) {
		this.numeautor = numeautor;
	}
	public String getPrenumeautor() {
		return prenumeautor;
	}
	public void setPrenumeautor(String prenumeautor) {
		this.prenumeautor = prenumeautor;
	}
	public String getTaraorigine() {
		return taraorigine;
	}
	public void setTaraorigine(String taraorigine) {
		this.taraorigine = taraorigine;
	}
	
}
