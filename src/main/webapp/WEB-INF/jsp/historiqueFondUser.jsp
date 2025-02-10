<%@page import="mg.crypto.models.User"%>
<%@page import="mg.crypto.models.MvtFond"%>
<%@page import="java.util.List"%>

<jsp:include page="template/header.jsp" />
<% List<MvtFond> fonds = (List<MvtFond>)request.getAttribute("fonds"); %>

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
    <% 
    for(MvtFond fond : fonds) { %>
        <tr>
            <td><a href="/frontOffice/histoFondByUser?idUser=<%=fond.getIdUser()%>"></a></td>

            <td><%= fond.getIdUser()%></td>
            <td><%= fond.getDepot()%></td>
            <td><%= fond.getRetrait()%></td>
            <td><%= fond.getDtMvt()%></td>   
        </tr>
    <%  }%>

  </tbody>
</table>
<jsp:include page="template/footer.jsp" />