<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">

    <h2>Elija su tipo de usuario</h2>


<a class="btn btn-default" href='<spring:url value="/users/newEntrenador" htmlEscape="true"/>'>ENTRENADOR</a>
<br><br>
<a class="btn btn-default" href='<spring:url value="/users/newDeportista" htmlEscape="true"/>'>DEPORTISTA</a>
</petclinic:layout>
