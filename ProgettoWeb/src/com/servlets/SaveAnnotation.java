package com.servlets;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Base64;
import java.util.Random;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

/**
 * Servlet implementation class SaveAnnotation
 */
@WebServlet("/SaveAnnotation")
public class SaveAnnotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		PrintWriter out = response.getWriter();
	    response.setContentType("text/plain");

	    StringBuffer jb = new StringBuffer();
	    String line = null;
	    BufferedReader reader = request.getReader();
	    while ((line = reader.readLine()) != null)
	        jb.append(line);

	    String img64 = jb.toString();   
	    //check if the image is really a base64 png, maybe a bit hard-coded
	    if(img64 != null && img64.startsWith("data:image/png;base64,")){
	        //Remove Content-type declaration
	        img64 = img64.substring(img64.indexOf(',') + 1);            
	    }else{
	        response.setStatus(403);
	        out.print("Formato immagine non corretto!");
	        return;
	    }   
	    try{
	        InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(img64.getBytes()));  
	        BufferedImage bfi = ImageIO.read(stream);
	        Random random = new Random();
	        int rn = random.nextInt();
			String fileName = request.getParameter("campaign") + "_" + request.getParameter("worker") + "_" + rn + ".png";
	        String path = "C:/Users/Luca/Desktop/ImmaginiProgetto/AnnotazioniCanvas/"+fileName;
	        File outputfile = new File(path);
	        outputfile.createNewFile();
	        ImageIO.write(bfi , "png", outputfile);
	        bfi.flush();
	        
		    	Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_web","root","root");

				String query = "INSERT INTO pixellines_annotations (imgRelated, name_pixelline) values (?,?)";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, request.getParameter("img"));
				ps.setString(2, fileName);
				ps.execute();
				ps.close();
	        

connection.close();
	        
	        
	    }catch(Exception e){  
	        e.printStackTrace();
	        
	    }  
	
	}

}
