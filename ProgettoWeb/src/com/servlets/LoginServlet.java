package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beans.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{

			//loading drivers for mysql
			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			String query = "SELECT * FROM accounts WHERE username=? and password=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,request.getParameter("username"));
			ps.setString(2, request.getParameter("password"));
			ResultSet rs = ps.executeQuery();

// Se esiste una corrispondenza, creo una sessione attribuendole le info dell'utente e lo reindirizzo alla home
			if(rs.next()){
				HttpSession session = request.getSession();
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRole(Integer.parseInt(rs.getString("role")));

				session.setAttribute("user", user);
				response.sendRedirect("Home.jsp");	

			}else{
//altrimenti messaggio di errore
				response.setContentType("text/html");  
				PrintWriter out = response.getWriter();  				
				RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");  
				rd.include(request, response); 
				out.print("<script> document.getElementById('error').style.visibility = 'visible';</script>");  
			}

			ps.close();

			connection.close();
			
		}catch(Exception e){

			e.printStackTrace();
		}

	}  
}


