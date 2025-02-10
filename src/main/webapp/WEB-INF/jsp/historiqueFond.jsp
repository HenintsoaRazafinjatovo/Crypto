<%@page import="mg.crypto.models.User"%>
<%@page import="mg.crypto.models.MvtFond"%>
<%@page import="java.util.List"%>



<jsp:include page="template/header.jsp" />

<% List<User> users = (List<User>)request.getAttribute("users"); %>
<% List<MvtFond> fonds = (List<MvtFond>)request.getAttribute("fonds"); %>

<h1 class="uk-h1">Analyse</h1>
<br />
<form class="uk-form-stacked" method="get" action="/frontOffice/histoFondAll">
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
      <th>Total depot</th>
      <th>Total retrait</th>
      <th>Date mouvement </th>

     
    </tr>
  </thead>
  <tbody>
    <% if(fonds != null) { 
    for(MvtFond fond : fonds) { %>
        <tr>
            <td><a href="/frontOffice/histoFondUser?id_user=<%=fond.getIdUser()%>"></a></td>

            <td><%= fond.getIdUser()%></td>
            <td><%= fond.getDepot()%></td>
            <td><%= fond.getRetrait()%></td>
            <td><%= fond.getDtMvt()%></td>   
        </tr>
    <% } }%>
  </tbody>
</table>
<jsp:include page="template/footer.jsp" />