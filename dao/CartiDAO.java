package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import model.Carti;

public class CartiDAO {
	private String jdbcURL = "jdbc:postgresql://localhost:5432/proiect3";
	private String jdbcUsername = "postgres";
	private String jdbcPassword = "";
	
	private static final String INSERT_CARTI_QUERY = "INSERT INTO proiect3.carti" + "(denumire, anaparitie, editura) VALUES " + " (?, ?, ?);";
	private static final String SELECT_CARTE_BY_ID = "select carteid, denumire, anaparitie, editura from proiect3.carti where carteid = ?;";
	private static final String SELECT_ALL_CARTI = "select * from proiect3.carti;";
	private static final String DELETE_CARTI_QUERY = "delete from proiect3.carti where carteid = ?;";
	private static final String UPDATE_CARTI_QUERY = "update proiect3.carti set denumire = ?, anaparitie = ?, editura = ? where carteid = ?;";
	
	protected Connection getConnection()  {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public  void insertCarte(Carti carte) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CARTI_QUERY)){
			preparedStatement.setString(1, carte.getDenumire());
			preparedStatement.setDate(2, carte.getAnaparitie());
			preparedStatement.setString(3, carte.getEditura());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateCarte(Carti carte) throws SQLException {
		boolean rowUpdated;
		try (	
				Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CARTI_QUERY)){
			preparedStatement.setString(1, carte.getDenumire());
			preparedStatement.setDate(2, carte.getAnaparitie());
			preparedStatement.setString(3, carte.getEditura());
			preparedStatement.setInt(4, carte.getCarteid());
			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	public Carti selectCarte(int id) {
		Carti carte = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CARTE_BY_ID)){
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				String denumire = rs.getString("denumire");
				Date anaparitie = rs.getDate("anaparitie");
				String editura = rs.getString("editura");
				carte = new Carti(id, denumire, anaparitie, editura);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return carte;
	}
	
	public List<Carti> selectAllCarti() {
		List<Carti> carti = new ArrayList<>();
		try (	Connection connection = getConnection();
				
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CARTI);){
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("carteid");
				String denumire = rs.getString("denumire");
				Date anaparitie = rs.getDate("anaparitie");
				String editura = rs.getString("editura");
				carti.add( new Carti(id, denumire, anaparitie, editura) );
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return carti;
	}
	
	
	public boolean deleteCarte(int id) throws SQLException{
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_CARTI_QUERY);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
}
