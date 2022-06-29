package com.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class User {

	private String username;
	private String password;
	private int role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public List<Campaign> loadCampaignsManager(){

		List<Campaign> myCampaigns = new ArrayList<Campaign>();

		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			String query = "SELECT * FROM campaigns where owner=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, this.username );
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				Campaign c = new Campaign();
				c.setNomeCampagna(rs.getString("name"));
				c.setOwner(rs.getString("owner"));
				c.setkValutations(Integer.parseInt(rs.getString("kvalutations")));
				c.setPixelline(Integer.parseInt(rs.getString("pixelline")));
				c.setEditable(rs.getInt("editable"));
				myCampaigns.add(c);
			}

			ps.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}

		return myCampaigns;

	}


	public List<User> loadWorkers(){

		List<User> workers = new ArrayList<User>();

		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			String query = "select * from accounts where role = 2";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				User u = new User();
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setRole(2);
				workers.add(u);
			}
			ps.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}
		return workers;
	}

	public List<String> loadCampaignsWorkerSelection(){

		List<String> myCampaigns = new ArrayList<String>();

		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			String query = ("select * from workers_campaigns where worker = ? and task = 'n'");
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, this.username);
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{				
				myCampaigns.add(rs.getString("campaign"));
			}
			ps.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}

		return myCampaigns;

	}

	public List<String> loadCampaignsWorkerAnnotation(){

		List<String> myCampaigns = new ArrayList<String>();

		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			String query = ("select * from workers_campaigns where worker = ? and task = 'm'");
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,this.username);
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{				
				myCampaigns.add(rs.getString("campaign"));
			}

			ps.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}

		return myCampaigns;

	}

}
