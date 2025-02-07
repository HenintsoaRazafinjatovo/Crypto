<jsp:include page="template/header.jsp" />
<h1 class="uk-h1">Analyse</h1>
<br />
<form class="uk-form-stacked" method="post" action="">
    <div class="mb-3">
        <label class="uk-form-label" for="form-stacked-select">Select crypto</label>
        <div class="uk-form-controls">
            <select class="uk-select" name="crypto" id="form-stacked-select">
                <option value="">Option 01</option>
            </select>
        </div>
    </div>
    <div class="mb-3">
        <label class="uk-form-label" for="form-stacked-select">Select user</label>
        <div class="uk-form-controls">
            <select class="uk-select" name="user" id="form-stacked-select">
                <option value="">Option 01</option>
            </select>
        </div>
    </div>
    <div class="mb-3">
        <label class="uk-form-label" for="form-stacked-select">Date</label>
        <div class="uk-form-controls">
            <label for="datemvt">Date et heure max:</label>
            <input class="uk-input" type="datetime-local" id="datemvt" name="datemvt" required>
            <br>
        </div>
    </div>
    <input type="submit" class="uk-btn uk-btn-primary" value="Valider" />
</form>
<table class="uk-table uk-table-middle uk-table-divider">
  <thead>
    <tr>
      <th class="uk-width-small"></th>
      <th>id_user</th>
      <td>Cryptomonnaie</td>
      <th>Total achat</th>
      <th>Total vente</th>
     
    </tr>
  </thead>
  <tbody>
    <c:forEach var="analyse" items="${fonds}">
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            
        </tr>
    </c:forEach>
  </tbody>
</table>
<jsp:include page="template/footer.jsp" />