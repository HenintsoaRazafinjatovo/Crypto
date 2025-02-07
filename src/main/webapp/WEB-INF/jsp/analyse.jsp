<jsp:include page="template/header.jsp" />
<h1 class="uk-h1">Analyse</h1>
<br />
<form class="uk-form-stacked" method="post" action="/analyseTransaction">
    <div class="mb-3">
        <label class="uk-form-label" for="form-stacked-select">Date</label>
        <div class="uk-form-controls">
            <label for="datemvt">Date et heure max:</label>
            <input class="uk-input" type="datetime-local" id="datemvt" name="datemvt" required>
            <br>
        </div>
        <input type="submit" class="uk-btn uk-btn-primary" value="Valider" />
    </div>
</form>
<table class="uk-table uk-table-middle uk-table-divider">
  <thead>
    <tr>
      <th class="uk-width-small">id_user</th>
      <th>Total achat</th>
      <th>Total vente</th>
      <th>Valeur portefeuille</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="analyse" items="${transactions}">
        <tr>
            <td>${analyse.idUser}</td>
            <td>${analyse.totalAchat}</td>
            <td>${analyse.totalVente}</td>
            <td>${analyse.valeurPorteFeuille}</td>
        </tr>
    </c:forEach>
  </tbody>
</table>

<jsp:include page="template/footer.jsp" />