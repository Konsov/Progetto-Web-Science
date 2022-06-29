package com.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Image {

	String campaign;
	String name;
	int valutazioni_pos;
	int valutazioni_neg;
	int status;
	
	public String getCampaign() {
		return campaign;
	}
	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValutazioni_pos() {
		return valutazioni_pos;
	}
	public void setValutazioni_pos(int valutazioni_pos) {
		this.valutazioni_pos = valutazioni_pos;
	}
	public int getValutazioni_neg() {
		return valutazioni_neg;
	}
	public void setValutazioni_neg(int valutazioni_neg) {
		this.valutazioni_neg = valutazioni_neg;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

	public List<String> loadPixelLines(){
		
		List<String> lines = new ArrayList<String>();

		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");
			String query = "SELECT * FROM pixellines_annotations WHERE imgRelated = ?";

			PreparedStatement psLine = connection.prepareStatement(query);
			psLine.setString(1, this.getName());
			ResultSet rsLine = psLine.executeQuery();

			while(rsLine.next())
			{				
				lines.add(rsLine.getString("name_pixelline"));	
			}
			psLine.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}
		
		return lines;
	}
	
public int isAccepted(User user){
		
		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");
			String query = "SELECT * FROM workers_images WHERE worker = ? AND name_image=?";

			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, user.getUsername());
			ps.setString(2, this.getName());
			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{				
				return rs.getInt("selected");
			}
			ps.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}
		
		return 0;
	
	}
	
	
}
