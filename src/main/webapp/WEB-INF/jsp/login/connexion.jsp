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
        <h2>Login</h2>
        <form id="loginForm">
            <input type="email" name="email" placeholder="Email" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="submit" value="Login">
        </form>
        <p>Don't have an account? <a href="/inscription">Register here</a></p>
        <p>Or you are admin? <a href="/login/admin">Heree</a></p>

    </div>

    <script>
        $(document).ready(function() {
            $('#loginForm').submit(function(event) {
                event.preventDefault(); // Empêche la soumission normale du formulaire

                var formData = {
                    email: $('input[name="email"]').val(),
                    password: $('input[name="password"]').val()
                };
                var $submitButton = $('input[type="submit"]');
                $submitButton.prop('disabled', true); // Désactiver le bouton
                $submitButton.css('background-color', 'grey'); // Griser le bouton

                $.ajax({
                    url: API_BASE_URL + '/login',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(formData),
                    success: function(response) {
                        // Gérer la réponse de succès
                        alert('Login successful! Votre code pin a ete envoyer a votre email');
                        // Rediriger vers une autre page si nécessaire
                        window.location.href = '/pin';
                    },
                    error: function(xhr, status, error) {
                        // Gérer les erreurs
                        alert('Login failed: ' + xhr.responseText);
                    },
                    complete: function() {
                        $submitButton.prop('disabled', false); // Réactiver le bouton
                        $submitButton.css('background-color', ''); // Restaurer la couleur d'origine
                    }
                });
            });
        });
    </script>
</body>
</html>