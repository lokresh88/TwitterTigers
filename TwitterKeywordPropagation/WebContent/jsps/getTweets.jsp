<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.bson.types.ObjectId"%>
<%@page import="com.cs6300.clouddemos.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.ArrayList"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%
ArrayList<ToDoItem> completedList =  new ArrayList();
ArrayList<ToDoItem> pendingList =  new ArrayList();
ArrayList<ToDoItem> overDueList =  new ArrayList();

completedList = request.getAttribute("completedList")!=null?(ArrayList<ToDoItem>)request.getAttribute("completedList"):completedList;
pendingList = request.getAttribute("pendingList")!=null?(ArrayList<ToDoItem>)request.getAttribute("pendingList"):pendingList;
overDueList = request.getAttribute("overDueList")!=null?(ArrayList<ToDoItem>)request.getAttribute("overDueList"):pendingList;

HttpSession serv=request.getSession();
String uname = (String)serv.getAttribute("loggedinUserName");
%>
<div style="float:right;font-size: 13px;font-family: cursive;color: #2c6da0"><span style="margin-right: 10px;">hey <%=uname%> !</span><a href="javascript:logOut()">logout</a></div>

<div class="tasksBox">
<div id="tasksHeader" class="headingPanel">
<span class="head"><span class="taskSelector green" asscDiv="overDue" onclick="showHideQ(this)">Overdue</span> <span  asscDiv="pending" class="taskSelector green" onclick="showHideQ(this)">Pending</span> <span  asscDiv="completed" class="taskSelector green" onclick="showHideQ(this)">Completed</span><span class="taskSelector blue" style="float: right;" onclick="showAddTaskPopUP()">Add Task</span></span>
</div>	

<div id="addTaskDiv" class="addtask" style="display:none;">
<span class="rowheader">Add a new Task</span>
<hr>
<span><input type="text" id="taskname" width="200" placeholder="task name"></span>
<span><textarea rows="5" cols="40" id="tasknotes" placeholder="task notes"></textarea></span>
<span><input type="checkbox" checked="checked" id="duedatereq" style="margin-right: 10px;" onclick="showHideDueDate(this)"><input type="text" id="taskduedate" placeholder="due date"> <input maxlength="2" value="00" id="date_hrs" style="width: 25px"/>h : <input maxlength="2" value="00" id="date_min" style="width: 25px"/>m : <input value="00" maxlength="2" id="date_sec" style="width: 25px"/>s <span style="display:inline;color: grey">(24 hr format)</span></span>
<span><select id="taskprir"><option value="2">High</option><option value="1">Medium</option><option value="0" selected="selected">Low</option></select></span>
<span class="red" id="task_error" style="display:none"></span>
<span><span style="display:inline-block;margin-left: -5px;" class="taskSelector blue" id="addedittask" edit="0" onclick="addNewTask(this)">Add</span><span style="display:inline-block;margin-left: -5px;" class="taskSelector blue" onclick="hideAddTaskPopUP()">Cancel</span></span>
</div>


<div id="overDue" class="tasks">
<div class="rowheader">Overdue Tasks</div>
<hr>
<%if(overDueList.size()<=0){%>
<div class="rowmsg">no pending tasks.</div>
<%}%>

<%
for(int i=0;i<overDueList.size();i++){
	ToDoItem task = (ToDoItem)overDueList.get(i);
	String taskName = task.getName();
	String taskDate = task.getDuedatestr();
	Long prior = task.getPriority();
	ObjectId id = task.getId();
	String notes = task.getNote();
	//JSONObject taskObj = DBUtil.getJSONObject(task);
%>
<div class="row" id="task_<%=id%>"  taskid="<%=id%>"><input type="checkbox" onclick="completeTask(this)"><span><%=taskName%></span> <%if(taskDate!=""){ %><i>overdue on</i> <span><%=taskDate%></span><%}%><span class="prir"><%if(prior==2){%><img style="height: 22;" src="../images/highPriority.png"/><%}%><%if(prior==0){%><img style="height: 22;" src="../images/lowPriority.png"/><%}%></span><span class="edit sh"><img style="height: 15;" src="../images/deleteIcon.png" onclick="deleteTask(this)"/></span><span class="edit sh"><img style="height: 15;" src="../images/taskEditImage.png" onclick="editTask(this)"/></span></div><% } %>
</div>	
<div id="pending" class="tasks">
<div class="rowheader">Pending tasks</div>
<hr>
<%if(pendingList.size()<=0){%>
<div class="rowmsg">no pending tasks.</div>
<%}%>
<%
for(int i=0;i<pendingList.size();i++){
	ToDoItem task = (ToDoItem)pendingList.get(i);
	String taskName = task.getName();
	String taskDate = task.getDuedatestr();
	Long prior = task.getPriority();
	ObjectId id = task.getId();
	String notes = task.getNote();
//	JSONObject taskObj = DBUtil.getJSONObject(task);
%>
<div class="row" id="task_<%=id%>"  taskid="<%=id%>"><input type="checkbox" onclick="completeTask(this)"><span><%=taskName%></span> <%if(taskDate!=""){ %><i>pending on</i> <span><%=taskDate%></span><%}%><span class="prir"><%if(prior==2){%><img style="height: 22;" src="../images/highPriority.png"/><%}%><%if(prior==0){%><img style="height: 22;" src="../images/lowPriority.png"/><%}%></span><span class="edit sh"><img style="height: 15;" src="../images/deleteIcon.png" onclick="deleteTask(this)"/></span><span class="edit sh"><img style="height: 15;" src="../images/taskEditImage.png" onclick="editTask(this)"/></span></div><% } %>
</div>
<div id="completed" class="tasks">
<div class="rowheader">Completed tasks</div>
<hr>
<%if(completedList.size()<=0){%>
<div class="rowmsg">no completed tasks.</div>
<%}%>

<%
for(int i=0;i<completedList.size();i++){
	ToDoItem task = (ToDoItem)completedList.get(i);
	String taskName = task.getName();
	
	String taskDate = task.getDuedatestr();
	Long prior = task.getPriority();
	ObjectId id = task.getId();
	String notes = task.getNote();
	//JSONObject taskObj =DBUtil.getJSONObject(task);
%>
<div class="row" id="task_<%=id%>"  taskid="<%=id%>"><span><%=taskName%></span> <%if(taskDate!=""){ %><i>for </i> <span><%=taskDate%></span><%}%><span class="prir"><%if(prior==2){%><img style="height: 22;" src="../images/highPriority.png"/><%}%><%if(prior==0){%><img style="height: 22;" src="../images/lowPriority.png"/><%}%></span><span class="edit sh"><img style="height: 15;" src="../images/deleteIcon.png" onclick="deleteTask(this)"/></span><span class="edit sh"><img style="height: 15;" src="../images/taskEditImage.png" onclick="editTask(this)"/></span></div><% } %>
</div>	
</div>


<script type="text/javascript">
$(document).ready(function(){
	$("#addTaskDiv").find("#taskduedate").datepicker({dateFormat: "dd-M-yy"} );
	var nowval = new Date();
	var onedayplusdef = new Date(nowval.getTime() + (24 * 60 * 60 * 1000));
	$("#taskduedate").datepicker("setDate", onedayplusdef);


$(".row").hover(
  function () {
    $(this).find(".edit").removeClass("sh");
  },
  function () {
     $(this).find(".edit").addClass("sh");
  }
);
});
</script>
