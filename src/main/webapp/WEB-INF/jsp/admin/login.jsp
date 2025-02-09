<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="/assets/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/js/apiConfig.js"></script>
</head>
<body>
    <div class="container">
        <h2>Login Admin</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        <form id="loginForm" method="POST" action="/login/admin">
            <input type="text" name="nom" placeholder="Email" value="admin" required>
            <input type="password" name="pwd" placeholder="Password" value="admin" required>
            <input type="submit" value="Login">
        </form>
    </div>
</body>
</html>