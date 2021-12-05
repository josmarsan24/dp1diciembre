<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="patrocinadores">
	<jsp:body>		
	
	
    <h2>Lista de patrocinadores</h2>

    <table id="patrocinadoresTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Nombre</th>
			<th style="width: 400px;">Tipo</th>
			<th style="width: 250px;">Twitter</th>
			<th style="width: 200px;">Instagram</th>			
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${patrocinadores}" var="patrocinadores">
            <tr>
                <td>
           			<c:out value="${patrocinadores.name}"/>
                </td>
               <td><c:out value="${patrocinadores.tipo}" /></td>
					<td><c:out value="${patrocinadores.twitter}" /></td>
					<td><c:out value="${patrocinadores.instagram}" /></td>
                <td>
                    <spring:url value="/athletes/show/{athleteId}/addPatrocinador/{patrocinadorId}" var="patroAddUrl">
                        <spring:param name="athleteId" value="${athlete.id}"/>
                        <spring:param name="patrocinadorId" value="${patrocinadores.id}"/>
                        
                    </spring:url>
                    <a href="${fn:escapeXml(patroAddUrl)}">Añadir</a>
                </td>             
            </tr>
        </c:forEach>
        </tbody>
         <sec:authorize access="hasAuthority('admin')">
	</sec:authorize>
    </table>
    </jsp:body>		
</petclinic:layout>