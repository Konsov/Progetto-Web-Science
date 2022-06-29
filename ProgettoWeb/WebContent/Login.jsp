<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>

<!-- Style -->
<link rel="stylesheet" href="css/styleLogin.css" type="text/css">
</head>
<body>

<div class="container">

<h1>Crowdsourcing Project</h1>


<div class="containerForm">
<!-- Form che passa dati di Login ad una servlet, che controllerà le credenziali -->

<form action="LoginServlet" method ="POST">

<a id="error">Invalid username or password</a><br>
<input type="text" name = "username" class="inputText" placeholder="Username" required> <br>
<input type="password" name = "password" class="inputText" placeholder="Password" required><br>
<input type="submit" value= "SIGN IN" class="button">

<!-- Link per la Registrazione di un nuovo account -->
<br>Not a member?<a href="/ProgettoWeb/Register.jsp"> Sing up Now!</a>
</form>

</div>
</div>
</body>
</html>