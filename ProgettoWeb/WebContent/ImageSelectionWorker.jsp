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


<script>
	function selection(s, name, imagesToSelect,name_campaign) {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				
				if(s=="true"){
					document.getElementById("buttons"+name).innerHTML="ACCEPTED";
					document.getElementById(name).style.border = "2px solid green";
				}else{
					document.getElementById("buttons"+name).innerHTML="REFUSED";
					document.getElementById(name).style.border = "2px solid red";
				}
			}
		};
		xhttp.open("GET", "Valutation?boolean=" + s + "&name_image=" + name + "&name_campaign=" + name_campaign, true);
		xhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xhttp.send();
	};
</script>

<title>Selection Image</title>
</head>
<body>
	<%
		if (session.getAttribute("user") == null) {

			response.sendRedirect("Login.jsp");
		} else {
			Campaign campaign = new Campaign();
			campaign.setNomeCampagna(request.getParameter("campaignname"));

			List<Image> imagesToSelect = new ArrayList<Image>();
			imagesToSelect = campaign.loadCampaignImagesWorker((User) session.getAttribute("user"));
	%>
	
	<form action='Home.jsp'>
		<input class='button' type='submit' value='Turn on my Campaigns'>
	</form>
	
	<h2>Selection Task From: <%=campaign.getNomeCampagna()%></h2>
	
	<div class='container1'>
		<%		
		
					for (Image i : imagesToSelect) {
						%>	<div class='container'><%
						if(i.isAccepted((User) session.getAttribute("user"))==0){
		%>
		
		
			<img  id= "<%=i.getName()%>" class="sel" src='/project/immaginiCampagne/<%=i.getName()%>' width='400' height='200'><br>
			
			<div id="buttons<%=i.getName()%>">
			
			    <input type='button' value='Accetta'
				onclick='selection("true", "<%=i.getName()%>", <%=imagesToSelect.size()%>,"<%=campaign.getNomeCampagna()%>")'> 
				
				<input type='button' value='Rifiuta' onclick='selection("false", "<%=i.getName()%>", <%=imagesToSelect.size()%>,"<%=campaign.getNomeCampagna()%>")'>
			
				</div>
				
				<%} else if(i.isAccepted((User) session.getAttribute("user"))==1){%>
		<img class="accepted" src='/project/immaginiCampagne/<%=i.getName()%>' width='400' height='200'><br>
		<div id="buttons<%=i.getName()%>">
			ACCEPTED			   
				</div>
		
				<%} else if(i.isAccepted((User) session.getAttribute("user"))==2){%>
		<img class="refused" src='/project/immaginiCampagne/<%=i.getName()%>' width='400' height='200'><br>
		<div id="buttons<%=i.getName()%>">
			REFUSED   
				</div>
		
				<%}%>		
		</div>
		<br>
		<%
			}
		%>
	</div>
	<%
		}
	%>
	
	

	
</body>
</html>