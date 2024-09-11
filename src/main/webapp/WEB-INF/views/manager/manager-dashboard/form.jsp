<%--
- form.jsp
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.totalNumberMustUserStories"/>
		</th>
		<td>
			<acme:print value="${totalNumberMustUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.totalNumberShouldUserStories"/>
		</th>
		<td>
			<acme:print value="${totalNumberShouldUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.totalNumberCouldUserStories"/>
		</th>
		<td>
			<acme:print value="${totalNumberCouldUserStories}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.totalNumberWontUserStories"/>
		</th>
		<td>
			<acme:print value="${totalNumberWontUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.averageEstimatedCostUserStories"/>
		</th>
		<td>
			<acme:print value="${averageEstimatedCostUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.deviationEstimatedCostUserStories"/>
		</th>
		<td>
			<acme:print value="${deviationEstimatedCostUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimunEstimatedCostUserStories"/>
		</th>
		<td>
			<acme:print value="${minimunEstimatedCostUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.maximumEstimatedCostUserStories"/>
		</th>
		<td>
			<acme:print value="${maximumEstimatedCostUserStories}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.averageCostProjects"/>
		</th>
		<td>
			<acme:print value="${averageCostProjects}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.deviationCostProjects"/>
		</th>
		<td>
			<acme:print value="${deviationCostProjects}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimunCostProjects"/>
		</th>
		<td>
			<acme:print value="${minimunCostProjects}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.maximumCostProjects"/>
		</th>
		<td>
			<acme:print value="${maximumCostProjects}"/>
		</td>
	</tr>
</table>


<jstl:choose>
	<jstl:when test="${totalNumberMustUserStories != 0 || totalNumberShouldUserStories != 0 || totalNumberCouldUserStories != 0 || totalNumberWontUserStories != 0}">
		<h3><acme:message code="manager.dashboard.form.label.userStories.priority.information"/></h3>
		<div>
			<canvas id="canvas"></canvas>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				var data = {
					labels : [
							"MUST", "SHOULD", "COULD", "WONT"
					],
					datasets : [
						{
							data : [
								<jstl:out value="${totalNumberMustUserStories}"/>, 
								<jstl:out value="${totalNumberShouldUserStories}"/>,
								<jstl:out value="${totalNumberCouldUserStories}"/>,
								<jstl:out value="${totalNumberWontUserStories}"/>,
							],
							backgroundColor: [
								'rgb(27, 187, 101)',
						    	'rgb(41, 169, 237)',
						    	'rgb(242, 212, 88)',
						      	'rgb(217, 177, 245)'
						    ]
						}
					]
				};
	
				var canvas, context;
				canvas = document.getElementById("canvas");
				context = canvas.getContext("2d");
				new Chart(context, {
					type : "doughnut",
					data : data,
				});
			});
		</script>
	</jstl:when>
</jstl:choose>


<jstl:choose>
	<jstl:when test="${averageEstimatedCostUserStories != null && deviationEstimatedCostUserStories != null && minimunEstimatedCostUserStories != null && maximumEstimatedCostUserStories != null}">

		<h3><acme:message code="manager.dashboard.form.label.userStories.information"/></h3>
		<div>
			<canvas id="canvas0"></canvas>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				var data = {
					labels : [
						"AVERAGE", "DEVIATION", "MIN","MAX"
					],
					datasets : [
						{
							data : [
								<jstl:out value="${averageEstimatedCostUserStories}"/>, 
								<jstl:out value="${deviationEstimatedCostUserStories}"/>, 
								<jstl:out value="${minimunEstimatedCostUserStories}"/>,
								<jstl:out value="${maximumEstimatedCostUserStories}"/>
							],
							backgroundColor: [
								'rgb(27, 187, 101)',
						    	'rgb(41, 169, 237)',
						    	'rgb(242, 212, 88)',
						      	'rgb(217, 177, 245)'
						    ]
						}
					]
				};	
				
				var options = {
						scales : {
							yAxes : [
								{
									ticks : {
										suggestedMin : 0.0,
										suggestedMax : 10000.0
									}
									}
							]
						},
						legend : {
							display : false
						}
					};
				
				var canvas, context;
				canvas = document.getElementById("canvas0");
				context = canvas.getContext("2d");
				new Chart(context, {
					type : "bar",
					data : data,
					options : options
				});
			});
		</script>
	</jstl:when>
</jstl:choose>


<jstl:choose>
	<jstl:when test="${averageCostProjects != null && deviationCostProjects != null && minimunCostProjects != null && maximumCostProjects != null}">
		<h3>
			<acme:message code="manager.dashboard.form.label.projects.information" />
		</h3>
		<div>
			<canvas id="canvas1"></canvas>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				var data = {
					labels : [
							"AVERAGE", "DEVIATION", "MIN","MAX"
					],
					datasets : [
						{
							data : [
								<jstl:out value="${averageCostProjects}"/>, 
								<jstl:out value="${deviationCostProjects}"/>, 
								<jstl:out value="${minimunCostProjects}"/>,
								<jstl:out value="${maximumCostProjects}"/>
							],
							backgroundColor: [
								'rgb(27, 187, 101)',
						    	'rgb(41, 169, 237)',
						    	'rgb(242, 212, 88)',
						      	'rgb(217, 177, 245)'
						    ]
						}
					]
				};
				
				var options = {
					scales : {
						yAxes : [
							{
								ticks : {
									suggestedMin : 0.0,
									suggestedMax : 10000.0
								}
							}
						]
					},
					legend : {
						display : false
					}
				};
	
				var canvas, context;
				canvas = document.getElementById("canvas1");
				context = canvas.getContext("2d");
				new Chart(context, {
					type : "bar",
					data : data,
					options : options
				});
			});
		</script>
	</jstl:when>
</jstl:choose>

<acme:return/>

