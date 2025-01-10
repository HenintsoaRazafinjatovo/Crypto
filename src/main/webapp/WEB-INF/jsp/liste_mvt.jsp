<jsp:include page="template/header.jsp" />
<h1 class="uk-h1">Liste des transactions</h1>
<br />
<div class="uk-card uk-card-body uk-card-default">
<form class="uk-form-inline" action="/filtrerMvt" method="post">
  <div class="mb-3">
    <label class="uk-form-label">Type</label>
    <div class="uk-form-controls">
      <select name="typeComposant" class="uk-select">
        <option value="-1"> </option>
          <option value="1">Achat</option>
          <option value="2">Vente</option>
      </select>
    </div>
  </div>
  <input type="submit" class="uk-btn uk-btn-primary" value="Filtrer" />
</form>
<br/>
<table class="uk-table uk-table-middle uk-table-divider">
  <thead>
    <tr>
      <th>Utilisateur</th>
      <th>Cryptomonnaie</th>
      <th>Quantite</th>
      <th>Type</th>
        <th>Prix</th>
        <th>Date de transaction</th>
    </tr>
  </thead>
  <tbody>
    <tr>
        <td>Utilisateur</td>
        <td>Cryptomonnaie</td>
        <td>Quantite</td>
        <td>Type</td>
        <td>Prix</td>
        <td>Date de transaction</td>
    </tr>
  </tbody>
</table>
</div>
<jsp:include page="template/footer.jsp" />