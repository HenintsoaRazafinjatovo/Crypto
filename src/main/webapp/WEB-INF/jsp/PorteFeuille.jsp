<%@page import="java.util.List"%>
<%@page import="mg.crypto.models.StockFondUser"%>
<%@page import="mg.crypto.models.StockCryptoUser"%>

<jsp:include page="template/header.jsp" />

<% List<StockFondUser> fonds = (List<StockFondUser>)request.getAttribute("fondUsers"); %>
<% List<StockCryptoUser> cryptos = (List<StockCryptoUser>)request.getAttribute("stockCryptos"); %>
<% double valeurTotalCryptos = (double)request.getAttribute("valeurTotalCryptos"); %>
<h1 class="uk-h1">Portefeuille</h1>
<br />
<div class="uk-card uk-card-body uk-card-default">

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
  <% for(StockCryptoUser crypto : cryptos) { %> 
    <tr>
      <form action="/vente/faireVente" method="post">
        <input type="hidden" name="idUser" value="<%=crypto.getIdUser()%>">
        <input type="hidden" name="idCrypto" value="<%=crypto.getIdCrypto()%>">
        <td><%=crypto.getNomCrypto()%></td>
        <td><%=crypto.getQttTotal()%></td>
        <td><%=crypto.getValeurTotal()%></td>
        <td><input type="number" min="0" name="qtt" step="1"></td>
        <td>
          <button class="uk-btn uk-btn-default" type="submit">Vendre</button>
        </td>
      </form>
    </tr>
  <% } %>
</tbody>

  </table>
  </br>
  <p> Valeur total des cryptos : <%=valeurTotalCryptos %> <p>
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
      <% for(StockFondUser fond : fonds) { %>
        <td><%=fond.getDepotTotal()%></td>
        <td><%=fond.getRetraitTotal()%></td>
        <td><%=fond.getFondTotal()%></td>
        <% } %>
    </form>
    </tr>
  </tbody>
</table>
</div>
  
</div>
<jsp:include page="template/footer.jsp" />