<jsp:include page="template/header.jsp" />
<h2 class="uk-h1">Vendre</h2>
<br>
<div class="uk-card uk-card-body uk-card-default">
<form class="uk-form-stacked">
  <div class="mb-3">
    <label class="uk-form-label" for="form-stacked-text">Montant</label>
    <div class="uk-form-controls">
      <input
        class="uk-input"
        id="form-stacked-text"
        type="number"
        placeholder="Some text..."
      />
    </div>
  </div>

  <div class="mb-3">
    <label class="uk-form-label" for="form-stacked-select">Date</label>
    <div class="uk-form-controls">
      <input
        class="uk-input"
        id="form-stacked-text"
        type="date"
        placeholder="Some text..."
      />
    </div>
  </div>
  <input type="submit" class="uk-btn uk-btn-primary" value="Valider" />
</form>
</div>

<jsp:include page="template/footer.jsp" />