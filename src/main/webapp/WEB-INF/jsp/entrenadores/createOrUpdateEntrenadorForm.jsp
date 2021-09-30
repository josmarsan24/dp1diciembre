<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="entrenadores">
<jsp:body>		
	<c:if test="${!entrenador['new']}">
	<sec:authorize access="hasAnyAuthority('entrenador')">
	<sec:authentication var="user" property="name" />
	<c:if test="${username != user}">
		<script type="text/javascript">
   			 window.location.href = "http://localhost:8081/";
	</script>
	</c:if>
	</sec:authorize>
	</c:if>
    <h2>
        <c:if test="${entrenador['new']}">Nuevo </c:if> Entrenador
    </h2>
    <form:form modelAttribute="entrenador" class="form-horizontal" id="add-entrenador-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Apellidos" name="apellidos"/>
            <petclinic:inputField label="DNI" name="dni"/>
            <petclinic:inputField label="Email (opcional)" name="email"/>
           <<h2><c:if test="${edit != true}">
				<div class="control-group">
                    <petclinic:selectField name="deporte" label="Deporte" names="${deportes}" size="1"/>
                </div>       
         		</c:if></h2>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${entrenador['new']}">
                    	<input type="hidden" name="entrenadorId" value="${entrenador.id}"/>
                        <button class="btn btn-default" type="submit">Crear Entrenador</button>
                    </c:when>
                    <c:otherwise>
                    	<input type="hidden" name="entrenadorId" value="${entrenador.id}"/>
                        <button class="btn btn-default" type="submit">Actualizar Entrenador</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
        </jsp:body>
    
</petclinic:layout>