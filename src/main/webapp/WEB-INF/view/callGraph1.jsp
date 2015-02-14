<!DOCTYPE html >
<html>
<head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-1.11.2.min.js"></script> 
    <!-- This is the Javascript file of jqGrid -->   
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery.jqGrid.min.js"></script>
    <!-- This is the localization file of the grid controlling messages, labels, etc.
    <!-- We support more than 40 localizations -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/grid.locale-en.js"></script>
    <!-- A link to a jQuery UI ThemeRoller theme, more than 22 built-in and many more custom -->
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/jquery-ui.css" />
    <!-- The link to the CSS that the grid needs -->
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/ui.jqgrid.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/underscore.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/callGraph.js"></script>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/backbone.min.js"></script>
    <meta charset="utf-8" />
    <title>jqTreeGrid - Load On Demand - Load all Rows at once collapsed</title>
</head>
<body>
<script type="text/javascript"> 
var _context = '<%=request.getContextPath()%>';
	
    </script>
		<div align="center">
			<table id="tree"></table>
   		 <div id="pager"></div>
		</div>
		

</body>
</html>