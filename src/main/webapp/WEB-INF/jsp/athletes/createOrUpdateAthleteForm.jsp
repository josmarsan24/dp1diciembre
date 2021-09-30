<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<petclinic:layout pageName="deportistas">

	<jsp:body>
	<sec:authorize access="hasAnyAuthority('deportista')">
	<sec:authentication var="user" property="name" />
	<c:if test="${!athlete['new']}">
	<c:if test="${username != user}">
	<script type="text/javascript">
    window.location.href = "http://localhost:8081/";
	</script>
	</c:if>
	</c:if>
	</sec:authorize>
	
	<sec:authorize access="hasAnyAuthority('entrenador')">
	<sec:authentication var="user" property="name" />
	<c:if test="${athlete['new']}">
	<c:if test="${username != user}">
	<script type="text/javascript">
    window.location.href = "http://localhost:8081/";
	</script>
	</c:if>
	</c:if>
	</sec:authorize>
	<sec:authentication var="user" property="name" />
		<c:if test="${!athlete['new']}"><h1>Actualizar Deportista </h1>
		
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Nombre</th>
                <th>Apellidos</th>
                <th>DNI</th>
                <th>Email</th>
                <th>Altura</th>
                <th>Peso</th>
                <th>Genero</th>
                <th>Deporte</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${athlete.nombre}"/></td>
                <td><c:out value="${athlete.apellidos}"></c:out></td>
                <td><c:out value="${athlete.dni}"/></td>
                <td><c:out value="${athlete.email}"/></td>
                <td><c:out value="${athlete.height}"/></td>
                <td><c:out value="${athlete.weight}"/></td>
                <td><c:out value="${athlete.genero}"/></td>
                <td><c:out value="${athlete.deporte.name}"></c:out></td>                            
            </tr>
        </table></c:if>
        
        <h1><c:if test="${athlete['new']}">Nuevo Deportista </c:if></h1>
        
         <form:form modelAttribute="athlete" class="form-horizontal">
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Nombre" name="nombre"/>
            	<petclinic:inputField label="Apellidos" name="apellidos"/>
             	<petclinic:inputField label="DNI" name="dni"/>
             	<petclinic:inputField label="Email (opcional)" name="email"/>
                <petclinic:inputField label="Altura" name="height"/>                
              	<petclinic:inputField label="Peso" name="weight"/>        
                <petclinic:selectField label="Genero" name="genero" size="1" names="${genero}"/>
				<h2><c:if test="${edit != true}">
				<div class="control-group">
                    <petclinic:selectField name="deporte" label="Deporte" names="${deportes}" size="1"/>
                </div>       
         		</c:if></h2>
                
                
            	
            </div>
            
            <c:if test="${athlete['new']}">
			<div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="athleteId" value="${athlete.id}"/>
                    <button class="btn btn-default" type="submit">Crear deportista</button>
                </div>
            </div>
			</c:if>
			<c:if test="${!athlete['new']}">
			<div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${athlete.id}"/>
                    <button class="btn btn-default" type="submit">Actualizar deportista</button>
                </div>
            </div>
			</c:if>
			
			
			
        </form:form>
    </jsp:body>
    
</petclinic:layout>

