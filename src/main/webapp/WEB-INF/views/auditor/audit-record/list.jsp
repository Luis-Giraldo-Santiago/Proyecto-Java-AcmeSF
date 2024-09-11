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

<acme:list>
	<acme:list-column code="auditor.auditRecord.list.label.code" path="code" width="10%"/>
	<acme:list-column code="auditor.auditRecord.list.label.auditPeriodStart" path="auditPeriodStart" width="10%"/>
	<acme:list-column code="auditor.auditRecord.list.label.auditPeriodEnd" path="auditPeriodEnd" width="10%"/>
	<acme:list-column code="auditor.auditRecord.list.label.mark" path="mark" width="10%"/>
	<acme:list-column code="auditor.auditRecord.list.label.link" path="link" width="10%"/>
	<acme:list-column code="auditor.auditRecord.list.label.published" path="published" width="10%"/>
</acme:list>
<jstl:if test="${codeAuditPublished == false}">
	<acme:button test="${showCreate}" code="auditor.auditRecord.form.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>
</jstl:if>