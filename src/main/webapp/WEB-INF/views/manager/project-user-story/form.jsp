	<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<jstl:choose>	 
	<jstl:when test="${acme:anyOf(_command, 'create|delete') && published == true}">
		<h1>
			<acme:message code="manager.projectUserStory.form.userStory.cant.info"/>
		</h1>
	</jstl:when>
	<jstl:when test="${acme:anyOf(_command, 'create') && published == false}">
		<h1>
			<acme:message code="manager.projectUserStory.form.userStory.create.info"/>
		</h1>
	</jstl:when>	
	<jstl:when test="${acme:anyOf(_command, 'delete') && published == false}">
		<h1>
			<acme:message code="manager.projectUserStory.form.userStory.delete.info"/>
		</h1>
	</jstl:when>	
</jstl:choose>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.projectUserStory.form.project.code"/>
		</th>
		<td>
			<acme:print value="${project.getCode()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.projectUserStory.form.project.title"/>
		</th>
		<td>
			<acme:print value="${project.getTitle()}"/>
		</td>
	</tr>
</table>

<acme:form>
	<acme:input-select code="manager.projectUserStory.form.label.userStory" path="userStory" choices="${userStories}"/>	
	
	<jstl:choose>	
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.projectUserStory.form.button.add" action="/manager/project-user-story/create?projectId=${projectId}"/>
		</jstl:when> 	
	</jstl:choose>
	<jstl:choose>	
		<jstl:when test="${_command == 'delete'}">
			<acme:submit code="manager.projectUserStory.form.button.remove" action="/manager/project-user-story/delete?projectId=${projectId}"/>
		</jstl:when> 	
	</jstl:choose>
</acme:form>