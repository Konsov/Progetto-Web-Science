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

import com.beans.Campaign;

/**
 * Servlet implementation class CreateCampaign
 */
@WebServlet("/CreateCampaign")
public class CreateCampaign extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String[] nWorkers= request.getParameterValues("Nusers");
		String[] mWorkers= request.getParameterValues("Musers");
	
		if (request.getParameterValues("Nusers") == null || request.getParameterValues("Musers")==null)
		{
			response.setContentType("text/html");  
			PrintWriter out = response.getWriter();  
			RequestDispatcher rd=request.getRequestDispatcher("/NewCampaign.jsp");  
			rd.include(request, response); 	
			out.print("<script>alert('You have to compile all fields!')</script>");  
			
		}else{
			if( (Integer.parseInt(request.getParameter("Kvalutations")) <= nWorkers.length &&
					Integer.parseInt(request.getParameter("Kvalutations")) !=0)	&& 
					(Integer.parseInt(request.getParameter("pixelline")) <=10 &&
					Integer.parseInt(request.getParameter("pixelline")) != 0 )){

				try{

					Class.forName("com.mysql.jdbc.Driver");
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

					String query ="SELECT * FROM campaigns WHERE name=?";
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, request.getParameter("campaignname"));
					ResultSet rs = ps.executeQuery();

					if(!rs.next())
					{			
						String query2 ="INSERT INTO campaigns(name,kvalutations,pixelline,owner,editable) VALUES(?,?,?,?,?)";

						PreparedStatement ps2 = connection.prepareStatement(query2);   
						ps2.setString(1, request.getParameter("campaignname"));
						ps2.setInt(2, Integer.parseInt(request.getParameter("Kvalutations")));
						ps2.setInt(3, Integer.parseInt(request.getParameter("pixelline")));
						ps2.setString(4, request.getParameter("owner"));
						ps2.setInt(5,1);
						ps2.execute();  
						ps2.close();

						Campaign campaign = new Campaign();
						campaign.setNomeCampagna(request.getParameter("campaignname"));			
						campaign.setkValutations(Integer.parseInt(request.getParameter("Kvalutations")));
						campaign.setPixelline(Integer.parseInt(request.getParameter("pixelline")));
						campaign.setOwner(request.getParameter("owner"));
						campaign.setEditable(1);

						HttpSession session = request.getSession(false);
						session.setAttribute("campaign", campaign);


						for(String s: nWorkers){
							String queryAdd = "insert into workers_campaigns (worker, campaign, task) values (?,?,?)";
							PreparedStatement ps3 = connection.prepareStatement(queryAdd);
							ps3.setString(1, s);
							ps3.setString(2, request.getParameter("campaignname"));
							ps3.setString(3, "n");
							ps3.execute();
							ps3.close();
						}

						for(String s: mWorkers){
							String queryAdd = "insert into workers_campaigns (worker, campaign, task) values (?,?,?)";
							PreparedStatement ps3 = connection.prepareStatement(queryAdd);
							ps3.setString(1, s);
							ps3.setString(2, request.getParameter("campaignname"));
							ps3.setString(3, "m");
							ps3.execute();
							ps3.close();
						}

						session.setAttribute("nWorkers", nWorkers);	
						session.setAttribute("mWorkers", mWorkers);	
						
						
						response.sendRedirect("AddPhoto.jsp"); 
					}
					else{
						response.setContentType("text/html");  
						PrintWriter out = response.getWriter();   
						RequestDispatcher rd=request.getRequestDispatcher("/NewCampaign.jsp");  
						rd.include(request, response); 	
						out.print("<script>alert('Sorry campaign Name already existed!')</script>");  
					}

				}catch(Exception e){

					e.printStackTrace();
				}

			}else{
				response.setContentType("text/html");  
				PrintWriter out = response.getWriter();  
				RequestDispatcher rd=request.getRequestDispatcher("/NewCampaign.jsp");  
				rd.include(request, response); 	
				
				if(Integer.parseInt(request.getParameter("pixelline")) >10 ||
					Integer.parseInt(request.getParameter("pixelline")) == 0 ){
					out.print("<script>alert('Pixelline value must be between 1 and 10')</script>");  
				}else{
				
				out.print("<script>alert('K value cant be 0 and must be smaller than number of Selection Workers! A Selection Worker can vote a photo only once')</script>");
				}
				
			}
		}

	}

}
