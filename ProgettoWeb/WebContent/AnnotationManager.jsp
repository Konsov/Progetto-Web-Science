<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<title>Info Annotation</title>
<script>

var slideIndex = 1;

function plusDivs(n,nameslide) {
  showDivs(slideIndex += n,nameslide);
}

function showDivs(n,nameslide) {
  var i;
  var x = document.getElementsByClassName(nameslide);
  if (n > x.length) {
	  slideIndex = 1
	  }    
  if (n < 1) {
	  slideIndex = x.length
	  }
  for (i = 0; i < x.length; i++) {
     x[i].style.display = "none";  
  }
  x[slideIndex-1].style.display = "block";  
}

</script>


<!-- Style -->
<link rel="stylesheet" href="css/styleAnnotation.css" type="text/css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ page import="com.beans.User"%>
<%@ page import="com.beans.Campaign"%>
<%@ page import="com.beans.Image"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>



<body>
<%if (session.getAttribute("user") == null) {
			response.sendRedirect("Login.jsp");

		} else {

%>
<form action='Home.jsp'>
		<input class='button' type='submit' value='Turn on my Campaigns'>
	</form>
<h2><%=request.getParameter("campaignname")%></h2>
<%
            Campaign campaign = new Campaign();
			campaign.setNomeCampagna(request.getParameter("campaignname"));

			List<Image> images = new ArrayList<Image>();
			images = campaign.loadImagesAnnotated(); 
			if(images.size() !=0 ){
			int k = 0;
			float totAnnotation = 0;
			%> 
			<div class="container1"> 
			<%
			for(Image i: images){ 
			List<String> lines = new ArrayList<String>();
			lines = i.loadPixelLines(); 
		k++;
%>
<div class="container" >
  <div class="imgMountain"  style="background-image: url('/project/immaginiCampagne/<%=i.getName()%>')">
  <%for(String s: lines){ %>
  <img id="linea"  onload="showDivs(1,'mySlides<%=k%>')" class="mySlides<%=k%>" src="/project/annotazioniCanvas/<%=s%>" width='100%' height='100%'>
<%} %>

  </div>
 <div class="info">
 Questa immagini ha <%=lines.size()%> annotazioni
 <%totAnnotation += lines.size();%>
  <button onclick="plusDivs(-1,'mySlides<%=k%>')">&#10094;</button>
  <button onclick="plusDivs(1,'mySlides<%=k%>')">&#10095;</button>
</div>
</div>
<% }%>
<p>Sono state annotate <%=k %> immagini con una media di <%=(totAnnotation/k)%> Annotazioni per immagine.</p>
<% }else{%>
Non ci sono ancora immagini annotate in questa campagna
<%} %>
</div>
<%} %>
</body>
</html>