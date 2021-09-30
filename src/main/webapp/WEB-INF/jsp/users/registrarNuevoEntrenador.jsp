<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="entrenadores">
    <h2>
        <c:if test="${entrenador['new']}">Nuevo </c:if> Entrenador
    </h2>
 
    <form:form modelAttribute="entrenador" class="form-horizontal" id="add-entrenador-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Apellidos" name="apellidos"/>
             <petclinic:inputField label="DNI" name="dni"/>
              <petclinic:inputField label="Email (opcional)" name="email"/>
              <div class="control-group">
                    <petclinic:selectField name="deporte" label="Deporte" names="${deportes}" size="1"/>
                </div>  
            <petclinic:inputField label="Usuario" name="user.username"/>
            <petclinic:inputField label="Contraseña" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${entrenador['new']}">
                        <button class="btn btn-default" type="submit">Crear Entrenador</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Entrenador</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>