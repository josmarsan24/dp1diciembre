<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="athlete">
<h2>
        <c:if test="${athlete['new']}">Nuevo </c:if> Deportista
    </h2>
 
    <form:form modelAttribute="athlete" class="form-horizontal" id="add-athlete-form">
        <div class="form-group has-feedback">
        	<petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Apellidos" name="apellidos"/>
             <petclinic:inputField label="DNI" name="dni"/>
             <petclinic:inputField label="Email (opcional)" name="email"/>
            <petclinic:inputField label="Altura" name="height"/>                
           	<petclinic:inputField label="Peso" name="weight"/>        
            <petclinic:selectField label="Genero" name="genero" size="1" names="${genero}"/>
            <div class="control-group">
                    <petclinic:selectField name="deporte" label="Deporte" names="${deportes}" size="1"/>
                </div>  
            <petclinic:inputField label="Usuario" name="user.username"/>
            <petclinic:inputField label="Contraseña" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${athlete['new']}">
                        <button class="btn btn-default" type="submit">Crear Deportista</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Deportista</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    
</petclinic:layout>
