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
import javax.websocket.Session;

import com.beans.Campaign;

@WebServlet("/StartCampaign")
public class StartCampaign extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		
		if(Integer.parseInt(request.getParameter("editable")) == 1){
			HttpSession session = request.getSession(false);
			session.removeAttribute("campaing");
			session.removeAttribute("nWorkers");
			session.removeAttribute("mWorkers");
			response.sendRedirect("Home.jsp");
		}else{

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			String queryCheck = "SELECT * FROM images WHERE campaignRelated = ?";
			PreparedStatement psCheck = connection.prepareStatement(queryCheck);
			psCheck.setString(1, request.getParameter("nameCampaign"));
			ResultSet rsCheck = psCheck.executeQuery();
		
			if(rsCheck.next()){

				String queryEdit = "UPDATE campaigns SET editable = 0 WHERE name=?";
				PreparedStatement psEdit = connection.prepareStatement(queryEdit);
				psEdit.setString(1, request.getParameter("nameCampaign"));
				psEdit.executeUpdate();

				HttpSession session = request.getSession(false);
				session.removeAttribute("campaing");
				session.removeAttribute("nWorkers");
				session.removeAttribute("mWorkers");

				response.sendRedirect("Home.jsp");
			}else{
				
				response.setContentType("text/html");  
				PrintWriter out = response.getWriter();  
				if(request.getParameter("page").equals("addPhoto")){
				RequestDispatcher rd = request.getRequestDispatcher("/AddPhoto.jsp"); 
				rd.include(request, response); 
				out.print("<script>alert('You have to insert at least one image')</script>");  
				}else if(request.getParameter("page").equals("home")){
					RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
					rd.include(request, response); 
					out.print("<script>alert('You have to insert at least one image')</script>");  
					}
				
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	}



}
