<jsp:include page="template/header.jsp" />

<h2 class="uk-h1">Graphique des Cryptomonnaies</h2>
<br/>

<div class="uk-card uk-card-body uk-card-default">
  <select id="cryptoSelect" class="uk-select">
    <option value="all">Toutes les cryptomonnaies</option>
  </select>
  <canvas id="myChart" width="400" height="200"></canvas>
  <br/>
</div>
<br/>
<h2 class="uk-h1">Liste des Cryptomonnaies</h2>
<br/>
<div class="uk-card uk-card-body uk-card-default">
  <table class="uk-table uk-table-middle uk-table-divider" id="cryptoTable">
    <thead>
      <tr>
        <th>Nom</th>
        <th>Valeur</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <!-- Les lignes seront ajoutées dynamiquement -->
    </tbody>
  </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
  $(document).ready(function() {
    var ctx = document.getElementById('myChart').getContext('2d');
    var myChart = new Chart(ctx, {
      type: 'line', // Type de graphique en courbe
      data: {
        labels: [], // Les labels seront ajoutés dynamiquement
        datasets: [] // Les datasets seront ajoutés dynamiquement
      },
      options: {
        scales: {
          y: {
            beginAtZero: false
          }
        }
      }
    });

    var historicalData = {}; // Stocker les données historiques

    function initializeDatasets() {
      $.ajax({
        url: '/rest/crypto',
        method: 'GET',
        success: function(response) {
          var cryptos = response;
          var select = $('#cryptoSelect');
          cryptos.forEach(function(crypto) {
            myChart.data.datasets.push({
              label: 'Prix du ' + crypto.nomCrypto,
              data: [],
              backgroundColor: getRandomColor(0.2),
              borderColor: getRandomColor(1),
              borderWidth: 1,
              fill: false // Ne pas remplir sous la courbe
            });
            historicalData[crypto.nomCrypto] = []; // Initialiser les données historiques
            select.append(new Option(crypto.nomCrypto, crypto.nomCrypto));
          });
          updateChart();
          myChart.update();
        }
      });
    }

    function updateChart() {
      $.ajax({
        url: '/crypto/getValues',
        method: 'GET',
        success: function(data) {
          var selectedCrypto = $('#cryptoSelect').val();
          myChart.data.labels = []; // Réinitialiser les labels
          myChart.data.datasets.forEach((dataset) => {
            dataset.data = []; // Réinitialiser les données du dataset
          });

          var labelsSet = false;
          myChart.data.datasets.forEach((dataset) => {
            var cryptoName = dataset.label.replace('Prix du ', '');
            var values = data[cryptoName];
            if (values && values.length > 0) {
              values.reverse(); // Inverser l'ordre des valeurs pour afficher les plus récentes à droite
              values.forEach((value) => {
                if (!labelsSet) {
                  var formattedDate = moment(value.dateHistorique).format('DD/MM/YYYY HH:mm:ss'); // Formater la date
                  myChart.data.labels.push(formattedDate); // Ajouter les labels de date
                }
                if (selectedCrypto === 'all' || cryptoName === selectedCrypto) {
                  dataset.data.push(value.valeur); // Ajouter les valeurs au dataset
                } else {
                  dataset.data.push(null); // Ajoute une valeur nulle pour maintenir l'alignement des labels
                }
              });
              labelsSet = true;
            }
          });
          myChart.update();
        }
      });
    }

    function updateCryptoTable() {
      $.ajax({
        url: '/rest/crypto',
        method: 'GET',
        success: function(response) {
          var tableBody = $('#cryptoTable tbody');
          tableBody.empty(); // Vider le tableau avant de le remplir
          response.forEach(function(crypto) {
            var row = '<tr>' +
              '<td>' + crypto.nomCrypto + '</td>' +
              '<td>' + crypto.valInitial + '</td>' +
              '<td><button class="uk-button uk-button-default">Acheter</button></td>' +
              '</tr>';
            tableBody.append(row);
          });
        }
      });
    }

    function getRandomColor(opacity) {
      var r = Math.floor(Math.random() * 255);
      var g = Math.floor(Math.random() * 255);
      var b = Math.floor(Math.random() * 255);
      return 'rgba(' + r + ',' + g + ',' + b + ',' + opacity + ')';
    }

    $('#cryptoSelect').change(function() {
      updateChart(); // Mettre à jour le graphique lorsque la sélection change
    });

    initializeDatasets();
    setInterval(updateChart, 10000); // Mettre à jour toutes les 10 secondes
    setInterval(updateCryptoTable, 10000); // Mettre à jour le tableau toutes les 10 secondes
    updateChart(); // Mettre à jour immédiatement au chargement de la page
    updateCryptoTable(); // Mettre à jour immédiatement au chargement de la page
  });
</script>
<jsp:include page="template/footer.jsp" />

