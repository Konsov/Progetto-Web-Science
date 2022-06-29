<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@ page import="com.beans.User"%>
<%@ page import="com.beans.Campaign"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>



<!-- Style -->
<link rel="stylesheet" href="css/styleNewCampaign.css" type="text/css">


<title>NewCampaign</title>
</head>
<body>
	<%
		if (session.getAttribute("user") == null) {
			response.sendRedirect("Login.jsp");
		} else {

			User user = new User();
			user = (User) session.getAttribute("user");
			
			List<User> workers = new ArrayList<User>();
			workers = user.loadWorkers();
	%>

<h3>Create New Campaign</h3>
<div class="container">
	<form action="CreateCampaign" method="post">
	
		<input name="campaignname" class="inputText"  placeholder="Insert Campaign Name" type="text" required>
		
	<p>	Workers enable to Selection task:</p>
		<div name="nWorkers" class="check">
		<%for(User u: workers){ %>
		<input type='checkbox'  name='Nusers' value="<%=u.getUsername()%>"/><%=u.getUsername()%><br>
		<% }%>
		
		</div>
		
		<input name="Kvalutations" class="inputText"  placeholder="Insert K-Value" type="text" required>
		
		<p>Workers enable to Annotation Task:</p>
		<div name="mWorkers" class="check" >
		<%for(User u: workers){ %>
		<input type='checkbox' name='Musers' value="<%=u.getUsername()%>"/><%=u.getUsername()%><br>
		<% }%>
		</div>
		
		<input name="pixelline"  class="inputText"  placeholder="Insert Pixel-Line Width" type="text" required><br>
		
		 <input	name="owner" type="hidden" value = "<%=user.getUsername()%>"><br>
		<input type="submit"  class="button" value="Create">
	</form>

</div>
	<%} %>

</body>
</html>