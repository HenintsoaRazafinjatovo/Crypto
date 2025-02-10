<%@page import="java.util.List"%>
<%@page import="mg.crypto.models.Crypto"%>
<%@page import="mg.crypto.models.User"%>
<%@page import="mg.crypto.models.MvtTransaction"%>


<jsp:include page="template/header.jsp" />

<% List<Crypto> cryptos = (List<Crypto>)request.getAttribute("cryptos"); %>
<% List<User> users = (List<User>)request.getAttribute("users"); %>
<% List<MvtTransaction> transactions = (List<MvtTransaction>)request.getAttribute("transactions"); %>
<% Crypto c=new Crypto();%>
<h1 class="uk-h1">Analyse</h1>
<br />
<form class="uk-form-stacked" method="get" action="/frontOffice/histoTransactionAll">
    <div class="mb-3">
        <label class="uk-form-label" for="form-stacked-select">Select crypto</label>
        <div class="uk-form-controls">
            <select class="uk-select" name="crypto" id="form-stacked-select">
                <option>Select crypto</option>
                <% for(Crypto crypto : cryptos) { %>
                    <option value="<%=crypto.getIdCrypto()%>"><%=crypto.getNomCrypto()%></option>
                <% } %>
            </select>
        </div>
    </div>
    <div class="mb-3">
        <label class="uk-form-label" for="form-stacked-select">Select user</label>
        <div class="uk-form-controls">
            <select class="uk-select" name="user" id="form-stacked-select">
                <option>Select user</option>
                <% for(User user : users) { %>
                    <option value="<%=user.getId()%>"><%=user.getUsername()%></option>
                <% } %>
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
      <th>Type transaction</th>
      <th>Montant</th>
     
    </tr>
  </thead>
  <tbody>
    <% if(transactions != null) {
        
    for(MvtTransaction transaction : transactions) { %>
        <tr>
            <td><a href="/frontOffice/histoTransactionUser?id_user=<%=transaction.getIdUser()%>"></a></td>
            <td><%=transaction.getIdUser()%></td>
            <td><%=c.findById(transaction.getIdCrypto()).getNomCrypto()%></td>

            <td><%=transaction.getType() ? "Vente" : "Achat" %></td>
            <td><%=transaction.getMontant()%></td>
            
        </tr>
    <% }} %>
  </tbody>
</table>
<jsp:include page="template/footer.jsp" />