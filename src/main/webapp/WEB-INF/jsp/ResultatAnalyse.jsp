<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<jsp:include page="template/header.jsp"/>
<%@page import="mg.crypto.models.*" %>

<table class="uk-table uk-table-middle uk-table-divider">
   <% 
   List<Analyse>  mvtFonds = (List<Analyse>) request.getAttribute("analyse"); 
    Analyse first = mvtFonds.get(0);
   %>
  <thead>
    <h1 class="uk-h1">Analyse <%= first.getAction() %></h1>
    <tr>
      <th>getNomCrypto</th>
      <th><%= first.getType() %></th>
    </tr>
  </thead>
  <tbody>
 
    <tr>
      <% for(Analyse mvtFond : mvtFonds)  { %>
    <tr>
      <td><%= mvtFond.getNomCrypto() %></td>
      <td><%= mvtFond.getMontant() %></td>
    </tr>
    <% } %>
    </tr>

  </tbody>
</table>