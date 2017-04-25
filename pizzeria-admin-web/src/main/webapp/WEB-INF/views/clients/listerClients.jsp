<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../layout/entete.jsp">
	<jsp:param name="title" value="Liste des Desserts" />
</jsp:include>

<jsp:include page="../layout/navbar.jsp" />

<div class="container">

	<h1>Liste des clients</h1>
	<a class="btn btn-primary" href="new">Nouveau client</a> <br>
	<c:if test="${msg != null}">
		<div class="alert alert-danger" role="alert">${msg}</div>
	</c:if>

	<table class="table">
		<tr>
			<td>Informations</td>
			<td></td>
			<td></td>
		</tr>

		<c:forEach var="client" items="${clients}">
			<tr>
				<td>
					<div class="row">
						<div class="col-md-6">
							Ref. ${client.id}<br>
							Nom: ${client.nom}<br>
							prenom: ${client.prenom}<br>
							Email: ${client.email}<br>
							Adresse: ${client.adresse}<br>
							Mot de passe crypté: ${client.motDePasse}
						</div>
						<div class="col-md-6">
							<a href="<c:url value="/clients/edit?id=${client.id}"/>"
								class="btn btn-primary">Editer</a> <br>
							<form method="POST"
								action="<c:url value='/clients/delete?id=${client.id}'/>">
								<button type="submit" class="btn btn-danger">Supprimer</button>
							</form>
						</div>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>

<jsp:include page="../layout/footer.html" />