<jsp:include page="template/header.jsp" />
<table class="uk-table uk-table-middle uk-table-divider">
  <thead>
    <tr>
      <th class="uk-width-small">id_user</th>
      <th>Cryptomonnaie</th>
      <th>Total achat</th>
      <th>Total vente</th>   
    </tr>
  </thead>
  <tbody>
    <c:forEach var="analyse" items="${transactions}">
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>   
        </tr>
    </c:forEach>
  </tbody>
</table>
<jsp:include page="template/footer.jsp" />