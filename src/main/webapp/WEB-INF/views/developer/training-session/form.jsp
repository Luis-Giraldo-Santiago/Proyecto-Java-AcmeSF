<%--
- form.jsp
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.TrainingSession.form.label.code" path="code"/>
	<acme:input-moment code="developer.TrainingSession.form.label.start" path="start"/>
	<acme:input-moment code="developer.TrainingSession.form.label.finish" path="finish"/>
	<acme:input-textbox code="developer.TrainingSession.form.label.location" path="location"/>
	<acme:input-textbox code="developer.TrainingSession.form.label.instructor" path="instructor"/>
	<acme:input-textbox code="developer.TrainingSession.form.label.contactEmail" path="contactEmail"/>
	<acme:input-textbox code="developer.TrainingSession.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && published == false}">
			<acme:submit code="developer.TrainingSession.form.button.update" action="/developer/training-session/update"/>
			<acme:submit code="developer.TrainingSession.form.button.delete" action="/developer/training-session/delete"/>
			<acme:submit code="developer.TrainingSession.form.button.publish" action="/developer/training-session/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.TrainingSession.form.button.create" action="/developer/training-session/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>

