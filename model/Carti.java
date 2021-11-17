package model;

import java.sql.Date;

public class Carti {
	private int carteid;
	private String denumire;
	private Date anaparitie;
	private String editura;
	
	public Carti(int carteid, String denumire, Date anaparitie, String editura) {
		super();
		this.carteid = carteid;
		this.denumire = denumire;
		this.anaparitie = anaparitie;
		this.editura = editura;
	}

	public Carti(String denumire, Date anaparitie, String editura) {
		super();
		this.denumire = denumire;
		this.anaparitie = anaparitie;
		this.editura = editura;
	}

	public int getCarteid() {
		return carteid;
	}

	public void setCarteid(int carteid) {
		this.carteid = carteid;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public Date getAnaparitie() {
		return anaparitie;
	}

	public void setAnaparitie(Date anaparitie) {
		this.anaparitie = anaparitie;
	}

	public String getEditura() {
		return editura;
	}

	public void setEditura(String editura) {
		this.editura = editura;
	}
	
	
	
}
