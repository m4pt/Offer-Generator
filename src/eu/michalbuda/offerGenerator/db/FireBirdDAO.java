package eu.michalbuda.offerGenerator.db;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.codec.Charsets;

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
		
		String sql = "SELECT id, kod, tytul FROM aukcje WHERE id_grupa = ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, groupId);
			rs = pst.executeQuery();
			while(rs.next()) {
				Offer offer = new Offer(rs.getInt("id"));
				offer.setProductCode(rs.getString("kod"));
				offer.setTitle(rs.getString("tytul"));
				list.add(offer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<Offer> getOffersFromGroupWithoutNewText(int groupId){
		ArrayList<Offer> list = new ArrayList<>();
		
		String sql = "SELECT id, kod, tytul FROM aukcje WHERE id_grupa = ? AND id IN (SELECT id_aukcji from auk_opisy where bit_length(nowy_opis) < 50)";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, groupId);
			rs = pst.executeQuery();
			while(rs.next()) {
				Offer offer = new Offer(rs.getInt("id"));
				offer.setProductCode(rs.getString("kod"));
				offer.setTitle(rs.getString("tytul"));
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
	
	public Offer getOldProductText(Offer offer) {
		String sql = "Select opis from AUK_OPISY where id_aukcji = ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offer.getOfferId());
			rs = pst.executeQuery();
			if (rs.next()) {
				offer.setOldHtmlText(rs.getString("opis"));
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
	
	public void setProductText(Offer offer){
		String sql = "UPDATE auk_opisy SET nowy_opis = ? WHERE id_aukcji = ?";
		PreparedStatement pst = null;
		try{
			pst = conn.prepareStatement(sql);
			pst.setInt(2, offer.getOfferId());
			pst.setBytes(1, offer.getText().getBytes("Cp1250"));
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
		
		setNewDescriptionMarker(1, offer);
	}
	
	public void setNewDescriptionMarker(int marker, Offer offer) {
		String sql = "UPDATE aukcje SET czy_nowy_opis = ? WHERE id = ?";
		PreparedStatement pst = null;
		try{
			pst = conn.prepareStatement(sql);
			pst.setInt(1, marker);
			pst.setInt(2, offer.getOfferId());
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
	
	public ArrayList<Offer> getOffersWithNewTexts() {
		ArrayList<Offer> list = new ArrayList<Offer>();
		
		String sql = "SELECT id_aukcji,nowy_opis from auk_opisy where bit_length(nowy_opis) > 000";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				Offer offer = new Offer();
				offer.setOfferId(rs.getInt("id_aukcji"));
				offer.setText(rs.getString("nowy_opis"));
				list.add(offer);
				
			}
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
		return list;
	}
}
