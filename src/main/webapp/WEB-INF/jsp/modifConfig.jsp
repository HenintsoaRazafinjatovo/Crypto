<jsp:include page="template/header.jsp" />
<h2 class="uk-h1">Formulaire</h2>
<br>
<div class="uk-card uk-card-body uk-card-default">
<form class="uk-form-stacked" action="/commissionConfig" method="post">
  <div class="mb-3">
    <label class="uk-form-label" for="form-stacked-text">Text</label>
    <div class="uk-form-controls">
      <input
        class="uk-input"
        id="form-stacked-text"
        type="number"
        placeholder="Some text..."
        name="config"
        step="0.01"
      />
    </div>
  </div>

  <div class="mb-3">
    <label class="uk-form-label" for="form-stacked-select">Select</label>
    <div class="uk-form-controls">
      <select class="uk-select" id="form-stacked-select" name="type"> 
        <option value="1">achat</option>
        <option value="2">vente</option>
      </select>
    </div>
  </div>
  <input type="submit" class="uk-btn uk-btn-primary" value="Submit" />
</form>
</div>

<jsp:include page="template/footer.jsp" />