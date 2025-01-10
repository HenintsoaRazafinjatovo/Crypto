<%  %>
<jsp:include page="template/header.jsp" />
<%@ page import="mg.crypto.models.MvtFond" %>
<%@ page import="java.util.List" %>
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
      <th>mouvement type</th>
      <th>montant</th>
      <th>Date</th>
    </tr>
  </thead>
  <tbody>
    <% for(MvtFond mvt : (List<MvtFond>)request.getAttribute("lmvt"))  {%>
    <tr>
    
    
      <td>Utilisateur</td>
      <td>mouvement type</td>
      <td>montant</td>
      <td>Date</td>
    </tr>
    <% } %>
  </tbody>
</table>
</div>
<jsp:include page="template/footer.jsp" />