<jsp:include page="template/header.jsp" />
<h1 class="uk-h1">Portefeuille</h1>
<br />
<div class="uk-card uk-card-body uk-card-default">

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
    <c:forEach var="crypto" items="${stockCryptos}">
      <td>${crypto.nomCrypto}</td>
      <td>${crypto.totalQuantite}</td>
      <td>
        <button class="uk-btn uk-btn-default" type="button">
            Vente
        </button>
      </td>
    </c:forEach>
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