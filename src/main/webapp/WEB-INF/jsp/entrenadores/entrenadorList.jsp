<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<petclinic:layout pageName="entrenadores">
    <h2>Entrenadores</h2>

    <table id="entrenadoresTable" class="table table-striped">
        <thead>
        <tr>

				<th style="width: 200px;">Nombre</th>
				<th style="width: 40px;">DNI</th>
				<th style="width: 250px;">Email</th>
				<th style="width: 200px;">Deporte</th>
				<th >Actions</th>
			</tr>
        </thead>
        <tbody>
        <c:forEach items="${entrenadores}" var="entrenador">
      
            <tr>
                
                <td>
                     <spring:url value="/entrenadores/{entrenadorId}" var="entrenadorUrl">
                        <spring:param name="entrenadorId" value="${entrenador.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(entrenadorUrl)}"><c:out value="${entrenador.nombre} ${entrenador.apellidos}"/></a>
                </td>
               <td><c:out value="${entrenador.dni}" /></td>
					<td><c:out value="${entrenador.email}" /></td>
					<td><c:out value="${entrenador.deporte.name}" /></td>
					<sec:authorize access="hasAuthority('admin')">
					<td>
                                    <spring:url value="/entrenadores/delete/{entrenadorId}" var="entrenadorDeleteUrl">
                        <spring:param name="entrenadorId" value="${entrenador.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(entrenadorDeleteUrl)}">Delete</a>
                </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/entrenador/new" htmlEscape="true"/>'>Crear entrenador</a>
	</sec:authorize>
	
	 
</petclinic:layout>