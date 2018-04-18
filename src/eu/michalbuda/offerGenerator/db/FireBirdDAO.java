package eu.michalbuda.offerGenerator.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import eu.michalbuda.offerGenerator.model.Offer;


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
	
	public ArrayList<Offer> getOffersFromGroup(int groupId){
		ArrayList<Offer> list = new ArrayList<>();
		
		String sql = "SELECT id, kod FROM aukcje WHERE id_grupa = ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, groupId);
			rs = pst.executeQuery();
			while(rs.next()) {
				Offer offer = new Offer(rs.getInt("id"));
				offer.setProductCode(rs.getString("kod"));
				list.add(offer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		
		return list;
	}
	
	public String getOldProductText(int idAukcji) {
		String sql = "Select opis from AUK_OPISY where id_aukcji = ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, idAukcji);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getString("opis");
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "null";
	}
	
	public String getProductText(int idAukcji) {
		String sql = "Select nowy_opis from AUK_OPISY where id_aukcji = ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, idAukcji);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getString("opis");
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "null";
	}
	
	public Offer getProductText(Offer offer) {
		String sql = "Select nowy_opis from AUK_OPISY where id_aukcji = ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offer.getOfferId());
			rs = pst.executeQuery();
			if (rs.next()) {
				offer.setText(rs.getString("nowy_opis"));
				return offer;
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return offer;
	}
	
	private void insertProductText(Offer offer){
		String sql = "Insert into auk_opisy (id_aukcji,nowy_opis) Values (?,?,?)";
		PreparedStatement pst = null;
		try{
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offer.getOfferId());
			pst.setBytes(2, offer.getText().getBytes());
			pst.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		} finally{
			try {
				pst.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
}
