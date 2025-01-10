<jsp:include page="template/header.jsp" />

<h2 class="uk-h1">Graphique des Cryptomonnaies</h2>
<br/>

<div class="uk-card uk-card-body uk-card-default">
<select id="cryptoSelect" class="uk-select">
  <option value="all">Toutes les cryptomonnaies</option>
</select>
<canvas id="myChart" width="400" height="200"></canvas>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
          updateChart()
          myChart.update();
        }
      });
    }

    function updateChart() {
      $.ajax({
        url: '/crypto/getValues',
        method: 'GET',
        success: function(data) {
          var currentTime = new Date().toLocaleTimeString();
          myChart.data.labels.push(currentTime);
          var selectedCrypto = $('#cryptoSelect').val();
          myChart.data.datasets.forEach((dataset) => {
            var cryptoName = dataset.label.replace('Prix du ', '');
            var value = data[cryptoName];
            historicalData[cryptoName].push(value); // Stocker les données historiques
            if (selectedCrypto === 'all' || cryptoName === selectedCrypto) {
              dataset.data.push(value);
            } else {
              dataset.data.push(null); // Ajoute une valeur nulle pour maintenir l'alignement des labels
            }
          });
          myChart.update();
        }
      });
    }

    $('#cryptoSelect').change(function() {
      var selectedCrypto = $(this).val();
      myChart.data.datasets.forEach((dataset) => {
        var cryptoName = dataset.label.replace('Prix du ', '');
        dataset.data = historicalData[cryptoName].map((value, index) => {
          if (selectedCrypto === 'all' || cryptoName === selectedCrypto) {
            return value;
          } else {
            return null; // Ajoute une valeur nulle pour maintenir l'alignement des labels
          }
        });
      });
      myChart.update();
    });

    function getRandomColor(opacity) {
      var r = Math.floor(Math.random() * 255);
      var g = Math.floor(Math.random() * 255);
      var b = Math.floor(Math.random() * 255);
      return 'rgba(' + r + ',' + g + ',' + b + ',' + opacity + ')';
    }

    initializeDatasets();
    setInterval(updateChart, 10000); // Mettre à jour toutes les 10 secondes
    updateChart(); // Mettre à jour immédiatement au chargement de la page
  });
</script>

<jsp:include page="template/footer.jsp" />