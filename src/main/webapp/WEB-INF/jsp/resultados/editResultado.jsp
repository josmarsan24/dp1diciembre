<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<petclinic:layout pageName="resultado">

	<jsp:body>
        <h2>Resultado</h2>

	<spring:url value="/torneos/show/{torneoId}/{athleteId}/resultado/new" var="addResultadoUrl">
                        <spring:param name="athleteId" value="${athlete.id}"/>
                        <spring:param name="torneoId" value="${torneo.id}"/>
                        </spring:url>

        <form:form modelAttribute="resultado" class="form-horizontal"
			action="${fn:escapeXml(addResultadoUrl)}">
            <div class="form-group has-feedback">
            <petclinic:inputField label="Posicion" name="posicion" />
                
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${resultado.id}" />
                    <button class="btn btn-default" type="submit">Guardar</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>
