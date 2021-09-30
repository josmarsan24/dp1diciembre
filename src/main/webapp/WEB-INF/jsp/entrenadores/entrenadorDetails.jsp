<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="entrenadores">
    <h2>Entrenador</h2>
    <table class="table table-striped">
      <h3><c:out value="${entrenador.nombre} ${entrenador.apellidos}"/></h3>
             
        <tr>
            <th>DNI</th>
            <td><b><c:out value="${entrenador.dni}"/></b></td>
        </tr>
        <tr>
            <th>Email</th>
            <td><b><c:out value="${entrenador.email}"/></b></td>
        </tr>
        <tr>
            <th>Deporte</th>
            <td><b><c:out value="${entrenador.deporte.name}"/></b></td>
        </tr>
    </table>
 
    <spring:url value="{entrenadorId}/edit" var="editUrl">
        <spring:param name="entrenadorId" value="${entrenador.id}"/>
    </spring:url>
    
    <spring:url value="{entrenadorId}/athletes/new" var="addUrl">
        <spring:param name="entrenadorId" value="${entrenador.id}"/>
    </spring:url>
    <sec:authorize access="hasAuthority('admin')">
  	<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Crear y añadir deportista</a>
  	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Entrenador</a>
    </sec:authorize>
     <sec:authentication var="user" property="name" />
    <sec:authorize access="hasAnyAuthority('entrenador')">
	<c:if test="${username == user}">
	  <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Crear y añadir deportista</a>
	  <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar mis datos</a>
	</c:if>
	</sec:authorize>
    
	
    <br/>
    <br/>
    <br/>
    <h2>Deportistas entrenados</h2>
    <table class="table table-striped">
        <c:forEach var="athlete" items="${entrenador.athletes}">
       <h2><b> 
       		<spring:url value="/athletes/show/{athleteId}" var="athleteUrl">
            <spring:param name="athleteId" value="${athlete.id}"/>
            </spring:url>
            <a href="${fn:escapeXml(athleteUrl)}"><c:out value="${athlete.nombre} ${athlete.apellidos}"/></a></b></h2>


    <table class="table table-striped">
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
    <spring:url value="/entrenadores/{entrenadorId}/delete/{athleteId}/{user}" var="athletedelUrl">
    	<spring:param name="athleteId" value="${athlete.id}"/>
    	<spring:param name="entrenadorId" value="${athlete.entrenador.id}"/>
    	 <sec:authorize access="hasAuthority('admin')">
		<spring:param name="user" value="gy7gt87qgwowhbudvhbwkwpfk4fa545w46894wdyftwqtfvdghwvdywt76twt7tqte"/>
		</sec:authorize>
    	 <sec:authorize access="hasAuthority('entrenador')">
		<spring:param name="user" value="${user}"/>
		</sec:authorize>
    </spring:url>
    <sec:authorize access="hasAuthority('admin')">
  		<a href="${fn:escapeXml(athletedelUrl)}" class="btn btn-default">Eliminar</a><br/>
    </sec:authorize>
    <sec:authorize access="hasAnyAuthority('entrenador')">
	<c:if test="${username == user}">
	  <a href="${fn:escapeXml(athletedelUrl)}" class="btn btn-default">Eliminar</a><br/>
	  </c:if>
	</sec:authorize>               
    <br/>
    <h2>Torneos en los que <c:out value ="${athlete.nombre}"/> participa:</h2>	
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
    <br><br>
        </c:forEach>
    </table>
    <spring:url value="{entrenadorId}/add" var="addAthleteUrl">
        <spring:param name="entrenadorId" value="${entrenador.id}"/>
    </spring:url>
     <sec:authorize access="hasAuthority('admin')">
  		<a href="${fn:escapeXml(addAthleteUrl)}" class="btn btn-default">Añadir Deportista</a>
    </sec:authorize>
    <sec:authentication var="user" property="name" />
    <sec:authorize access="hasAnyAuthority('entrenador')">
	<c:if test="${username == user}">
	  <a href="${fn:escapeXml(addAthleteUrl)}" class="btn btn-default">Añadir Deportista</a>
	</c:if>
	</sec:authorize>


</petclinic:layout>