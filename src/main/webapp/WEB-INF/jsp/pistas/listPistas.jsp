<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="pistas">
    <h2>Pistas</h2>

    <table id="pistasTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 350px;">Nombre</th>
            <th>Ciudad</th>
            <th>Aforo</th>
            <th>Deporte</th>
            <th>       </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pistas}" var="pista">
            <tr>
                <td>
                    <c:out value="${pista.name}"/>
                </td>
                <td>
                    <c:out value="${pista.ciudad}"/>
                </td>
                <td>
                    <c:out value="${pista.aforo}"/>
                </td>
                 <td>
                    <c:out value="${pista.deporte}"/>
                </td>
                 <td>
                 <sec:authorize access="hasAuthority('admin')">
                                    <spring:url value="/pistas/delete/{pistaId}" var="pistaUrl">
                        <spring:param name="pistaId" value="${pista.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(pistaUrl)}">Delete</a>
                    </sec:authorize>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/pistas/new" htmlEscape="true"/>'>Añadir pista</a>
	</sec:authorize>
</petclinic:layout>