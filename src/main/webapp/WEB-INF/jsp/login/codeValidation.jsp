<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Code Validation</title>
    <link rel="stylesheet" href="/assets/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container">
        <h2>Enter Validation Code</h2>
        <!-- Display alert message -->
        <div id="alertMessage" class="alert" style="display: none;">
            <p id="alertText"></p>
        </div>
        <form id="codeValidationForm">
            <input type="text" name="code" placeholder="Validation Code" required>
            <input type="submit" value="Submit">
        </form>
    </div>

    <script>
        $(document).ready(function() {
            $('#codeValidationForm').submit(function(event) {
                event.preventDefault(); // Empêche la soumission normale du formulaire

                var formData = {
                    code: $('input[name="code"]').val()
                };

                $.ajax({
                    url: 'http://localhost:5005/api/login/code',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(formData),
                    success: function(response) {
                        // Gérer la réponse de succès
                        $('#alertMessage').show();
                        $('#alertText').text('Code validation successful!');
                        $.ajax({
                            url: 'http://localhost:8080/session/set',
                            type: 'GET',
                            data: { idUser: response.userId },
                            success: function() {
                                console.log('Session set successfully');
                                // Rediriger vers une autre page si nécessaire
                                window.location.href = '/vente/form';
                            },
                            error: function(xhr, status, error) {
                                console.log('Failed to set session: ' + xhr.responseText);
                            }
                        });
                    },
                    error: function(xhr, status, error) {
                        // Gérer les erreurs
                        $('#alertMessage').show();
                        $('#alertText').text('Code validation failed: ' + xhr.responseText);
                    }
                });
            });
        });
    </script>
</body>
</html>