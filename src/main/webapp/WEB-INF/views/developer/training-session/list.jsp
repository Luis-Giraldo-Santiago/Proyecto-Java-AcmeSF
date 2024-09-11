<%--
- list.jsp
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.TrainingSession.list.label.code" path="code" width="10%"/>
	<acme:list-column code="developer.TrainingSession.list.label.start" path="start" width="10%"/>
	<acme:list-column code="developer.TrainingSession.list.label.finish" path="finish" width="10%"/>
	<acme:list-column code="developer.TrainingSession.list.label.location" path="location" width="10%"/>
	<acme:list-column code="developer.TrainingSession.list.label.instructor" path="instructor" width="10%"/>
	<acme:list-column code="developer.TrainingSession.list.label.contactEmail" path="contactEmail" width="10%"/>
	<acme:list-column code="developer.TrainingSession.list.label.link" path="link" width="10%"/>
	<acme:list-column code="developer.TrainingSession.list.label.published" path="published" width="10%"/>
</acme:list>

<acme:button test="${showCreate}" code="developer.TrainingSession.list.button.create" action="/developer/training-session/create?masterId=${masterId}"/>
