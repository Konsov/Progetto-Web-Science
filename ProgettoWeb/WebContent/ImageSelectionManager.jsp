<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">



<!-- Style -->
<link rel="stylesheet" href="css/styleSelection.css" type="text/css">


<%@ page import="com.beans.User"%>
<%@ page import="com.beans.Campaign"%>
<%@ page import="com.beans.Image"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<title>Selection Image</title>
</head>
<body>
	<%
		if (session.getAttribute("user") == null) {

			response.sendRedirect("Login.jsp");
		} else {
			Campaign campaign = new Campaign();
			campaign.setNomeCampagna(request.getParameter("campaignname"));

			List<Image> images = new ArrayList<Image>();
			images = campaign.loadCampaignImagesManager();
			
			int imageAccepted = 0;
			int imageRefused = 0;
			
			
	%>
	
	<form action='Home.jsp'>
		<input class='button' type='submit' value='Turn on my Campaigns'>
	</form>
	
	<h2>Selection Task From: <%=campaign.getNomeCampagna()%></h2>
	
	<div class='container1'>
		<%
			if (images.size() == 0) {
		%><p>There Aren't images to select !</p>
		<%
			} else {
					for (Image i : images) {
		%>
		<div id="<%=i.getName()%>" class='container'>
			<%
				if (i.getStatus() == 1 || i.getStatus() == 3) {
			%>
			
			<img class="accepted"src='/project/immaginiCampagne/<%=i.getName()%>' width='400' height='200'><br>
			Accepted Image
			<%
				}
				else if (i.getStatus() == 2) {
			%>
			
			<img class="refused"src='/project/immaginiCampagne/<%=i.getName()%>' width='400' height='200'><br>
			Refused Image
			<%
				} else {
			%>
			
			<img class="sel" src='/project/immaginiCampagne/<%=i.getName()%>' width='400' height='200'><br>
			Selection in Progress..
			<%
				}
			%>
<br>Voti Positivi: <%=i.getValutazioni_pos()%> Negativi: <%=i.getValutazioni_neg()%>
		</div>
		<br>
		<%
			}
	
		}
	
		%>
		</div>
		<%
	
		}
	%>

</body>
</html>