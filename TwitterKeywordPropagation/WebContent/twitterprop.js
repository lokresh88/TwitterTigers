function populateCelebs() {
	var ddData;
	$.post("/TwitterKeywordPropagation/getTweets", {
		"action" : "getCelebs"
	}, function(data) {
		ddData = data;
		$("#info").html(" Total Celebs: 20"+" Total Tweets:464216");
		$('#celebBox').ddslick({
			data : data,
			width : 400,
			imagePosition : "left",
			selectText : "Select a Celebrity .. ",
			onSelected : function(data) {
				populateSuggestions(data);
			//	console.log(data);
			}
		});
	}, "json");

}

function selectKeyword(elm) {
	var key = $(elm).html();
	$("#keyword").html(key);
}

function populateSuggestions(ddData) {
	$
			.post(
					"/TwitterKeywordPropagation/getTweets",
					{
						"action" : "getCelebsSugg",
						"cid" : ddData.selectedData.value
					},
					function(dataop) {
						var htmlVal = "<ul>";
						var data = dataop.op;
					//	$("#cTC").html(dataop.counts.tweets);
					//	$("#rTC").html(dataop.counts.rtweets);
						for ( var scnt = 0; scnt < data.length; scnt++) {
							//console.log(data[scnt]);
							htmlVal += "<li><a href=\"#\" onclick='javascript:selectKeyword(this)'>"
									+ data[scnt].Keywords + "</a>  <span style='color: #BEB49C;font-family: verdana;font-size: 12px;'>"+data[scnt].percent+"</span></li>";
						}
						htmlVal += "</ul>";
						$("#keywordsList").html(htmlVal);
					}, "json");
}

function sendOpts(display) {
	var data = $('#celebBox').data('ddslick');
	//console.log(data);
	var cid = data.selectedData.value;
	var key = $("#keyword").html();
	key = $.trim(key);
	if (key == "")
		return;

	$('#comparisonDiv').slideUp();
	$('#displayDiv').slideUp();
	$('#timeLineDiv').slideUp();
	
	switch (display) {
	case 1:
		$.post("/TwitterKeywordPropagation/getTweets", {
			"action" : "getGeoInfo",
			"cid" : cid,
			"key" : key
		}, function(data) {
			//console.log(data);
			$('#displayDiv').jHERE('nomarkers');
			$.each(data, function(i, m) {
				$('#displayDiv').jHERE('marker', {
					latitude : m.latitude,
					longitude : m.longitude
				}, {
					icon : '../images/marker.png',
					anchor : {
						x : 12,
						y : 32
					},
				});
			});
			$('#displayDiv').slideDown();

		}, "json");
		
		break;
	case 2:
		$.post("/TwitterKeywordPropagation/getTweets", {
			"action" : "getComparisons",
			"cid" : cid,
			"key" : key
		}, function(data) {
			console.log(data);
			
			$('#comparisonDiv').jqChart({
                title: { text: 'Comparison Chart' },
                animation: { duration: 1 },
                shadows: {
                    enabled: true
                },
                series: [
                            {
                                type: 'bar',
                                title: 'keyword popularity',
                                data: data
                                	//[['A', 33], ['B', 57], ['C', 33], ['D', 12], ['E', 35], ['F', 7], ['G', 24]]
                            },                            
                        ]
            });

			
			
			$('#comparisonDiv').slideDown();
		}, "json");
		break;

	case 3:
		$.post("/TwitterKeywordPropagation/getTweets", {
			"action" : "getTimeline",
			"cid" : cid,
			"key" : key
		}, function(data) {
			console.log(data);
			var jsonArray = new Array();
			$.each(data, function(i, m) {
			var jsonObj = new Array();
			console.log(m);
			var d = new Date();
			d.setTime(m[0]);
			console.log(d);
			jsonObj.push(d);
			jsonObj.push(m[1]);
			jsonArray.push(jsonObj);
		//	break;
			});
			console.log(jsonArray);
			 $('#timeLineDiv').jqChart({
	                title: { text: 'Time Line' },
	                tooltips: { type: 'shared' },
	                animation: { duration: 3 },
	                axes: [{
	                    location: 'bottom',
	                    type: 'dateTime',
	                    labels: { stringFormat: 'mm/dd/yy' },
	                    interval: 1,
                        intervalType: 'days'

	                }],
	                series: [
	                            {
	                                type: 'line',
	                                title: 'Time Line',
	                                data: jsonArray
	                            }                            
	                            ]
	            });
			 $('#timeLineDiv').slideDown();
			 /*
			  * 
			  * [new Date(2010, 0, 1), 62]
			  */
		}, "json");
		break;

	default:
		break;
	}
}

// ddData= [{"text":"Kaley
// Cuoco","imageSrc":"http:\/\/a0.twimg.com\/profile_background_images\/159009688\/nb.jpg","value":95233955,"sname":null}];
/*
 * var ddData = [ { text : "Rahul Dravid", value : 1, selected : false,
 * description : "", imageSrc :
 * "http://dl.dropbox.com/u/40036711/Images/facebook-icon-32.png" } ];
 */

/* task operations */

function addNewTask(elm) {

	if ($(elm).attr("edit") == "1") {
		editTaskReq(elm);
	}
	var name = $("#addTaskDiv").find("#taskname").val();
	var notes = $("#addTaskDiv").find("#tasknotes").val();
	var datereq = $("#addTaskDiv").find("#duedatereq").is(":Checked");
	var date = $("#addTaskDiv").find("#taskduedate").val();
	var pr = $("#addTaskDiv").find("#taskprir").val();
	$("#task_error").hide();

	$("#addTaskDiv").find("#taskname").removeClass("red");
	$("#addTaskDiv").find("#taskduedate").removeClass("red");

	if (name.trim() == "") {
		$("#addTaskDiv").find("#taskname").focus();
		$("#addTaskDiv").find("#taskname").addClass("red");
		return;
	}

	if (datereq && date.trim() == "") {
		$("#addTaskDiv").find("#taskduedate").focus();
		$("#addTaskDiv").find("#taskduedate").addClass("red");
		return;
	}

	var timeComp = $("#date_hrs").val() + ":" + $("#date_min").val() + ":"
			+ $("#date_sec").val();

	$.post("/cloudDbsDemo/taskOperations", {
		"name" : encodeURIComponent(name),
		"duedate" : encodeURIComponent(date),
		"noduedate" : encodeURIComponent(datereq),
		"priority" : encodeURIComponent(pr),
		"notes" : encodeURIComponent(notes),
		"timeComp" : encodeURIComponent(timeComp),
		"action" : "addTask"
	}, function(data) {
		var jsObj = data;
		if (!jsObj.status) {
			$("#task_error").html(jsObj.errorMessage);
			$("#task_error").show();
		} else {
			var taskid = jsObj.taskId + "";
			setTimeout(function() {
				getUserHome(taskid);
			}, 2000);
			$("#addTaskDiv").slideUp(1500);
		}
	}, "json");

}
