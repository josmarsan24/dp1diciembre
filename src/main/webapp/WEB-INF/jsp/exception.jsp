<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">

    <spring:url value="/resources/images/pets.png" var="petsImage"/>
    <img src="${petsImage}"/>

    <h2>Lo sentimos usted no tiene permiso para acceder a este recurso</h2>

    <p>${exception.message}</p>
    
    <spring:url value="/resources/images/error.png" var="errorImage"/>
    <img src="${errorImage}"/>

</petclinic:layout>
