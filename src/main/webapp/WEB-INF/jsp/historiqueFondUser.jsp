<jsp:include page="template/header.jsp" />
<table class="uk-table uk-table-middle uk-table-divider">
  <thead>
    <tr>
      <th class="uk-width-small"></th>
      <th>id_user</th>
      <th>Total depot</th>
      <th>Total retrait</th>
      <th>Date mouvement </th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="analyse" items="${fonds}">
        <tr>
            <td>${analyse.idUser}</td>
            <td>${analyse.depot}</td>
            <td>${analyse.retrait}</td>
            <td>${analyse.datemvt}</td>    
        </tr>
    </c:forEach>
  </tbody>
</table>
<jsp:include page="template/footer.jsp" />