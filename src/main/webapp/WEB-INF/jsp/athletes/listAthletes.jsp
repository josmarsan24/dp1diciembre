<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
	
<petclinic:layout pageName="deportistas">
	<h2>Athletes</h2>

	<table id="athletesTable" class="table table-striped">
		<thead>
			<tr>

				<th style="width: 200px;">Nombre</th>
				<th style="width: 40px;">DNI</th>
				<th style="width: 250px;">Email</th>
				<th style="width: 40px;">Altura</th>
				<th style="width: 40px;">Peso</th>
				<th style="width: 200px;">Deporte</th>	
				<th >        </th>			

			</tr>


		</thead>
		<tbody>
			<c:forEach items="${athletes}" var="athlete">
				<tr>
					<td><spring:url value="/athletes/show/{athleteId}" var="athleteUrl">
							<spring:param name="athleteId" value="${athlete.id}" />
						</spring:url> <a href="${fn:escapeXml(athleteUrl)}"><c:out
								value="${athlete.nombre} ${athlete.apellidos}" /></a></td>
					<td><c:out value="${athlete.dni}" /></td>
					<td><c:out value="${athlete.email}" /></td>
					<td><c:out value="${athlete.height}" /></td>
					<td><c:out value="${athlete.weight}" /></td>
					<td><c:out value="${athlete.deporte.name}" /></td>
					<sec:authorize access="hasAuthority('admin')">
					<td>
                                    <spring:url value="/athletes/delete/{athleteId}" var="athleteDeleteUrl">
                        <spring:param name="athleteId" value="${athlete.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(athleteDeleteUrl)}">Delete</a>
                </td>
                </sec:authorize>
				</tr>
			</c:forEach>
			
		</tbody>

	</table>
	
	 <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/athletes/new" htmlEscape="true"/>'>Crear deportista</a>
	</sec:authorize>
</petclinic:layout>
