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
import model.Users;

public class UsersDAO {
	
	private String jdbcURL = "jdbc:postgresql://localhost:5432/proiect3";
	private String jdbcUsername = "postgres";
	private String jdbcPassword = "";
	
	private static final String INSERT_USER_QUERY = "INSERT INTO proiect3.users" + "(username, password, admin) VALUES " + " (?, ?, ?);";
	private static final String SELECT_USER_BY_USERNAME = "select userid, username, password, admin  from proiect3.users where username = ?;";
	
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
	
	public  void insertUser(Users user) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY)){
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setBoolean(3, user.isAdmin());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Users selectUser(String username) {
		Users user = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME)){
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("userid");
				String password = rs.getString("password");
				Boolean admin = rs.getBoolean("admin");
				user = new Users(id, username, password, admin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
