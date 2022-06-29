<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Style -->
<link rel="stylesheet" href="css/styleRegister.css" type="text/css">


<title>Register Page</title>
</head>
<body>


<div class="container">
<form action="RegisterServlet" method="POST">

<h2>Join Us!</h2>


<a id="error">Sorry this username already exist</a><br>
<input class="inputText" type="text" name="username" placeholder="Insert Your Username" required> <br>
<input class="inputText" type="password" name="password" placeholder="Insert Your Password" required>
<br>
<select class="inputText" name="role">
  <option value="manager">Manager</option>
  <option value="worker">Worker</option>
</select><br>
  <input class="button" type="submit" value="Register Now">
</form>
</div>

</body>
</html>