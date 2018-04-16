package eu.michalbuda.offerGenerator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class FireBirdConnector {

	private static FireBirdConnector instance = new FireBirdConnector();
	private static Preferences prefs;
	private static final String prefsNodeName = "/eu/michalbuda/offerGenerator/db";
	private static String URL;
	private static String USER;
	private static String PASS;
	
	private static Connection conn;
	
	private FireBirdConnector(){
		
	}
	
	public static FireBirdConnector getInstance() throws Exception{
		if(conn == null) {
			connect();
		}
		return instance;
	}
	
	public Connection getConnection() throws Exception{
		if(conn == null) {
			connect();
		}
		return conn;
	}
	
	private static void connect() throws Exception{
		if(conn != null)
			return;
		
		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver");
		} catch (Exception e) {
			throw new Exception("Driver not found!");
		}
		
		// define node for storing preferences
		prefs = Preferences.userRoot().node(prefsNodeName);
		
		URL = prefs.get("URL", "null");
		USER = prefs.get("USER", "sysdba");
		PASS = prefs.get("PASS", "masterkey");
		
		if(URL.equals("null")) {
			//prompt user for settings
			URL = JOptionPane.showInputDialog("Please enter path to FDB file");
			prefs.put("URL", URL);
			
			USER = JOptionPane.showInputDialog("Please enter user login");
			prefs.put("USER", USER);
			
			//TODO
			PASS = JOptionPane.showInputDialog("Please enter password");
			prefs.put("PASS", PASS);
		}
		
		conn = DriverManager.getConnection("jdbc:firebirdsql://" + URL + "?characterEncoding=win1250", USER, PASS);
		
	}
	
	public void disconnect(){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Can't close connection to FDB");
			}
		}
		
		conn = null;
	}
	

	
	
}
