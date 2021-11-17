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
import model.Biblioteca;

public class BibliotecaDAO {

	private String jdbcURL = "jdbc:postgresql://localhost:5432/proiect3";
	private String jdbcUsername = "postgres";
	private String jdbcPassword = "tavi1999";
	
	private static final String INSERT_BIBLIOTECA_QUERY = "INSERT INTO proiect3.biblioteca" + "(denumire, adresa) VALUES " + " (?, ?);";
	private static final String SELECT_BIBLIOTECA_BY_ID = "select bibliotecaid, denumire, adresa from proiect3.biblioteca where bibliotecaid = ?;";
	private static final String SELECT_ALL_BIBLIOTECA = "select * from proiect3.biblioteca;";
	private static final String DELETE_BIBLIOTECA_QUERY = "delete from proiect3.biblioteca where bibliotecaid = ?;";
	private static final String UPDATE_BIBLIOTECA_QUERY = "update proiect3.biblioteca set denumire = ?, adresa = ? where bibliotecaid = ?;";
	
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
	
	public  void insertBiblioteca(Biblioteca biblioteca) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BIBLIOTECA_QUERY)){
			preparedStatement.setString(1, biblioteca.getDenumire());
			preparedStatement.setString(2, biblioteca.getAdresa());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateBiblioteca(Biblioteca biblioteca) throws SQLException {
		boolean rowUpdated;
		try (	
				Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BIBLIOTECA_QUERY)){
			preparedStatement.setString(1, biblioteca.getDenumire());
			preparedStatement.setString(2, biblioteca.getAdresa());
			preparedStatement.setInt(3, biblioteca.getBibliotecaid());
			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	public Biblioteca selectBiblioteca(int id) {
		Biblioteca biblioteca = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BIBLIOTECA_BY_ID)){
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				String denumire = rs.getString("denumire");
				String adresa = rs.getString("adresa");
				biblioteca = new Biblioteca(id, denumire, adresa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return biblioteca;
	}
	
	public List<Biblioteca> selectAllBiblioteca() {
		List<Biblioteca> biblioteca = new ArrayList<>();
		try (	Connection connection = getConnection();
				
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BIBLIOTECA);){
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("bibliotecaid");
				String denumire = rs.getString("denumire");
				String adresa = rs.getString("adresa");
				biblioteca.add( new Biblioteca(id, denumire, adresa) );
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return biblioteca;
	}
	
	
	public boolean deleteBiblioteca(int id) throws SQLException{
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_BIBLIOTECA_QUERY);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	
	
}


