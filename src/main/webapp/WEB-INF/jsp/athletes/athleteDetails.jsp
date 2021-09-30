<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="deportistas">
 	<h2>Deportista</h2>
    <h3><b><c:out value="${athlete.nombre} ${athlete.apellidos}"/></b></h3>


     <table class="table table-striped">
      	<tr>
            <th>DNI</th>
            <td><c:out value="${athlete.dni}"/></td>
        </tr>
         <tr>
            <th>Email</th>
            <td><c:out value="${athlete.email}"/></td>
        </tr>
        <tr>
            <th>Altura</th>
            <td><c:out value="${athlete.height}"/></td>
        </tr>
        <tr>
            <th>Peso</th>
            <td><c:out value="${athlete.weight}"/></td>
        </tr>
        <tr>
            <th>Genero</th>
            <td><c:out value="${athlete.genero}"/></td>
        </tr>
        <tr>
            <th>Deporte</th>
            <td><c:out value="${athlete.deporte.name}"/></td>
        </tr>
    </table>
      <sec:authentication var="user" property="name" />
  
    <spring:url value="{athleteId}/edit" var="editUrl">
        <spring:param name="athleteId" value="${athlete.id}"/>
    </spring:url>
    <sec:authorize access="hasAnyAuthority('admin')">
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Deportista</a>
  	<br>
  </sec:authorize>    
  <sec:authorize access="hasAnyAuthority('deportista')">
  	<c:if test="${username == user}">
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar mis Datos</a>
    </c:if>
  </sec:authorize>    
    <br/>
    <br/>
   
    <h2>Sanciones:</h2>

    <table class="table table-striped">
        <c:forEach var="sancion" items="${athlete.sanciones}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Fecha de finalizacion</dt>
                        <dd><petclinic:localDate date="${sancion.fechaFin}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Descripcion</dt>
                        <dd><c:out value="${sancion.descripcion}"/></dd>
                        </dl>
                        <sec:authorize access="hasAuthority('admin')">
                        <spring:url value="/athletes/show/{athleteId}/delete/{sancionId}" var="deleteSancionUrl">
       						<spring:param name="athleteId" value="${athlete.id}"/>
       						<spring:param name="sancionId" value="${sancion.id}"/>
  						</spring:url>
    					<a href="${fn:escapeXml(deleteSancionUrl)}" class="btn btn-default">Eliminar</a>
                    </sec:authorize>    
                </td>
		</c:forEach>
    </table>
     <sec:authorize access="hasAuthority('admin')">
     <spring:url value="{athleteId}/newSancion" var="newSancionUrl">
        <spring:param name="athleteId" value="${athlete.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(newSancionUrl)}" class="btn btn-default">Sancionar</a>
	</sec:authorize>
	<br>
	<br>
	<br>
	<h2>Torneos:</h2>

    <table class="table table-striped">
        <c:forEach var="torneo" items="${athlete.torneos}">
            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                    	<dt>Nombre</dt>
                        <dd><spring:url value="/torneos/show/{torneoId}"
							var="torneoUrl">
							<spring:param name="torneoId" value="${torneo.id}" />
						</spring:url> <a href="${fn:escapeXml(torneoUrl)}"><c:out value = "${torneo.name}"/></a></dd>
                        <dt>Fecha de comienzo</dt>
                         <dd><petclinic:localDate date="${torneo.fechaInicio}" pattern="yyyy-MM-dd"/></dd>
                       </dl>
                </td>
		</c:forEach>
    </table>  
</petclinic:layout>
