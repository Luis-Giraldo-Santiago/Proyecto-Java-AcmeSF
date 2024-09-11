<%--
- form.jsp
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="sponsor.sponsor-dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.totalInvoicesTaxLessEqual21"/>
		</th>
		<td>
			<acme:print value="${totalInvoicesTaxLessEqual21}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.totalSponsorshipsWithLink"/>
		</th>
		<td>
			<acme:print value="${totalSponsorshipsWithLink}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.averageSponsorships"/>
		</th>
		<td>
			<acme:print value="${averageSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.deviationSponsorships"/>
		</th>
		<td>
			<acme:print value="${deviationSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.minimumSponsorships"/>
		</th>
		<td>
			<acme:print value="${minimumSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.maximunSponsorships"/>
		</th>
		<td>
			<acme:print value="${maximunSponsorships}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.maximunInvoices"/>
		</th>
		<td>
			<acme:print value="${maximunInvoices}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.averageInvoices"/>
		</th>
		<td>
			<acme:print value="${averageInvoices}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.deviationInvoices"/>
		</th>
		<td>
			<acme:print value="${deviationInvoices}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.minimumInvoices"/>
		</th>
		<td>
			<acme:print value="${minimumInvoices}"/>
		</td>
	</tr>

</table>


<div>
	<canvas id="canvas"></canvas>
</div>

<acme:return/>

