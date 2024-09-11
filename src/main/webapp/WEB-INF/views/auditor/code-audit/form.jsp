<%--
- list.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.codeAudit.form.label.code" path="code"/>
	<acme:input-moment code="auditor.codeAudit.form.label.executionDate" path="executionDate"/>
	<acme:input-select code="auditor.codeAudit.form.label.type" path="type" choices="${types}"/>
	<acme:input-textbox code="auditor.codeAudit.form.label.mark" path="mark" readonly="true"/>
	<acme:input-textbox code="auditor.codeAudit.form.label.correctiveActions" path="correctiveActions"/>
	<acme:input-url code="auditor.codeAudit.form.label.link" path="link"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'create'}">
		<acme:input-select code="auditor.codeAudit.form.label.project" path="project" choices="${projects}"/>	
			<acme:submit code="auditor.codeAudit.form.button.create" action="/auditor/code-audit/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && published == false}">
			<acme:submit code="auditor.codeAudit.form.button.delete" action="/auditor/code-audit/delete"/>
			<acme:submit code="auditor.codeAudit.form.button.update" action="/auditor/code-audit/update"/>
			<acme:submit code="auditor.codeAudit.form.button.publish" action="/auditor/code-audit/publish"/>
			<acme:button code="auditor.auditRecord.form.button.list" action="/auditor/audit-record/list?masterId=${id}"/>
			
		</jstl:when>
		<jstl:when test="${published == true}">
			<acme:button code="auditor.auditRecord.form.button.list" action="/auditor/audit-record/list?masterId=${id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>