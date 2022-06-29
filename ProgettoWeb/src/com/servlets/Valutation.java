package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beans.User;


@WebServlet("/Valutation")
public class Valutation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		try{

			//loading drivers for mysql
			Class.forName("com.mysql.jdbc.Driver");

			//creating connection with the database 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

			//Dico che il lavoratore ha selezionato l'immagine
			

			if(request.getParameter("boolean").equals("true")){
				//valutazioni positive ++
				
				String query = "UPDATE workers_images SET selected = 1 WHERE name_image=? AND worker=?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, request.getParameter("name_image"));
				ps.setString(2, ((User) session.getAttribute("user")).getUsername());
				ps.executeUpdate();

				
				
				String update = "UPDATE images SET valutazioni_pos = valutazioni_pos+1 where name_image=?";
				PreparedStatement ups = connection.prepareStatement(update);
				ups.setString(1, request.getParameter("name_image"));
				ups.executeUpdate();
				
				ups.close();
				ps.close();
			}else{

				String query = "UPDATE workers_images SET selected = 2 WHERE name_image=? AND worker=?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, request.getParameter("name_image"));
				ps.setString(2, ((User) session.getAttribute("user")).getUsername());
				ps.executeUpdate();

				
				//valutazioni negative ++
				String update = "UPDATE images SET valutazioni_neg = valutazioni_neg+1 where name_image=?";
				PreparedStatement ups = connection.prepareStatement(update);
				ups.setString(1, request.getParameter("name_image"));
				ups.executeUpdate();
				
				ups.close();
				ps.close();
			}

			
			//Verifico che l'immagine non sia già stata modificata
			String query2 = "SELECT * FROM images WHERE campaignRelated=? AND name_image=?";
			PreparedStatement psImage = connection.prepareStatement(query2);
			psImage.setString(1, request.getParameter("name_campaign"));
			psImage.setString(2, request.getParameter("name_image"));
			ResultSet rsImage = psImage.executeQuery();
			
			int withOutStatus = 0;
			
			if(rsImage.next()){
				withOutStatus = Integer.parseInt(rsImage.getString("status"));
			}
			
			if(withOutStatus == 0){
				
			
			//Prendo il valore K voti necessari per Accettare o Rifiutare un Immagine
			String queryCampaign = "SELECT * FROM campaigns WHERE name=?";
			PreparedStatement ps2 = connection.prepareStatement(queryCampaign);
			ps2.setString(1, request.getParameter("name_campaign") );
			ResultSet rs2 = ps2.executeQuery();

			int k = 0;

			if(rs2.next()){
				k = rs2.getInt("kvalutations");
			}

			//Prendo il Numero dei lavoratri che devono selezionare l'immagine
			String querynWorkers = "SELECT * FROM workers_campaigns WHERE campaign= ? AND task = 'n'";
			PreparedStatement psnWorkers = connection.prepareStatement(querynWorkers);
			psnWorkers.setString(1, request.getParameter("name_campaign"));
			ResultSet rsnWorkers = psnWorkers.executeQuery();
			int  nWorkers = 0;

			while(rsnWorkers.next())
			{
				nWorkers += 1;
			}

			//selezione l'immagine
			PreparedStatement psImage2 = connection.prepareStatement(query2);
			psImage2.setString(1, request.getParameter("name_campaign"));
			psImage2.setString(2, request.getParameter("name_image"));
			ResultSet rsImage2 = psImage2.executeQuery();
		
			if(rsImage2.next()){
				
				if(rsImage2.getInt("valutazioni_pos") >= k)
				{
					String update = "UPDATE images SET status = 1 WHERE name_image=?";
					PreparedStatement ups = connection.prepareStatement(update);
					ups.setString(1, rsImage2.getString("name_image"));
					ups.executeUpdate();
					ups.close();
				}

				if(nWorkers - rsImage2.getInt("valutazioni_neg") < k){
					String update = "UPDATE images SET status = 2 WHERE name_image=?";
					PreparedStatement ups = connection.prepareStatement(update);
					ups.setString(1, rsImage2.getString("name_image"));
					ups.executeUpdate();
					ups.close();
				}

			}
			
			ps2.close();
			psnWorkers.close();
			}
			connection.close();
			
		}catch(Exception e){

			e.printStackTrace();
		}
	}


}
