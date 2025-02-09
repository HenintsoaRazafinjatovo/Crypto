<jsp:include page="template/header.jsp" />
<h1 class="uk-h1">Portefeuille</h1>
<br />
<div class="uk-card uk-card-body uk-card-default">

<<<<<<< Updated upstream
<table class="uk-table uk-table-middle uk-table-divider">
  <thead>
    <tr>
      <th class="uk-width-small">Crytomonnaie</th>
      <th>Quantite total</th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
    <form action="/venteCrypto" method="post">
        <c:forEach var="crypto" items="${stockCryptos}">
        <input type="hidden" name="idCrypto" value="${crypto.idCrypto}">
        <td>${crypto.nomCrypto}</td>
        <td>${crypto.totalQuantite}</td>
        <td>
            <button class="uk-btn uk-btn-default" type="button">
                Vente
            </button>
        </td>
=======
  <table class="uk-table uk-table-middle uk-table-divider">
    <thead>
      <tr>
        <th class="uk-width-small">Crytomonnaie</th>
        <th>Quantite total</th>
        <th>Valeur crypto</th>
        <th>Quantite à vendre</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
  <c:forEach var="crypto" items="${stockCryptos}">
    <tr>
      <form action="/vente/faireVente" method="post">
        <input type="hidden" name="idUser" value="${crypto.idUser}">
        <input type="hidden" name="idCrypto" value="${crypto.idCrypto}">
        <td>${crypto.nomCrypto}</td>
        <td>${crypto.totalQuantite}</td>
        <td>${crypto.valeurTotal}</td>
        <td><input type="number" min="0" name="qtt" step="1"></td>
        <td>
          <button class="uk-btn uk-btn-default" type="submit">Vendre</button>
        </td>
      </form>
    </tr>
  </c:forEach>
</tbody>

  </table>
  </br>
  <p> Valeur total des cryptos : ${valeurTotalCryptos} <p>
  </br>



  <table class="uk-table uk-table-middle uk-table-divider">
    <thead>
      <tr>
        <th class="uk-width-small">Depot total</th>
        <th>Retrait total</th>
        <th>Fond total</th>
      </tr>
    </thead>
    <tbody>
      <tr>
      <c:forEach var="fond" items="${fondUsers}">
        <td>${fond.totalDepot}</td>
        <td>${fond.totalRetrait}</td>
        <td>${fond.fondTotal}</td>
>>>>>>> Stashed changes
        </c:forEach>
    </form>
    </tr>
  </tbody>
</table>
</div>


<table class="uk-table uk-table-middle uk-table-divider">
  <thead>
    <tr>
      <th class="uk-width-small">Depot total</th>
      <th>Retrait total</th>
      <th>Fond total</th>
    </tr>
  </thead>
  <tbody>
    <tr>
    <c:forEach var="fond" items="${fondUsers}">
      <td>${fond.totalDepot}</td>
      <td>${fond.totalRetrait}</td>
      <td>${fond.fondTotal}</td>
      </c:forEach>
    </tr>
  </tbody>
</table>
</div>
<jsp:include page="template/footer.jsp" />