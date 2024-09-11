<%--
- form.jsp
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="client.ProgressLog.form.label.recordId" path="recordId"/>
	<acme:input-double code="client.ProgressLog.form.label.completeness" path="completeness"/>
	<acme:input-textarea code="client.ProgressLog.form.label.comment" path="comment"/>
	<jstl:choose>
		<jstl:when test="${_command == 'show' && published == true}">
			<acme:input-moment code="client.ProgressLog.form.label.registrationMoment" path="registrationMoment"/>
			</jstl:when>
	</jstl:choose>
	<acme:input-textbox code="client.ProgressLog.form.label.responsiblePerson" path="responsiblePerson"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && published == false}">
			<acme:submit code="client.ProgressLog.form.button.update" action="/client/progress-log/update"/>
			<acme:submit code="client.ProgressLog.form.button.delete" action="/client/progress-log/delete"/>
			<acme:submit code="client.ProgressLog.form.button.publish" action="/client/progress-log/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="client.ProgressLog.form.button.create" action="/client/progress-log/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>

