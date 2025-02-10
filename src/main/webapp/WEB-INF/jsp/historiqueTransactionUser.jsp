<jsp:include page="template/header.jsp" />
<table class="uk-table uk-table-middle uk-table-divider">
  <thead>
    <tr>
      <th class="uk-width-small">id_user</th>
      <th>Cryptomonnaie</th>
      <th>Type transaction</th>
      <th>Montant</th>   
    </tr>
  </thead>
  <tbody>
    <c:forEach var="analyse" items="${transactions}">
        <tr>
            <td><a href="/frontOffice/histoTransactionUser?id_user=${analyse.idUser}">${analyse.idUser}</a></td>
            <td>${analyse.idCrypto}</td>
            <td>${analyse.type}</td>
            <td>${analyse.montant}</td>  
        </tr>
    </c:forEach>
  </tbody>
</table>
<jsp:include page="template/footer.jsp" />