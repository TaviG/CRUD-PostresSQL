package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Autori;
import model.Carti;
import model.Rafturi;
import model.Volume;

public class VolumeDAO {
	private String jdbcURL = "jdbc:postgresql://localhost:5432/proiect3";
	private String jdbcUsername = "postgres";
	private String jdbcPassword = "";
	private AutoriDAO autoridao = new AutoriDAO();
	private CartiDAO cartidao = new CartiDAO();
	
	private static final String INSERT_VOLUM_QUERY = "INSERT INTO proiect3.volume" + "(autorid, carteid, nrvolum) VALUES " + " (?, ?, ?);";
	private static final String SELECT_VOLUM_BY_ID = "select volumid, autorid, carteid, nrvolum from proiect3.volume where volumid = ?;";
	private static final String SELECT_ALL_VOLUME = "select * from proiect3.volume inner join proiect3.autori on proiect3.volume.autorid = proiect3.autori.autorid inner join proiect3.carti on proiect3.volume.carteid = proiect3.carti.carteid order by volumid;";
	private static final String DELETE_VOLUM_QUERY = "delete from proiect3.volume where volumid = ?;";
	private static final String UPDATE_VOLUM_QUERY = "update proiect3.volume set autorid = ?, carteid = ?, nrvolum = ? where volumid = ?;";
	
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
	
	public  void insertVolum(Volume volum) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VOLUM_QUERY)){
			preparedStatement.setInt(1, volum.getAutor().getAutorid());
			preparedStatement.setInt(2, volum.getCarte().getCarteid());
			preparedStatement.setInt(3, volum.getNrvolum());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateVolum(Volume volum) throws SQLException {
		boolean rowUpdated;
		try (	
				Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_VOLUM_QUERY)){
			preparedStatement.setInt(1, volum.getAutor().getAutorid());
			preparedStatement.setInt(2, volum.getCarte().getCarteid());
			preparedStatement.setInt(3, volum.getNrvolum());
			preparedStatement.setInt(4, volum.getVolumid());
			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	public Volume selectVolum(int id) {
		Volume volum = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_VOLUM_BY_ID)){
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int autorid = rs.getInt("autorid");
				int carteid = rs.getInt("carteid");
				int nrvolum = rs.getInt("nrvolum");
				
				Autori autor = autoridao.selectAutor(autorid);
				Carti carte = cartidao.selectCarte(carteid);
				
				volum = new Volume(id, autor, carte, nrvolum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return volum;
	}
	
	public List<Volume> selectAllVolume() {
		List<Volume> volume = new ArrayList<>();
		try (	Connection connection = getConnection();
				
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_VOLUME);){
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("volumid");
				int autorid = rs.getInt("autorid");
				int carteid = rs.getInt("carteid");
				int nrvolum = rs.getInt("nrvolum");
				
				Autori autor = autoridao.selectAutor(autorid);
				Carti carte = cartidao.selectCarte(carteid);
				
				volume.add( new Volume(id, autor, carte, nrvolum) );
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return volume;
	}
	
	
	public boolean deleteVolum(int id) throws SQLException{
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_VOLUM_QUERY);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
}
