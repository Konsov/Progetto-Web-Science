<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js" type="text/javascript"></script> 


<!-- Style -->
<link rel="stylesheet" href="css/styleAnnotationW.css" type="text/css">

<%@ page import="com.beans.User"%>
<%@ page import="com.beans.Campaign"%>
<%@ page import="com.beans.Image"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<title>Annotation</title>


<script>
	var ctx = null;
	var started = false;
	var imgPath;

	function disegnare(pixelLine) {

		ctx = document.querySelector('canvas').getContext('2d');
		ctx.lineWidth = pixelLine; //dimensione linea
		ctx.strokeStyle = 'red';
		started = false;
		

		iniziaDisegno = function(evento) {
			ctx.beginPath();
			ctx.moveTo(evento.offsetX, evento.offsetY);
			started = true;
		}

		disegna = function(evento) {
			if (started) {
				ctx.lineTo(evento.offsetX, evento.offsetY);
				ctx.stroke();
			}
		}

		fermaDisegno = function(evento) {
			ctx.closePath();
			started = false;
		}
		
		ctx.canvas.addEventListener('mousedown', iniziaDisegno, false);
		ctx.canvas.addEventListener('mousemove', disegna, false);
		ctx.canvas.addEventListener('mouseup', fermaDisegno, false);
		ctx.canvas.addEventListener('mouseleave', fermaDisegno, false);

	}

	function erase() {
		ctx.clearRect(0, 0, document.querySelector('canvas').width, document.querySelector('canvas').height);
	}
	


	function setImage(campaign_name, worker){
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var json = JSON.parse(this.responseText);
				if(json.vuoto != 0){
				imgPath = json.name_image;
				document.getElementById("imgtoannote").src ="/project/immaginiCampagne/" + imgPath;
				}
				else{
					document.getElementById("container").innerHTML="NON CI SONO ALTRE IMMAGINI DA ANNOTARE";
				}
				}
		};
		xhttp.open("GET", "GetImageToAnnote?campaign="+ campaign_name + "&worker="+worker , true);
		xhttp.send();
	}
	
	function saveAnnotationToServer(campaign_name, worker){   
		var canvas = document.getElementById("canvas");
		var dataURL = canvas.toDataURL();
		$.ajax({
		    type: "POST",
		    url: "SaveAnnotation?campaign="+ campaign_name + "&worker="+worker+"&img="+imgPath,
		    data: dataURL,
		}); 
		}
	
	function save(campaign_name, worker){
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				saveAnnotationToServer(campaign_name, worker);
				erase();
				setImage(campaign_name, worker);
			}
		};
		xhttp.open("GET", "AnnotationServlet?id="+ imgPath , true);
		xhttp.send();
	}
		

</script>
</head>


	
<%
if (session.getAttribute("user") == null) {
	response.sendRedirect("Login.jsp");

} else {
        Campaign campaign = new Campaign();
		campaign.setNomeCampagna(request.getParameter("campaignname"));
		int pixelLine = campaign.getPixellineByName();
		User user = new User();
		user = (User) session.getAttribute("user");
		 %>
<body onload="setImage('<%=request.getParameter("campaignname")%>','<%=user.getUsername()%>')">
	<form action='Home.jsp'>
		<input class='button' type='submit' value='Turn on my Campaigns'>
	</form>
	
	<div align="center" id="container">

	<canvas id="canvas" width="400px" height="300px" style="position:absolute; border: 2px solid #000; z-index:20;"></canvas>

		<img onload="disegnare(<%=pixelLine%>)" id="imgtoannote"
			style="position: relative; z-index: 10; padding-top: 2px; padding-left: 2px;"
			width="400px" height="300px">
			<br><br>
 <input type="button" id="saveButton" onclick="save('<%=request.getParameter("campaignname")%>','<%=user.getUsername()%>')" value="AccettaAnnotazione" />

<input type="button" id="eraseButton" onclick="erase()" value="Annulla Annotazione" />
	</div>
	<%} %>

</body>
</html>