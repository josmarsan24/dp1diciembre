<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="deportistas">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fechaFin").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            <c:if test="${sancion['new']}">Nueva </c:if> Sancion
        </h2>
        <form:form modelAttribute="sancion" class="form-horizontal">
            <input type="hidden" name="id" value="${sancion.id}"/>
            <div class="form-group has-feedback">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Deportista</label>
                    <c:out value="${sancion.athlete.nombre} ${sancion.athlete.apellidos}"/>
                    <div class="col-sm-10">
                    </div>
                </div>
                <petclinic:inputField label="Descripción" name="descripcion"/>
                <petclinic:inputField label="Fecha fin de sanción" name="fechaFin"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                 	<button class="btn btn-default" type="submit">Añadir Sanción</button>
                </div>
            </div>
        </form:form>
        <c:if test="${!sancion['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>
