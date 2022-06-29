<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<%@ page import="com.beans.User"%>
<%@ page import="com.beans.Image"%>
<%@ page import="com.beans.Campaign"%>

<!-- Style -->
<link rel="stylesheet" href="css/styleNewCampaign.css" type="text/css">

<title>Add New Photo</title>
</head>
<body>
	<%
		//controllo che esista una sessione e che ci sia associato un utente
		if (session.getAttribute("user") == null) {
			response.sendRedirect("Login.jsp");

		} else {
			Campaign c = new Campaign();
			c = (Campaign) session.getAttribute("campaign");
	%>

	<h3>Add New Photo</h3>
	<div class="containerAddPhoto">

		<form action="AddPhotoServlet" method="post" enctype="multipart/form-data">
			<input class="inputPhoto" type="file" name="photo"><br>
			<input type="submit" class="button" value="Add Image"> <br> 
			<a class="show" id="imageStored" >Image stored. You can add more</a>
			<a class="show" id="error">File format not valid. Insert Jpeg or Png</a>
		</form>
</div>
<div class="divbuttons">
		
		<form id="startCampaign" class="formBotton" action='StartCampaign' method="get">
			<input type="hidden" name="nameCampaign" value="<%=c.getNomeCampagna()%>">
			<input type="hidden" name="editable" value="0">
			<input type="hidden" name="page" value="addPhoto">			
			 <input class='button' type='submit' value='Start Campaign'>
		</form>
		
		<form class= "formBotton" action="StartCampaign" method="get">
		<input type="hidden" name="editable" value="1">
	  <input class='button' type='submit' value='Complete After'>
</form>

</div>
<%} %>
</body>
</html>