<!DOCTYPE html >
<html>
<head>
<!-- 	<script type="text/ecmascript" src="../static/css/jquery.min.js"></script>  -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-1.10.2.min.js"></script> 
    <!-- This is the Javascript file of jqGrid -->   
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery.jqGrid.min.js"></script>
    <!-- This is the localization file of the grid controlling messages, labels, etc.
    <!-- We support more than 40 localizations -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/grid.locale-en.js"></script>
    <!-- A link to a jQuery UI ThemeRoller theme, more than 22 built-in and many more custom -->
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/jquery-ui.css" />
    <!-- The link to the CSS that the grid needs -->
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/ui.jqgrid.css" />
     <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/drPosten.css" />
     <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/jquery-ui.min.css" />
     
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery.blockUI.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-ui.min.js"></script>
    
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/underscore.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/callGraph.js"></script>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/backbone.min.js"></script>
    <meta charset="utf-8" />
    <title>Call Graph</title>
</head>
<body>
<style>
	.ui-widget {
		font-family: Trebuchet MS,Tahoma,Verdana,Arial,sans-serif;
		font-size: .9em;
		}

/* body {
    background: none repeat scroll 0% 0% red;
}

header[role="banner"] div.main-logo{background:url("/CallGraph/resources/images/logo.png") no-repeat scroll left center transparent}
header[role="banner"] div.main-logo {display: block;height: 54px;width: 253px;margin: 0px;text-indent: -9999em;float: left;padding-top: 0px;}
header[role="banner"] {height: 80px;width: 100%;}
.inner {margin: 0px auto;position: relative;width: 986px;} */

</style>
<script type="text/javascript"> 
var _context = '<%=request.getContextPath()%>';
	
    </script>
    	<header role="banner">
    		<div class="inner group">
    			<div class="main-logo">Posten</div>
    			<nav id="main-menu" role="navigation">
    				<ul class="menu-main">
    				  <li class="menu-level-1 path active">
    				  	<a class="first local">
    				  	LM Decomposition
    				  	</a>
    				  </li>
    				</ul>
    			</nav>
    		</div>
    		
    	</header>
    	
		<section role="main">
		  <div id="page" class="row">
				<div align="center">
					<table id="tree"></table>
		   		 <div id="pager"></div>
				</div>
				
				<br>
				<br>
				<div id="treetable" align="center">
					<table id="remoteinfinite"></table>
			   		 <div id="pager"></div>
				</div>
		</div>		
		<div id="dialog-confirm" style="display:none">
			<input id="filePath" type="file">
		</div>
		</section>
		
		

</body>
</html>