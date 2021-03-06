<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/views/layout/entete.jsp">
	<jsp:param name="title" value="Enregistrer Ingredient"/>
</jsp:include>

<jsp:include page="../layout/navbar.jsp"/>

<div class="container">
	<div class="row">
		<div class="col-md-9">

			<h1>Nouvel Ingredient</h1>
			<br>
			<c:if test="${msg != null}">
				<div class="alert alert-danger" role="alert">${msg}</div>
			</c:if>
			
			<form method="post">

				<label for="nom">Nom :</label>
				<input style="border-color:${ erreur [0] }" class="form-control" id="nom" name="nom" type="text" placeholder=" ex : tomate..." value="${ nom }">

					<label for="quantite">Quantité :</label>
					<input style="border-color:${ erreur [1] }" class="form-control" id="quantite" name="quantite" type="number" min="1" placeholder="ex : 3" value="${ quantite }">

						<label for="prix">Prix :</label>
						<input style="border-color:${ erreur [2] }" class="form-control" id="prix" name="prix" type="number" min="0.01" step="0.01" placeholder="ex : 0,35" value="${ prix }">

							<br>
								<button class="btn btn-success" type="submit">Valider</button>

								<a href='./liste'>
									<button type="button" class="btn btn-primary">Retour</button>
								</a>
							</form>

						</div>

						<jsp:include page="../layout/footer.jsp"/>
