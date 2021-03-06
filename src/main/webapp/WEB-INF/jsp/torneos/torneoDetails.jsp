<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="torneos">
	<h2>Torneo</h2>
	
		<h3>
			<c:out value="${torneo.name}" />
		</h3>
		<table class="table table-striped">
		<tr>
			<th>Fecha de Inicio</th>
			<td><b><c:out value="${torneo.fechaInicio}" /></b></td>
		</tr>
		<tr>
			<th>Fecha de Fin</th>
			<td><b><c:out value="${torneo.fechaFin}" /></b></td>
		</tr>
		<tr>
			<th>Deporte</th>
			<td><b><c:out value="${torneo.deporte.name}" /></b></td>
		</tr>
		<tr>
			<th>Pista</th>
			<td><b><c:out value="${torneo.pista.name}" /></b></td>
		</tr>
	</table>
	<br />
	<br />
	
	<h2>Deportistas Participantes</h2>
	<br>
	<table class="table table-striped">
		<c:forEach var="athlete" items="${torneo.participantes}">
			<h2>
				<b> <spring:url value="/athletes/show/{athleteId}"
						var="athleteUrl">
						<spring:param name="athleteId" value="${athlete.id}" />
					</spring:url> <a href="${fn:escapeXml(athleteUrl)}"><c:out
							value="${athlete.nombre} ${athlete.apellidos}" /></a></b>
			</h2>
		</c:forEach>
		</table>
		
		<table class="table table-striped">
			<sec:authorize access="hasAuthority('admin')">
				<h2>Deportistas sin resultado</h2>
				<c:forEach var="athlete" items="${atheltesWithoutResult}">

					<tr>
						<td><b> <c:out
									value="${athlete.nombre} ${athlete.apellidos}" /></b> 
									<spring:url
								value="/torneos/show/{torneoId}/{athleteId}/resultado/new"
								var="addResultadoUrl">
								<spring:param name="athleteId" value="${athlete.id}" />
								<spring:param name="torneoId" value="${torneo.id}" />
							</spring:url> <a href="${fn:escapeXml(addResultadoUrl)}"
							class="btn btn-default">A?adir Resultado</a></td>
					</tr>


				</c:forEach>
			</sec:authorize>
		</table>
		<h2>Resultados</h2>
		<table class="table table-striped">
			<c:forEach var="resultado" items="${torneo.resultados}">
				<tr>
					<td><b><c:out
								value="${resultado.atleta.nombre} ${resultado.atleta.apellidos} ${resultado.posicion}" /></b></td>
				</tr>
			</c:forEach>


		</table>
		<spring:url value="{torneoId}/add" var="addParticipanteUrl">
			<spring:param name="torneoId" value="${torneo.id}" />
		</spring:url>
		<sec:authorize access="hasAuthority('admin')">
			<a href="${fn:escapeXml(addParticipanteUrl)}" class="btn btn-default">A?adir
				Participante</a>
		</sec:authorize>
		
</petclinic:layout>