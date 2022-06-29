package com.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.beans.Campaign;

/**
 * Servlet implementation class AddPhotoServlet
 */
@WebServlet("/AddPhotoServlet")
@MultipartConfig
public class AddPhotoServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory); // Ritorna una lista

		try {
			List formItems = upload.parseRequest(request);
			Iterator iter = formItems.iterator();

			// iterates over form's fields
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				// processes only fields that are not form fields

				String contentType = item.getContentType();

				if ("image/png".equals(contentType) || "image/jpeg".equals(contentType)) {


					if (!item.isFormField()) {

						HttpSession session = request.getSession(false);
						Campaign c = new Campaign();
						c = (Campaign) session.getAttribute("campaign");
						
						Random random = new Random();
						int rn = random.nextInt();

						String fileName = c.getNomeCampagna() + "_" + c.getOwner() + "_" + rn + ".png";

						String filePath = "C:/Users/Luca/Desktop/ImmaginiProgetto/ImmaginiCampagne/" + fileName;

						File storeFile = new File(filePath);


						// saves the file on disk
						item.write(storeFile);


						Class.forName("com.mysql.jdbc.Driver");
						Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

						String query = "INSERT INTO images (campaignRelated, name_image, valutazioni_pos, valutazioni_neg, status) values (?,?,?,?,?)";
						PreparedStatement ps = connection.prepareStatement(query);
						ps.setString(1, c.getNomeCampagna());
						ps.setString(2, fileName);
						ps.setInt(3, 0);
						ps.setInt(4, 0);	
						ps.setInt(5, 0);	// 0 ne accettata ne rifiutata, 1 accettata, 2 rifiutata		
						ps.execute();
						ps.close();

						String[] nWorkers= (String[]) session.getAttribute("nWorkers");
						for(String w:nWorkers){
							String query2 ="INSERT INTO workers_images (name_image, worker, selected, campaign,annotated) values (?,?,?,?,?)";
							PreparedStatement ps2 = connection.prepareStatement(query2);
							ps2.setString(1, fileName);
							ps2.setString(2, w);
							ps2.setInt(3, 0);
							ps2.setString(4, c.getNomeCampagna());			
							ps2.setInt(5, 0);
							ps2.execute();
							ps2.close();
						}

						String[] mWorkers= (String[]) session.getAttribute("mWorkers");
						for(String w:mWorkers){
							String query3 ="INSERT INTO workers_images (name_image, worker, selected, campaign,annotated) values (?,?,?,?,?)";
							PreparedStatement ps3 = connection.prepareStatement(query3);
							ps3.setString(1, fileName);
							ps3.setString(2, w);
							ps3.setInt(3, 0);
							ps3.setString(4, c.getNomeCampagna());			
							ps3.setInt(5, 0);
							ps3.execute();
							ps3.close();
						}


						for(String w:mWorkers){
							int i = 0;
							String queryControl ="SELECT * FROM workers_images WHERE name_image=? AND worker=? AND campaign=?";
							PreparedStatement ps4 = connection.prepareStatement(queryControl,ResultSet.TYPE_FORWARD_ONLY,
									ResultSet.CONCUR_UPDATABLE);
							ps4.setString(1, fileName);
							ps4.setString(2, w);
							ps4.setString(3, c.getNomeCampagna());			
							ResultSet rs = ps4.executeQuery();
							while(rs.next()){
								if(i==1){
									rs.deleteRow();
								}
								i++;
							}

							ps4.close();
						}

						connection.close();	
						

						response.setContentType("text/html");  
						PrintWriter out = response.getWriter();  
						RequestDispatcher rd = request.getRequestDispatcher("/AddPhoto.jsp");  
						rd.include(request, response); 
						out.print("<script>document.getElementById('imageStored').style.visibility = 'visible';"
								+ "document.getElementById('error').style.visibility = 'hidden';"
								+ "</script>");  
						
					}
				}else{


					response.setContentType("text/html");  
					PrintWriter out = response.getWriter();  
				
					RequestDispatcher rd = request.getRequestDispatcher("/AddPhoto.jsp");  
					rd.include(request, response); 

					out.print("<script> document.getElementById('error').style.visibility = 'visible';"
							+ "document.getElementById('imageStored').style.visibility = 'hidden';"
							+ "</script>"); 
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
