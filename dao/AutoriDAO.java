package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Autori;

public class AutoriDAO {
	private String jdbcURL = "jdbc:postgresql://localhost:5432/proiect3";
	private String jdbcUsername = "postgres";
	private String jdbcPassword = "tavi1999";
	
	private static final String INSERT_AUTORI_QUERY = "INSERT INTO proiect3.autori" + "(numeautor, prenumeautor, taraorigine) VALUES " + " (?, ?, ?);";
	private static final String SELECT_AUTOR_BY_ID = "select autorid, numeautor, prenumeautor, taraorigine from proiect3.autori where autorid = ?;";
	private static final String SELECT_ALL_AUTORI = "select * from proiect3.autori;";
	private static final String DELETE_AUTORI_QUERY = "delete from proiect3.autori where autorid = ?;";
	private static final String UPDATE_AUTORI_QUERY = "update proiect3.autori set numeautor = ?, prenumeautor = ?, taraorigine = ? where autorid = ?;";
	
	public AutoriDAO() {}
	
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
	
	public  void insertAutor(Autori autor) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTORI_QUERY)){
			preparedStatement.setString(1, autor.getNumeautor());
			preparedStatement.setString(2, autor.getPrenumeautor());
			preparedStatement.setString(3, autor.getTaraorigine());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateAutor(Autori autor) throws SQLException {
		boolean rowUpdated;
		try (	
				Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTORI_QUERY)){
			preparedStatement.setString(1, autor.getNumeautor());
			preparedStatement.setString(2, autor.getPrenumeautor());
			preparedStatement.setString(3, autor.getTaraorigine());
			preparedStatement.setInt(4, autor.getAutorid());
			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	public Autori selectAutor(int id) {
		Autori autor = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTOR_BY_ID)){
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				String numeautor = rs.getString("numeautor");
				String prenumeautor = rs.getString("prenumeautor");
				String taraorigine = rs.getString("taraorigine");
				autor = new Autori(id, numeautor, prenumeautor, taraorigine);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return autor;
	}
	
	public List<Autori> selectAllAutori() {
		List<Autori> autori = new ArrayList<>();
		try (	Connection connection = getConnection();
				
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_AUTORI);){
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("autorid");
				String numeautor = rs.getString("numeautor");
				String prenumeautor = rs.getString("prenumeautor");
				String taraorigine = rs.getString("taraorigine");
				autori.add( new Autori(id, numeautor, prenumeautor, taraorigine) );
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return autori;
	}
	
	
	public boolean deleteAutor(int id) throws SQLException{
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_AUTORI_QUERY);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	
	
}
