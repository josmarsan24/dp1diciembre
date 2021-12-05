<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="torneos">

<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fechaInicio").datepicker({dateFormat: 'yy/mm/dd'});
                $("#fechaFin").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    
    <jsp:body>
        <h2>Torneo</h2>
        

        <form:form modelAttribute="torneo" class="form-horizontal">
            <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="name"/>
               <petclinic:inputField label="Fecha inicio" name="fechaInicio"/>
               <petclinic:inputField label="Fecha fin" name="fechaFin"/>
            </div>
            <div class="control-group">
                    <petclinic:selectField name="pista" label="Pista" names="${pistas}" size="1"/>
                </div> 
             <h2><c:if test="${edit != true}">
				<div class="control-group">
                    <petclinic:selectField name="deporte" label="Deporte" names="${deportes}" size="1"/>
                </div>  
                </c:if>     </h2>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${torneo.id}"/>
                    <button class="btn btn-default" type="submit">Guardar torneo</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>
