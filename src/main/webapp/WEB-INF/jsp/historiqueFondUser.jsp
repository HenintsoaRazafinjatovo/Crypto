<jsp:include page="template/header.jsp" />
<table class="uk-table uk-table-middle uk-table-divider">
  <thead>
    <tr>
      <th class="uk-width-small">id_user</th>
      <th>Total depot</th>
      <th>Total retrait</th>   
    </tr>
  </thead>
  <tbody>
    <c:forEach var="analyse" items="${fonds}">
        <tr>
            <td></td>
            <td></td>
            <td></td>   
        </tr>
    </c:forEach>
  </tbody>
</table>
<jsp:include page="template/footer.jsp" />