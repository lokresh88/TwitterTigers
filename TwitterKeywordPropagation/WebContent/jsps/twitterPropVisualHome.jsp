<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
%>

<html>
<head>

<script language="JavaScript" type="text/javascript"
	src="../jquery_min.js"></script>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script language="JavaScript" type="text/javascript"
	src="../jquery_ui.js"></script>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="../twitterprop.css" />
<script language="JavaScript" type="text/javascript"
	src="../twitterprop.js"></script>
<script language="JavaScript" type="text/javascript"
	src="../jqueryselectbox.js"></script>
<script src="http://jhere.net/js/jhere.js"></script>
<script src="../js/jqChartJQueryPlugin_3_6_2_0/js/jquery.jqChart.min.js" type="text/javascript"></script>
    <script src="../js/jqChartJQueryPlugin_3_6_2_0/js/jquery.jqRangeSlider.min.js" type="text/javascript"></script>
    

<script>
	$(function() {
		$("#radio").buttonset();
	});
</script>

</head>

<body>
	<div id="mainContent">
		<div class="heading">
			<div style="left: 2%; position: absolute; top: 22%;">
				<select class="mySelectBoxClass"
					style="display: inline; max-height: 500px; overflow: scroll;"
					id="celebBox"></select>
			</div>
			<span style="left: 35%; position: absolute; top: 31%">keyword
				-<span id="keyword"> </span>
			</span>
			<span style="float: right; font-family: cursive; position: relative; font-size: 15px; top: 67%; left: -1%;color: #BEB49C;font-style: italic;"><span id="info"> </span>
			</span>
		</div>
		<div class="line"></div>
		<div class="">
		<span style="left:1%;top:21%;position: fixed;font-family: cursive;"> No of tweets : <span id="cTC" style="color: #BEB49C"></span></span>
			<div id="radio" class="menu-css">
				<input type="radio" id="radio1" name="radio"
					onclick="javascript:sendOpts(1)" /><label for="radio1">
					Geographic</label> <input onclick="javascript:sendOpts(3)" type="radio"
					id="radio2" name="radio" /><label for="radio2">Timeline</label> <input
					onclick="javascript:sendOpts(2)" type="radio" id="radio3"
					name="radio" /><label for="radio3">Compare</label>
			</div>
			<span style="left:59%;top:21%;position: fixed;font-family: cursive;"> No of re-tweets : <span id="rTC" style="color: #BEB49C"></span></span>
			<div class="suggestionBox" id="suggestionsDiv">
				<p class="headerkeys">Suggested keywords</p>
				<div id='keywordsList'></div>
			</div>
			<div>
				<div class="midBox">
					<div id="displayDiv" style="display: none;"></div>
					<div id="comparisonDiv" style="display: none;height: 90%;width: 90%;position: relative;"></div>
					<div id="timeLineDiv" style="display: none;height: 90%;width: 90%;position: relative;"></div>
				</div>

			</div>
		</div>
		<div id="results" geo="" comp="" timeline="" style="display: none;">
		</div>
		<div id="loading">
			<img src="../images/ajax-loader.gif" />
		</div>
</body>
</html>
<script>
	$(document).ready(function() {

		populateCelebs();

	});

	$(window).on('load', function() {
		$('#displayDiv').jHERE({
			enable : [ 'behavior', 'zoombar' ],
			zoom : 1,
			type : 'terrain'
		});

	});

	$(document).ajaxStart(function() {
		$("#loading").show();
	});

	$(document).ajaxStop(function() {
		$("#loading").hide();
	});
</script>

