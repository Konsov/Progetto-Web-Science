package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beans.User;

/**
 * Servlet implementation class AnnotationServlet
 */
@WebServlet("/AnnotationServlet")
public class AnnotationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession(false);
		try{

			//loading drivers for mysql
			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");
			String query = "update workers_images set annotated=1 where name_image=? and worker=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, request.getParameter("id"));
			ps.setString(2, ((User) session.getAttribute("user")).getUsername());
			ps.executeUpdate();
			ps.close();
			
			String updateStatus = "update images set status=3 where name_image=?";
			PreparedStatement psUpdate = connection.prepareStatement(updateStatus);
			psUpdate.setString(1, request.getParameter("id"));
			psUpdate.executeUpdate();
			psUpdate.close();
			

connection.close();
		}catch(Exception e){

			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
