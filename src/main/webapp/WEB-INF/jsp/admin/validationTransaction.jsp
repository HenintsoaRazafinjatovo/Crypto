
<jsp:include page="../template/headerAdmin.jsp" />
<%@ page import="mg.crypto.models.*" %>
<%@ page import="java.util.List" %>
<br/>
<div class="uk-card uk-card-body uk-card-default">
<h2 class="uk-h4">Filtre</h2>
<form method="get" action="filterTransactions">
  <div class="uk-margin">
    <label for="type">Type:</label>
    <select name="type" id="type" class="uk-select">
      <option value="">Tous</option>
      <option value="Depot">Depot</option>
      <option value="Retrait">Retrait</option>
    </select>
  </div>
  <div class="uk-margin">
    <label for="etat">Etat:</label>
    <select name="etat" id="etat" class="uk-select">
      <option value="">Tous</option>
      <option value="Pending">Pending</option>
      <option value="Validated">Validated</option>
      <option value="Refused">Rejected</option>
    </select>
  </div>
  <button type="submit" class="uk-button uk-button-primary">Filtrer</button>
</form>
</div>
<br/>
<h2 class="uk-h1">Liste des Depots et Retraits</h2>
<br/>
<div class="uk-card uk-card-body uk-card-default">
  <table class="uk-table uk-table-middle uk-table-divider" id="cryptoTable">
    <thead>
      <tr>
        <th>ID</th>
        <th>ID User</th>
        <th>Type</th>
        <th>Montant</th>
        <th>Date</th>
        <th>Etat</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <% 
        List<MvtFondComplet> fonds = (List<MvtFondComplet>) request.getAttribute("fonds");
        for (MvtFondComplet fond : fonds) {
      %>
        <tr>
          <td><%= fond.getIdMvtFond() %></td>
          <td><%= fond.getIdUser() %></td>
          <td><%= fond.getType() %></td>
          <td><%= fond.getMontant() %></td>
          <td><%= fond.getDtMvt() %></td>
          <td><%= fond.getEtatText() %></td>
          <td>
          <% if(fond.getEtatText().equals("Pending")) { %>
            <form action="updateEtat" method="post">
              <input type="hidden" name="idMvtFond" value="<%= fond.getIdMvtFond() %>">
              <button type="submit" class="uk-btn uk-btn-default uk-btn-primary" name="newEtat" value="true">Valider</button>
              <button type="submit" class="uk-btn uk-btn-default" name="newEtat" value="false">Rejeter</button>
            </form>
          <% } %>
          </td>
        </tr>
      <% 
        }
      %>
    </tbody>
  </table>
</div>
<jsp:include page="../template/footer.jsp" />

