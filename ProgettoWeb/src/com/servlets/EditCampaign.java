package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.beans.*;


/**
 * Servlet implementation class EditCampaign
 */
@WebServlet("/EditCampaign")
public class EditCampaign extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] nWorkers= request.getParameterValues("Nusers");
		String[] mWorkers= request.getParameterValues("Musers");


		int new_nWorkers = 0;
		if(nWorkers != null){
			new_nWorkers = nWorkers.length;
		}


		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");


			boolean editK = false;
			boolean editPL = false;
			boolean editWorkers=false;
			boolean conditionK = false;
			boolean conditionPixelLine = false;


			if(request.getParameter("Kvalutations")!= ""){
				editK = true;
				if(Integer.parseInt(request.getParameter("Kvalutations")) <= Integer.parseInt(request.getParameter("nWorkersOld")) + new_nWorkers){				
					conditionK = true;	
				}

			}

			if(request.getParameter("pixelline") != ""){
				editPL = true;
				if(Integer.parseInt(request.getParameter("pixelline")) <=10 && Integer.parseInt(request.getParameter("pixelline")) >= 1){
					conditionPixelLine=true;

				}
			}
			if(editK || editPL || nWorkers != null || mWorkers != null){
				if(editK){
					if(!editPL){

						if(conditionK){
							String update = "UPDATE campaigns SET Kvalutations = ? WHERE name=?";
							PreparedStatement ups = connection.prepareStatement(update);
							ups.setInt(1, Integer.parseInt(request.getParameter("Kvalutations")));
							ups.setString(2, request.getParameter("campaignname"));
							ups.executeUpdate();

							response.sendRedirect("Home.jsp");
						}else{
							response.setContentType("text/html");  
							PrintWriter out = response.getWriter();   
							RequestDispatcher rd=request.getRequestDispatcher("/EditCampaign.jsp");  
							rd.include(request, response); 	
							out.print("<script>alert('Error1: K Value not Valid')</script>");  
						}
					}
				}

				if(editPL){
					if(!editK){
						if(conditionPixelLine){
							String update = "UPDATE campaigns SET pixelline = ? WHERE name=?";
							PreparedStatement ups = connection.prepareStatement(update);
							ups.setInt(1, Integer.parseInt(request.getParameter("pixelline")));
							ups.setString(2, request.getParameter("campaignname"));
							ups.executeUpdate();

							response.sendRedirect("Home.jsp");
						}else{
							response.setContentType("text/html");  
							PrintWriter out = response.getWriter();   
							RequestDispatcher rd=request.getRequestDispatcher("/EditCampaign.jsp");  
							rd.include(request, response); 	
							out.print("<script>alert('Error 2:Pixelline value valid')</script>");  

						}
					}
				}
				if(editK && editPL){

					if(conditionK && conditionPixelLine){
						String update = "UPDATE campaigns SET Kvalutations = ? WHERE name=?";
						PreparedStatement ups = connection.prepareStatement(update);
						ups.setInt(1, Integer.parseInt(request.getParameter("Kvalutations")));
						ups.setString(2, request.getParameter("campaignname"));
						ups.executeUpdate();


						String updatePL = "UPDATE campaigns SET pixelline = ? WHERE name=?";
						PreparedStatement upsPL = connection.prepareStatement(updatePL);
						upsPL.setInt(1, Integer.parseInt(request.getParameter("pixelline")));
						upsPL.setString(2, request.getParameter("campaignname"));
						upsPL.executeUpdate();

						response.sendRedirect("Home.jsp");

					}else if(conditionK || conditionPixelLine){

						if(conditionK){
							String update = "UPDATE campaigns SET Kvalutations = ? WHERE name=?";
							PreparedStatement ups = connection.prepareStatement(update);
							ups.setInt(1, Integer.parseInt(request.getParameter("Kvalutations")));
							ups.setString(2, request.getParameter("campaignname"));
							ups.executeUpdate();
							response.sendRedirect("Home.jsp");
						}else{
							response.setContentType("text/html");  
							PrintWriter out = response.getWriter();    
							RequestDispatcher rd=request.getRequestDispatcher("/EditCampaign.jsp");  
							rd.include(request, response); 
							out.print("<script>alert('Error1: K Value not Valid')</script>"); 

						}

						if(conditionPixelLine){
							String update = "UPDATE campaigns SET pixelline = ? WHERE name=?";
							PreparedStatement ups = connection.prepareStatement(update);
							ups.setInt(1, Integer.parseInt(request.getParameter("pixelline")));
							ups.setString(2, request.getParameter("campaignname"));
							ups.executeUpdate();
							response.sendRedirect("Home.jsp");
						}else{
							response.setContentType("text/html");  
							PrintWriter out = response.getWriter();   
							RequestDispatcher rd=request.getRequestDispatcher("/EditCampaign.jsp");  
							rd.include(request, response); 	
							out.print("<script>alert('Error 2:Pixelline value not valid')</script>");  

						}
					}else{

						response.setContentType("text/html");  
						PrintWriter out = response.getWriter();   
						RequestDispatcher rd=request.getRequestDispatcher("/EditCampaign.jsp");  
						rd.include(request, response); 	
						out.print("<script>alert('Error 3: K and PixelLine value not valid')</script>");
					}
				}



				if(new_nWorkers != 0){
					  editWorkers =true;
					Campaign c = new Campaign();
					c.setNomeCampagna(request.getParameter("campaignname"));

					for(String w: nWorkers){

						String queryAdd = "INSERT INTO workers_campaigns (worker, campaign, task) values (?,?,?)";
						PreparedStatement ps3 = connection.prepareStatement(queryAdd);
						ps3.setString(1, w);
						ps3.setString(2, request.getParameter("campaignname"));
						ps3.setString(3, "n");
						ps3.execute();
						ps3.close();

						List<Image> imagesN = new ArrayList<Image>();
						imagesN = c.loadCampaignImagesManager();

						for(Image i : imagesN){
							String query2 ="INSERT INTO workers_images (name_image, worker, selected, campaign,annotated) values (?,?,?,?,?)";
							PreparedStatement ps2 = connection.prepareStatement(query2);
							ps2.setString(1, i.getName());
							ps2.setString(2, w);
							ps2.setInt(3, 0);
							ps2.setString(4, c.getNomeCampagna());			
							ps2.setInt(5, 0);
							ps2.execute();
							ps2.close();


							int k = 0;
							String queryControl ="SELECT * FROM workers_images WHERE name_image=? AND worker=? AND campaign=?";
							PreparedStatement ps4 = connection.prepareStatement(queryControl,ResultSet.TYPE_FORWARD_ONLY,
									ResultSet.CONCUR_UPDATABLE);
							ps4.setString(1, i.getName());
							ps4.setString(2, w);
							ps4.setString(3, c.getNomeCampagna());			
							ResultSet rs = ps4.executeQuery();
							while(rs.next()){
								if(k==1){
									rs.deleteRow();
								}
								k++;
							}
							ps4.close();					
						}

					}
					if((editPL && conditionPixelLine) || (editK && conditionK)){
						System.out.println("non devo fare response");			//ho già fatto la response
								}else{
													response.sendRedirect("Home.jsp");
								}	
				}

				if(mWorkers != null){
                  
					Campaign c = new Campaign();
					c.setNomeCampagna(request.getParameter("campaignname"));

					for(String w: mWorkers){
						String queryAdd = "insert into workers_campaigns (worker, campaign, task) values (?,?,?)";
						PreparedStatement ps = connection.prepareStatement(queryAdd);
						ps.setString(1, w);
						ps.setString(2, request.getParameter("campaignname"));
						ps.setString(3, "m");
						ps.execute();
						ps.close();					


						List<Image> imagesM = new ArrayList<Image>();
						imagesM = c.loadCampaignImagesManager();
						for(Image i: imagesM){
							String query3 ="INSERT INTO workers_images (name_image, worker, selected, campaign,annotated) values (?,?,?,?,?)";
							PreparedStatement ps3 = connection.prepareStatement(query3);
							ps3.setString(1, i.getName());
							ps3.setString(2, w);
							ps3.setInt(3, 0);
							ps3.setString(4, c.getNomeCampagna());			
							ps3.setInt(5, 0);
							ps3.execute();
							ps3.close();


							int k = 0;
							String queryControl ="SELECT * FROM workers_images WHERE name_image=? AND worker=? AND campaign=?";
							PreparedStatement ps4 = connection.prepareStatement(queryControl,ResultSet.TYPE_FORWARD_ONLY,
									ResultSet.CONCUR_UPDATABLE);
							ps4.setString(1, i.getName());
							ps4.setString(2, w);
							ps4.setString(3, c.getNomeCampagna());			
							ResultSet rs = ps4.executeQuery();
							while(rs.next()){
								if(k==1){
									rs.deleteRow();
								}
								k++;
							}
							ps4.close();
						}
					}
					if((editPL && conditionPixelLine) || (editK && conditionK) || editWorkers){
			System.out.println("Ho già fatto response");			//ho già fatto la response
					}else{
										response.sendRedirect("Home.jsp");
					}				}
			}else{
				response.sendRedirect("Home.jsp");		
			}


			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

}
