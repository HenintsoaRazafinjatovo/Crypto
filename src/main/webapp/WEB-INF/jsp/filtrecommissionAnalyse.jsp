<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<jsp:include page="template/header.jsp" />
<%@page import="mg.crypto.models.*" %>
<h2 class="uk-h1">Formulaire</h2>
<br>
<div class="uk-card uk-card-body uk-card-default">
<form action="/commissionAnalyseResult" class="uk-form-stacked" method="post">
    <div class="mb-3">
    <label class="uk-form-label" for="form-stacked-text">date max</label>
    <div class="uk-form-controls">
      <input
        class="uk-input"
        id="form-stacked-text"
        type="datetime-local"
        placeholder="date max"
        name="dateFin"
      />
    </div>
  </div>

    <div class="mb-3">
    <label class="uk-form-label" for="form-stacked-text">date min</label>
    <div class="uk-form-controls">
      <input
        class="uk-input"
        id="form-stacked-text"
        type="datetime-local"
        placeholder="date min"
        name="dateDebut"
      />
    </div>
  </div>

  <div class="mb-3">
    <label class="uk-form-label" for="form-stacked-select">Select</label>
    <div class="uk-form-controls">
      <select class="uk-select" id="form-stacked-select" name="analysisType">
      <option value="max">somme</option>
      <option value="moyenne">moyenne</option>
      </select>
    </div>
  </div>

  <div class="mb-3">
    <div class="uk-form-label">Radio</div>
    <div class="uk-form-controls">
        <label>
          <input class="uk-checkbox" type="checkbox" id="selectAll" onclick="toggleCheckboxes(this)" />
          Select All
        </label>
    <br />
        <br />
     <% List<Crypto>  cryptos = (List<Crypto>) request.getAttribute("cryptos"); %> 
        <% for(Crypto crypto : cryptos)  { %>
      
            <input class="uk-checkbox crypto-checkbox" type="checkbox" name="cryptos" value="<%= crypto.getIdCrypto() %>" /> <%= crypto.getNomCrypto() %>
        </label>
        <br />
        
        <% } %>
    </div>
  </div>
  <input type="submit" class="uk-btn uk-btn-primary" value="Submit" />
</form>
</div>
<script>
  function toggleCheckboxes(selectAllCheckbox) {
    const checkboxes = document.querySelectorAll('.crypto-checkbox');
    checkboxes.forEach(checkbox => {
      checkbox.checked = selectAllCheckbox.checked;
    });
  }
</script>
<jsp:include page="template/footer.jsp" />