package eu.michalbuda.offerGenerator.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class FireBirdDAO {
private static Connection conn;
	
	public FireBirdDAO(){
		try {
			conn = FireBirdConnector.getInstance().getConnection();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to EasyUploader FDB database.", "Error", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			FireBirdConnector.getInstance().disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = null;
	}
	
	
	public void printCodes(){
		String sql = "Select KOD from AUKCJE";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(sql);
			System.out.println("after pst");
			rs = pst.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString("KOD"));
			}
		} catch (SQLException e) {
			System.out.println("FireBirdDAO problem " + e);
		} finally {
			try {
				pst.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
	
	
}
