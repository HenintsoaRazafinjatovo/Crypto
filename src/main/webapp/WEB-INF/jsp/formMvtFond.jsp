<jsp:include page="template/header.jsp" />
<h2 class="uk-h1">Vendre</h2>
<br>
<div class="uk-card uk-card-body uk-card-default">
<form class="uk-form-stacked" method="post">
  <div class="mb-3">
    <label class="uk-form-label" for="form-stacked-text">Montant</label>
    <div class="uk-form-controls">
      <select
        class="uk-input"
        id="typemvt"
        name="typemvt"
        type="number"
      >
        <option value="1">depot</option>
        <option value="2">retrait</option>
    </select>

    </div>
  </div>

  <div class="mb-3">
     <input
        class="uk-input"
        id="montantmvt"
        name="montantmvt"
        type="number"
        placeholder="montant">
  </div>

    <div class="mb-3">
        <label class="uk-form-label" for="form-stacked-select">Date</label>
        <div class="uk-form-controls">
        <label for="datemvt">Date and Time:</label>
        <input class="uk-input" type="datetime-local" id="datemvt" name="datemvt" required>
          <br>
    </div>

  <input type="submit" class="uk-btn uk-btn-primary" value="Valider" />
</form>
</div>

<jsp:include page="template/footer.jsp" />