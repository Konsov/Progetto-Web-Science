package com.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Campaign {


	private String nomeCampagna;
	private String owner;
	private int kValutations;
	private int pixelline;
	private int editable;


	public String getNomeCampagna() {
		return nomeCampagna;
	}
	public void setNomeCampagna(String nomeCampagna) {
		this.nomeCampagna = nomeCampagna;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getkValutations() {
		return kValutations;
	}
	public void setkValutations(int kValutations) {
		this.kValutations = kValutations;
	}

	public int getPixelline() {
		return pixelline;
	}
	public void setPixelline(int pixelline) {
		this.pixelline = pixelline;
	}
	public int getEditable() {
		return editable;
	}
	public void setEditable(int editable) {
		this.editable = editable;
	}

	public int getPixellineByName(){
		int pixelLine = 0;
		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");
			String query = "SELECT * FROM campaigns WHERE name = ? ";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, this.getNomeCampagna());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				pixelLine = rs.getInt("pixelline");
			}
			ps.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}


		return pixelLine;

	}

	public List<Image> loadImagesAnnotated(){

		List<Image> images = new ArrayList<Image>();

		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");
			String query = "SELECT * FROM images WHERE status = 3 and CampaignRelated = ?";

			PreparedStatement psImage = connection.prepareStatement(query);
			psImage.setString(1, this.getNomeCampagna());
			ResultSet rsImage = psImage.executeQuery();

			while(rsImage.next())
			{				
				Image i = new Image();
				i.setCampaign(this.getNomeCampagna());
				i.setName(rsImage.getString("name_image"));
				images.add(i);	
			}

			psImage.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}

		return images;
	}



	public List<Image> loadCampaignImagesWorker(User user){

		List<Image> images = new ArrayList<Image>();

		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			String query = "SELECT * FROM workers_images where campaign=? AND worker=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, this.getNomeCampagna());
			ps.setString(2, user.getUsername());
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				Image i = new Image();
				i.setCampaign(rs.getString("campaign"));
				i.setName(rs.getString("name_image"));;
				images.add(i);
			}

			ps.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}

		return images;

	}

	public List<Image> loadCampaignImagesWorker2(User user){

		List<Image> images = new ArrayList<Image>();

		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");


			String query = "SELECT workers_images.campaign, workers_images.worker, workers_images.name_image, workers_images.annotated, images.status "
					+ "FROM workers_images "
					+ "INNER JOIN images "
					+ "ON workers_images.name_image = images.name_image "
					+ "WHERE (status=1 OR status=3 )AND annotated = 0 "
					+ "AND campaign=? AND worker=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, this.getNomeCampagna());
			ps.setString(2, user.getUsername());

			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				Image i = new Image();
				i.setCampaign(rs.getString("campaign"));
				i.setName(rs.getString("name_image"));
				images.add(i);
			}

			ps.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}

		return images;

	}

	public List<Image> loadCampaignImagesManager(){

		List<Image> images = new ArrayList<Image>();

		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			String query = "SELECT * FROM images WHERE campaignRelated=?";
			PreparedStatement psImage = connection.prepareStatement(query);
			psImage.setString(1, this.getNomeCampagna());
			ResultSet rsImage = psImage.executeQuery();

			while(rsImage.next())
			{
				Image i = new Image();
				i.setCampaign(rsImage.getString("campaignRelated"));
				i.setName(rsImage.getString("name_image"));
				i.setValutazioni_pos(rsImage.getInt("valutazioni_pos"));
				i.setValutazioni_neg(rsImage.getInt("valutazioni_neg"));
				i.setStatus(rsImage.getInt("status"));
				images.add(i);
			}

			psImage.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}

		return images;

	}

	public List<String> loadNworkersFromCampaign(){

		List<String> nWorkers = new ArrayList<String>();

		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			String query = ("SELECT * FROM workers_campaigns where campaign = ? and task = 'n'");
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, this.nomeCampagna);
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{				
				nWorkers.add(rs.getString("Worker"));
			}

			ps.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}

		return nWorkers;

	}

	public List<String> loadMworkersFromCampaign(){

		List<String> mWorkers = new ArrayList<String>();

		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			String query = ("SELECT * FROM workers_campaigns where campaign = ? and task = 'm'");
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, this.nomeCampagna);
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{				
				mWorkers.add(rs.getString("Worker"));
			}

			ps.close();
			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}

		return mWorkers;

	}

	public int isEditable(){

		int editable = 0;

		try{

			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			String query = ("SELECT * FROM campaigns WHERE name = ?");
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, this.nomeCampagna);
			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{				
				editable = rs.getInt("editable");
			}

			ps.close();
			connection.close();
		
		}catch(Exception e){

			e.printStackTrace();
		}
		return editable;
	}
}