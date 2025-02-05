<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<jsp:include page="template/header.jsp"/>
<%@page import="mg.crypto.models.*" %>
<style>
     


 </style>

<h1 class="uk-h1">Liste des transactions</h1>
<br />
<div class="uk-card uk-card-body uk-card-default">
<form class="uk-form-inline" action="/etatFond" method="post">
       <div class="column" id="actif">
              <h2>fond total</h2>
              <ul>
                <li>Montant<span><%= (double) request.getAttribute("fond") %></span>
                </li>
              </ul>
        </div>
      
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
    <% List<MvtFond>  mvtFonds = (List<MvtFond>) request.getAttribute("lmvt"); %> 
    <% for(MvtFond mvtFond : mvtFonds)  { %>
    <tr>
      <td><%= mvtFond.getIdUser() %></td>
      <td><%= mvtFond.getTypeMvt() %></td>
      <td><%= mvtFond.getMontant() %> </td>
      <td><%= mvtFond.getDtMvt() %></td>
    </tr>
    <% } %>
  </tbody>
</table>
</div>
<jsp:include page="template/footer.jsp" />