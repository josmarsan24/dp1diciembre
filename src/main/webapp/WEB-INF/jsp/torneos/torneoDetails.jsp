<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="torneos">
    <h2>Torneo</h2>
    <table class="table table-striped">
      <h3><c:out value="${torneo.name}"/></h3>
             
        <tr>
            <th>Fecha de Inicio</th>
            <td><b><c:out value="${torneo.fechaInicio}"/></b></td>
        </tr>
        <tr>
            <th>Fecha de Fin</th>
            <td><b><c:out value="${torneo.fechaFin}"/></b></td>
        </tr>
        <tr>
            <th>Deporte</th>
            <td><b><c:out value="${torneo.deporte.name}"/></b></td>
        </tr>
        <tr>
            <th>Pista</th>
            <td><b><c:out value="${torneo.pista.name}"/></b></td>
        </tr>
    </table>
    <br/>
    <br/>
    <h2>Deportistas Participantes</h2>
    <br>
    <table class="table table-striped">
        <c:forEach var="athlete" items="${torneo.participantes}">
       <h2><b> 
       		<spring:url value="/athletes/show/{athleteId}" var="athleteUrl">
            <spring:param name="athleteId" value="${athlete.id}"/>
            </spring:url>
            <a href="${fn:escapeXml(athleteUrl)}"><c:out value="${athlete.nombre} ${athlete.apellidos}"/></a></b></h2>

<%--     <spring:url value="/entrenadores/{entrenadorId}/delete/{athleteId}/{user}" var="athletedelUrl"> --%>
<%--     	<spring:param name="athleteId" value="${athlete.id}"/> --%>
<%--     	<spring:param name="entrenadorId" value="${athlete.entrenador.id}"/> --%>
<%--     	 <sec:authorize access="hasAuthority('admin')"> --%>
<%-- 		<spring:param name="user" value="gy7gt87qgwowhbudvhbwkwpfk4fa545w46894wdyftwqtfvdghwvdywt76twt7tqte"/> --%>
<%-- 		</sec:authorize> --%>
<%--     	 <sec:authorize access="hasAuthority('entrenador')"> --%>
<%-- 		<spring:param name="user" value="${user}"/> --%>
<%-- 		</sec:authorize> --%>
<%--      </spring:url> --%>
<%--     <sec:authorize access="hasAuthority('admin')"> --%>
<%--   		<a href="${fn:escapeXml(athletedelUrl)}" class="btn btn-default">Eliminar</a><br/> --%>
<%--     </sec:authorize> --%>
<%--     <sec:authorize access="hasAnyAuthority('entrenador')"> --%>
<%-- 	<c:if test="${username == user}"> --%>
<%-- 	  <a href="${fn:escapeXml(athletedelUrl)}" class="btn btn-default">Eliminar</a><br/> --%>
<%-- 	  </c:if> --%>
<%-- 	</sec:authorize>                --%>

        </c:forEach>
    </table>
    <spring:url value="{torneoId}/add" var="addParticipanteUrl">
        <spring:param name="torneoId" value="${torneo.id}"/>
    </spring:url>
     <sec:authorize access="hasAuthority('admin')">
  		<a href="${fn:escapeXml(addParticipanteUrl)}" class="btn btn-default">Añadir Participante</a>
    </sec:authorize>
<%--     <sec:authentication var="user" property="name" /> --%>
<%--     <sec:authorize access="hasAnyAuthority('entrenador')"> --%>
<%-- 	<c:if test="${username == user}"> --%>
<%-- 	  <a href="${fn:escapeXml(addAthleteUrl)}" class="btn btn-default">Añadir Deportista</a> --%>
<%-- 	</c:if> --%>
<%-- 	</sec:authorize> --%>


</petclinic:layout>