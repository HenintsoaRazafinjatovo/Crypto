
<jsp:include page="template/header.jsp" />
<%@page import="mg.crypto.models.*" %>
<style>
   body {
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
        }

        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
        }

        .bilan {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
        .resultat {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        .column {
            width: 48%;
            padding: 10px;
            background: #f1f1f1;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .columnEtat {
            width: 48%;
            padding: 10px;
            background: #f1f1f1;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .columnEtat,.column h2 {
            font-size: 18px;
            margin-bottom: 10px;
            text-align: center;
            color: #444;
        }

        .columnEtat,.column ul {
            list-style-type: none;
            padding: 0;
        }

        .columnEtat,.column ul li {
            padding: 8px 10px;
            margin: 5px 0;
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        .columnEtat,.column ul li span {
            float: right;
            font-weight: bold;
            color: #555;
        }

        .footer {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
            color: #888;
        }
 </style>

<h1 class="uk-h1">Liste des transactions</h1>
<br />
<div class="uk-card uk-card-body uk-card-default">
<form class="uk-form-inline" action="/filtrerMvt" method="post">
  <div class="mb-3">
    <label class="uk-form-label">Type</label>
    <div class="uk-form-controls">
      <select name="typeComposant" class="uk-select">
        <option value="-1"> </option>
          <option value="1">Achat</option>
          <option value="2">Vente</option>
      </select>
    </div>
  </div>
  <input type="submit" class="uk-btn uk-btn-primary" value="Filtrer" />
       <div class="container">
          <div class="column" id="actif">
              <h2>fond total</h2>
              <ul>
                <li>Montant<span><%= (double) request.getAttribute("fond") %>%</span>
                </li>
              </ul>
        </div>
      </div>
</form>
<br/>
<table class="uk-table uk-table-middle uk-table-divider">
  <thead>
    <tr>
      <th>Utilisateur</th>
      <th>mouvement type</th>
      <th>montant</th>
      <th>Date</th>
    </tr>
  </thead>
  <tbody>
    <% List<MvtFond>  mvtFonds = (List<MvtFond>) request.getAttribute("lmvt"); %> 
    <% for(MvtFond mvtFond : mvtFonds)  { %>
    <tr>
      <td><%= mvtFond.getIdUser() %></td>
      <td><%= mvtFond.getTypeMvt() %></td>
      <td><%= mvtFond.getMontant() %> </td>
      <td><%= mvtFond.getDtMvt() %></td>
    </tr>
    <% } %>
  </tbody>
</table>
</div>
<jsp:include page="template/footer.jsp" />