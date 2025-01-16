<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Page</title>
    <link rel="stylesheet" href="/assets/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/js/apiConfig.js"></script>
</head>
<body>
    <div class="container">
        <h2>Register</h2>
        <!-- Display alert message -->
        <div id="message" class="alert" style="display: none;">
            <p></p>
        </div>
        <form id="registrationForm">
            <input type="text" name="username" placeholder="Username" required>
            <input type="email" name="email" placeholder="Email" required>
            <input type="text" name="telephone" placeholder="Telephone" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="submit" value="Register">
        </form>
        <p>Have an account? <a href="/login">CONNECT here</a></p>
    </div>

    <script>
        $(document).ready(function() {
            $('#registrationForm').submit(function(event) {
                event.preventDefault(); // Empêche la soumission normale du formulaire

                var formData = {
                    username: $('input[name="username"]').val(),
                    email: $('input[name="email"]').val(),
                    telephone: $('input[name="telephone"]').val(),
                    password: $('input[name="password"]').val()
                };

                var $submitButton = $('input[type="submit"]');
                $submitButton.prop('disabled', true); // Désactiver le bouton
                $submitButton.css('background-color', 'grey'); // Griser le bouton
                

                $.ajax({
                    url: API_BASE_URL + '/register',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(formData),
                    success: function(response) {
                        // Gérer la réponse de succès
                        $('#message').show().find('p').text('Registration successful! Please check your email for verification.');
                    },
                    error: function(xhr, status, error) {
                        // Gérer les erreurs
                        $('#message').show().find('p').text('Registration failed: ' + xhr.responseText);
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