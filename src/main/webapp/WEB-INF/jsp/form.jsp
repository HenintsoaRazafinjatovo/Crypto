<jsp:include page="template/header.jsp" />
<h2 class="uk-h1">Formulaire</h2>
<br>
<div class="uk-card uk-card-body uk-card-default">
<form class="uk-form-stacked">
  <div class="mb-3">
    <label class="uk-form-label" for="form-stacked-text">Text</label>
    <div class="uk-form-controls">
      <input
        class="uk-input"
        id="form-stacked-text"
        type="text"
        placeholder="Some text..."
      />
    </div>
  </div>

  <div class="mb-3">
    <label class="uk-form-label" for="form-stacked-select">Select</label>
    <div class="uk-form-controls">
      <select class="uk-select" id="form-stacked-select">
        <option>Option 01</option>
        <option>Option 02</option>
      </select>
    </div>
  </div>

  <div class="mb-3">
    <div class="uk-form-label">Radio</div>
    <div class="uk-form-controls">
      <label
        ><input class="uk-radio" type="radio" name="radio1" /> Option 01</label
      ><br />
      <label
        ><input class="uk-radio" type="radio" name="radio1" /> Option 02</label
      >
    </div>
  </div>
  <input type="submit" class="uk-btn uk-btn-primary" value="Submit" />
</form>
</div>

<jsp:include page="template/footer.jsp" />