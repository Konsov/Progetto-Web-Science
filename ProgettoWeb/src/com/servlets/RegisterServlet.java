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
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{

			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			String query = "SELECT * FROM accounts where username=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, request.getParameter("username"));
			ResultSet rs = ps.executeQuery();
			//Se non esiste già un Account con lo stesso nome Procedo
			if(!rs.next())
			{			

				HttpSession session = request.getSession();
				User user = new User();
				user.setUsername(request.getParameter("username"));
				user.setPassword(request.getParameter("password"));

				//Inserisco nuovo account nel database
				String queryAdd = "INSERT INTO accounts(username,password,role) VALUES(?,?,?)";
				PreparedStatement ps2 = connection.prepareStatement(queryAdd);   
				ps2.setString(1, request.getParameter("username"));
				ps2.setString(2, request.getParameter("password"));

				if(request.getParameter("role").equals("manager"))
				{			
					ps2.setInt(3, 1);  //Registro role 1 se è un manager
					user.setRole(1);
				}
				else{
					ps2.setInt(3, 2); //Registro role 2 se è un worker
					user.setRole(2);
				}

				ps2.execute();  
				ps2.close();


				session.setAttribute("user", user);			
				response.sendRedirect("Home.jsp");
			}else{
				//Altrimenti messaggio di errore
			response.setContentType("text/html");  
			PrintWriter out = response.getWriter();  
			RequestDispatcher rd=request.getRequestDispatcher("/Register.jsp");  
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
