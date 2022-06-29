package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/GetImageToAnnote")
public class GetImageToAnnote extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

		PrintWriter out = response.getWriter();

		JSONObject json = new JSONObject();

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

			PreparedStatement psImage = connection.prepareStatement(query);
			psImage.setString(1, request.getParameter("campaign"));
			psImage.setString(2, request.getParameter("worker"));
			ResultSet rsImage = psImage.executeQuery();

			if(rsImage.next())
			{
				json.put("name_image", rsImage.getString("name_image")); 	
				json.put("vuoto", 1);
			}else{
				json.put("vuoto", 0); 	
			}

			out.print(json.toString());
			psImage.close();

			connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}

	}



}
