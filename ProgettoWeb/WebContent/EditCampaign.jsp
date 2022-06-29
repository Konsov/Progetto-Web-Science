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



<title>Edit Campaign</title>
</head>
<body>

<%
		if (session.getAttribute("user") == null) {
			response.sendRedirect("Login.jsp");
		} else {

			User user = new User();
			user = (User) session.getAttribute("user");
			
			List<User> AllWorkers = new ArrayList<User>();
			AllWorkers = user.loadWorkers();
			
			
			Campaign c = new Campaign();
			c.setNomeCampagna(request.getParameter("campaignname"));
			c.setOwner(user.getUsername());

			List<String> nWorkers = new ArrayList<String>();
			nWorkers = c.loadNworkersFromCampaign();
			

			List<String> mWorkers = new ArrayList<String>();
			mWorkers = c.loadMworkersFromCampaign();
			
			List<String> nWorkersFree = new ArrayList<String>();
			for(int i = 0; i < AllWorkers.size(); i++){
				int k = 0;
				for(k = 0; k < nWorkers.size(); k++){
					if(AllWorkers.get(i).getUsername().equals(nWorkers.get(k)))
						break;
				}
				if(k == nWorkers.size()){
					nWorkersFree.add(AllWorkers.get(i).getUsername());
				}
			}
			
			List<String> mWorkersFree = new ArrayList<String>();
			for(int i = 0; i < AllWorkers.size(); i++){
				int k = 0;
				for(k = 0; k < mWorkers.size(); k++){
					if(AllWorkers.get(i).getUsername().equals(mWorkers.get(k)))
						break;
				}
				if(k == mWorkers.size()){
					mWorkersFree.add(AllWorkers.get(i).getUsername());
				}
			}
	%>

	
<div class="container">
 
<h3>What do you want to edit?</h3>


	<form action="AddPhoto.jsp">
					<%
					String[] nWork = new String[nWorkers.size()];
					String[] mWork = new String[mWorkers.size()];
					nWork = nWorkers.toArray(nWork);
					mWork = mWorkers.toArray(mWork);
					
					session.setAttribute("campaign", c);
					session.setAttribute("nWorkers", nWork);	
						session.setAttribute("mWorkers", mWork);	
						 %>
 <input type="submit"  class="button" value="AddPhoto">
 </form>	

	<form action="EditCampaign" method="post">
	
	
	
		
	<p>	Workers enable to Selection task:</p>
		<div name="nWorkers" class="check">
		<%for(String s: nWorkersFree){ %>
		<input type='checkbox'  name='Nusers' value="<%=s%>"/><%=s%><br>
		<% }%>
		
		</div>
		
		<input name="Kvalutations" class="inputText"  placeholder="Insert K-Value" type="text" >
		
		<p>Workers enable to Annotation Task:</p>
		<div name="mWorkers" class="check" >
		<%for(String s: mWorkersFree){ %>
		<input type='checkbox' name='Musers' value="<%=s%>"/><%=s%><br>
		<% }%>
		</div>
		
		<input name="pixelline"  class="inputText"  placeholder="Insert Pixel-Line Width" type="text"><br>
		
		
		 <input	name="nWorkersOld" type="hidden" value = "<%=nWorkers.size()%>">
		 <input	name="campaignname" type="hidden" value = "<%=request.getParameter("campaignname")%>">
		 <input	name="owner" type="hidden" value = "<%=user.getUsername()%>"><br>
		<input type="submit"  class="button" value="Edit">
	</form>

</div>

<%} %>



</body>
</html>