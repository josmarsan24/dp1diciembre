<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">

    

    <h2>Acceso Denegado</h2>

    <p>${exception.message}</p>
    
    <spring:url value="/resources/images/error.png" var="errorImage"/>
    <img src="${errorImage}"/>

</petclinic:layout>
