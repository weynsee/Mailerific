<html>
<head>
<link type="text/css" rel="stylesheet" href="/Status.css">
<title>Mailerific!</title>
</head>
<body>
<div class="status">
Hello! It seems you are not logged in. Please follow this 
<a href="<%=request.getAttribute("loginUrl")%>">link</a> to login.
If you don't know how you got this page, maybe you would like to
check out <a href="<%=request.getAttribute("home")%>">Mailerific</a>?
Thanks!
</div>
</body>
</html>