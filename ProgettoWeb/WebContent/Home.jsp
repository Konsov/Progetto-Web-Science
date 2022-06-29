<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">



<!-- Style -->
<link rel="stylesheet" href="css/styleHome.css" type="text/css">

<%@ page import="com.beans.User"%>
<%@ page import="com.beans.Image"%>
<%@ page import="com.beans.Campaign"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<title>HomePage</title>
</head>
<body>

	<%
		//controllo che esista una sessione e che ci sia associato un utente
		if (session.getAttribute("user") == null) {
			response.sendRedirect("Login.jsp");

		} else {
			User user = new User();
			user = (User) session.getAttribute("user");
	%>
	<!--bottone logout -->
	<form action='LogoutServlet'>
		<input class='button' type='submit' value='Logout'>
	</form>

	<%
		// Carico hompage diverse a seconda che sia un manager o un worker
		// Manager
			if (user.getRole() == 1) {
				List<Campaign> myCampaigns = new ArrayList<Campaign>();
				myCampaigns = user.loadCampaignsManager(); //Mi carica tutte le campagne del relativo manager
	%>

	<!-- Bottone pre creare nuova campagna -->
	<form action='NewCampaign.jsp'>
		<input class='button' type='submit' value='Create a new Campaign'>
	</form>
	<h1>
		Welcome,
		<%=user.getUsername()%></h1>
	<div class='containercampaigns'>

		<%
			//se il manager non ha campagne attive
					if (myCampaigns.size() == 0) {
		%><div class='container'>
		<p>You haven't any Campaigns yet, Enjoy !</p>
		</div>
		<%
			} else {
						//per ogni campagna del manager
						//Stampo le informazini relative alle sue immagini
						for (Campaign c : myCampaigns) {
		%>

		<div class='container'>
			<h3><%=c.getNomeCampagna()%></h3>
			<%
				if (c.getEditable() == 0) {
			%>
			<a
				href="/ProgettoWeb/ImageSelectionManager.jsp?campaignname=<%=c.getNomeCampagna()%>">Info
				Selection </a> <a
				href="/ProgettoWeb/AnnotationManager.jsp?campaignname=<%=c.getNomeCampagna()%>">Info
				Annotation</a> <br>
			<%
				List<Image> images = new ArrayList<Image>();
									int imageUndefined = 0; //status 0
									int imageAccepted = 0; //status 1
									int imageRefused = 0; //status 2
									int imageAnnoted = 0; //status 3

									images = c.loadCampaignImagesManager();
									for (Image i : images) {

										if (i.getStatus() == 0) { //quante immagini ancora in fase di selezione
											imageUndefined += 1;
										}

										if (i.getStatus() == 1) { //quante immagini Accettate
											imageAccepted += 1;
										}

										if (i.getStatus() == 2) { //quante immagini Rifiutate
											imageRefused += 1;
										}

										if (i.getStatus() == 3) { //quante immagini Annotate
											imageAnnoted += 1;
										}
									}
			%>
			<div>
				<br> Immagini Accettate:<%=imageAccepted + imageAnnoted%><br>
				Immagini Annotate:<%=imageAnnoted%><br> Immagini Rifiutate:<%=imageRefused%><br>
				Immagini in fase di Selezione:<%=imageUndefined%><br>
			</div>
			<%
				} else {
			%>

			<form id="startCampaign" class="formBotton" action='StartCampaign'>
				<input type="hidden" name="nameCampaign" value="<%=c.getNomeCampagna()%>"> 
					<input type="hidden" name="editable" value="0">
					
			<input type="hidden" name="page" value="home">
					 <input class='button' type='submit' value='Start Campaign'>
			</form>
			<a
				href="/ProgettoWeb/EditCampaign.jsp?campaignname=<%=c.getNomeCampagna()%>">Edit
				Campaign</a> <br>
			<%
				}
			%>
		</div>
		<%
			}
		%>
	</div>
	<%
		}

				//Schermata WORKER
			} else {
				List<String> myCampaignsN = new ArrayList<String>();
				myCampaignsN = user.loadCampaignsWorkerSelection();

				List<String> myCampaignsM = new ArrayList<String>();
				myCampaignsM = user.loadCampaignsWorkerAnnotation();
	%>

	<h1>
		Welcome,
		<%=user.getUsername()%></h1>
	<div class='containercampaignsworker'>
		<%
		if(myCampaignsM.size() == 0 && myCampaignsN.size() ==0){
		%><div class='container1'>	
		<div class='containerWorker'><p>You aren't enabled to any campaigns at the moment</p></div>
		</div><%
		}else{
			
			if (myCampaignsN.size() != 0) {
			%>

		<div class='container1'>
			<h2>Selection Task</h2>

			<%
				for (String s : myCampaignsN) {

								List<Image> images = new ArrayList<Image>();
								Campaign c = new Campaign();
								c.setNomeCampagna(s);
								images = c.loadCampaignImagesWorker(user);
								int imageToSelect = 0;
								int imageAccepted = 0;
								int imageRefused = 0;
			%>
			<div class='containerWorker'>
				<h3><%=s%></h3>
				<%if(c.isEditable()==0){
					for (Image i : images) {
										if (i.isAccepted(user) == 0) {
											imageToSelect++;
										}
										if (i.isAccepted(user) == 1) {
											imageAccepted++;
										}
										if (i.isAccepted(user) == 2) {
											imageRefused++;
										}
					}
					%>
					
					 Image Accepted: <%=imageAccepted%><br>
					Image Refused: <%=imageRefused%><br>
					Image not Selected:<%=imageToSelect%>
				
			
				<br> <br> <a
					href="/ProgettoWeb/ImageSelectionWorker.jsp?campaignname=<%=s%>">Go
					to Select</a>
					<%}else{
						%>Campaign not started yet..<%
					}	%>
					
			</div>
			<%
				}
			%>
				</div>
				<%
						}
		
			if (myCampaignsM.size() != 0) {
		%>

		<div class='container1'>
			<h2>Annotation Task</h2>
			<%
				for (String s : myCampaignsM) {

								List<Image> images = new ArrayList<Image>();
								Campaign c = new Campaign();
								c.setNomeCampagna(s);
								images = c.loadCampaignImagesWorker2(user);
			%>
			<div class='containerWorker'>
				<h3><%=s%></h3>
				<%if(c.isEditable()==0){
					if (images.size() != 0) {
				%>
				<a>Devi Annotare ancora <%=images.size()%> immagini
				</a>
				<br> <br> <a
					href="/ProgettoWeb/Annotation.jsp?campaignname=<%=s%>">Go to
					Annote</a>
				<%
					} else {
				%>
				<a>Non hai immagini da annotare</a>
				<%
					}
				%>
				
					<%}else{
					%>	Campaign not started yet..<%
					}	%>		
			</div>
			<%
				}%></div><%
						}
		}
			%>
		</div>
		<%
			}
	
		}
	%>





</body>
</html>