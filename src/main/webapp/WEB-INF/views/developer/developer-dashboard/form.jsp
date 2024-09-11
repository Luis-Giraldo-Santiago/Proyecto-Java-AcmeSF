<%--
- form.jsp
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="developer.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.totalNumberOfTrainingModulesWithAnUpdatedMoment"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTrainingModulesWithAnUpdatedMoment}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.totalNumberOfTrainingSessionsWithALink"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTrainingSessionsWithALink}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.averageTimeOfTrainingModules"/>
		</th>
		<td>
			<acme:print value="${averageTimeOfTrainingModules}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.devidationTimeOfTrainingModules"/>
		</th>
		<td>
			<acme:print value="${devidationTimeOfTrainingModules}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.minimumTimeOfTheTrainingModules"/>
		</th>
		<td>
			<acme:print value="${minimumTimeOfTheTrainingModules}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.maximumTimeOfTheTrainingModules"/>
		</th>
		<td>
			<acme:print value="${maximumTimeOfTheTrainingModules}"/>
		</td>
	</tr>

</table>


<div>
	<canvas id="canvas"></canvas>
</div>

<acme:return/>

