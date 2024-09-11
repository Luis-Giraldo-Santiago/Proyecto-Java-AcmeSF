<%--
- list.jsp
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="client.ProgressLog.list.label.recordId" path="recordId" width="20%"/>
	<acme:list-column code="client.ProgressLog.list.label.completeness" path="completeness" width="20%"/>
	<acme:list-column code="client.ProgressLog.list.label.comment" path="comment" width="20%"/>
	<acme:list-column code="client.ProgressLog.list.label.registrationMoment" path="registrationMoment" width="20%"/>
	<acme:list-column code="client.ProgressLog.list.label.responsiblePerson" path="responsiblePerson" width="20%"/>
</acme:list>

<jstl:if test="${contractPublished == true}">
	<acme:button test="${showCreate}" code="client.ProgressLog.list.button.create" action="/client/progress-log/create?masterId=${masterId}"/>
</jstl:if>