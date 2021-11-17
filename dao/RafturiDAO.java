package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Autori;
import model.Biblioteca;
import model.Carti;
import model.Rafturi;

public class RafturiDAO {
	private CartiDAO cartidao = new CartiDAO();
	private BibliotecaDAO bibliotecadao = new BibliotecaDAO();
	private String jdbcURL = "jdbc:postgresql://localhost:5432/proiect3";
	private String jdbcUsername = "postgres";
	private String jdbcPassword = "tavi1999";
	
	private static final String INSERT_RAFT_QUERY = "INSERT INTO proiect3.rafturi" + "(carteid, bibliotecaid, codraft) VALUES " + " (?, ?, ?);";
	private static final String SELECT_RAFT_BY_ID = "select raftid, carteid, bibliotecaid, codraft  from proiect3.rafturi where raftid = ?;";
	private static final String SELECT_ALL_RAFTURI = "select * from proiect3.rafturi inner join proiect3.carti on proiect3.rafturi.carteid = proiect3.carti.carteid inner join proiect3.biblioteca on proiect3.rafturi.bibliotecaid = proiect3.biblioteca.bibliotecaid order by raftid;";
	private static final String DELETE_RAFT_QUERY = "delete from proiect3.rafturi where raftid = ?;";
	private static final String UPDATE_RAFT_QUERY = "update proiect3.rafturi set carteid = ?, bibliotecaid = ?, codraft = ? where raftid = ?;";
	
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
	
	public  void insertRaft(Rafturi raft) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RAFT_QUERY)){
			preparedStatement.setInt(1, raft.getCarte().getCarteid());
			preparedStatement.setInt(2, raft.getBiblioteca().getBibliotecaid());
			preparedStatement.setString(3, raft.getCodraft());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateRaft(Rafturi raft) throws SQLException {
		boolean rowUpdated;
		try (	
				Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RAFT_QUERY)){
			preparedStatement.setInt(1, raft.getCarte().getCarteid());
			preparedStatement.setInt(2, raft.getBiblioteca().getBibliotecaid());
			preparedStatement.setString(3, raft.getCodraft());
			preparedStatement.setInt(4, raft.getRaftid());
			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	public Rafturi selectRaft(int id) {
		Rafturi raft = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RAFT_BY_ID)){
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int carteid = rs.getInt("carteid");
				int bibliotecaid = rs.getInt("bibliotecaid");
				String codraft = rs.getString("codraft");
				Carti carte = cartidao.selectCarte(carteid);
				Biblioteca biblioteca = bibliotecadao.selectBiblioteca(bibliotecaid);
				raft = new Rafturi(id, carte, biblioteca, codraft);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return raft;
	}
	
	public List<Rafturi> selectAllRafturi() {
		List<Rafturi> rafturi = new ArrayList<>();
		try (	Connection connection = getConnection();
				
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RAFTURI);){
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("raftid");
				int carteid = rs.getInt("carteid");
				int bibliotecaid = rs.getInt("bibliotecaid");
				String codraft = rs.getString("codraft");
				Carti carte = cartidao.selectCarte(carteid);
				Biblioteca biblioteca = bibliotecadao.selectBiblioteca(bibliotecaid);
				rafturi.add( new Rafturi(id, carte, biblioteca, codraft) );
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return rafturi;
	}
	
	
	public boolean deleteRaft(int id) throws SQLException{
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_RAFT_QUERY);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	
	
}
