<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="entrenadores">
	<jsp:body>		
	<sec:authorize access="hasAnyAuthority('entrenador')">
	<sec:authentication var="user" property="name" />
	<c:if test="${username != user}">
		<script type="text/javascript">
   			 window.location.href = "http://localhost:8081/";
	</script>
	</c:if>
	</sec:authorize>
	
    <h2>Deportistas sin entrenador y con el mismo deporte que <c:out value="${entrenador.nombre}" />&nbsp<c:out value="${entrenador.apellidos}"/></h2>

    <table id="athletesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Nombre</th>
			<th style="width: 40px;">DNI</th>
			<th style="width: 250px;">Email</th>
			<th style="width: 40px;">Altura</th>
			<th style="width: 40px;">Peso</th>
			<th style="width: 200px;">Deporte</th>			
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${atletas}" var="atletas">
            <tr>
                <td>
                    <spring:url value="/athletes/show/{athleteId}" var="athleteUrl">
                        <spring:param name="athleteId" value="${atletas.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(athleteUrl)}"><c:out value="${atletas.nombre} ${atletas.apellidos}"/></a>
                </td>
               <td><c:out value="${atletas.dni}" /></td>
					<td><c:out value="${atletas.email}" /></td>
					<td><c:out value="${atletas.height}" /></td>
					<td><c:out value="${atletas.weight}" /></td>
					<td><c:out value="${atletas.deporte.name}" /></td>
                    
                <td>
                    <spring:url value="/entrenadores/{entrenadorId}/add/{athleteId}" var="athletedelUrl">
                        <spring:param name="athleteId" value="${atletas.id}"/>
                        <spring:param name="entrenadorId" value="${entrenador.id}"/>
                        
                    </spring:url>
                    <a href="${fn:escapeXml(athletedelUrl)}">Añadir</a>
                </td>             
            </tr>
        </c:forEach>
        </tbody>
         <sec:authorize access="hasAuthority('admin')">
	</sec:authorize>
    </table>
    </jsp:body>		
</petclinic:layout>