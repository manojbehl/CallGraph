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
		<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/callGraph.js"></script>
		 --%><script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/backbone.min.js"></script>
    <meta charset="utf-8" />
    <title>jqTreeGrid - Load On Demand - Load all Rows at once collapsed</title>
</head>
<body>

    <table id="tree"></table>
    <div id="pager"></div>

    <script type="text/javascript"> 
	jQuery(document).ready(function($) {
		jQuery('#tree').jqGrid({
			"width":"780",
			"hoverrows":false,
			"viewrecords":false,
			"gridview":true,
			"url":"data.json",
			"editurl" : "clientArray",
			"ExpandColumn":"name",
			"height":"auto",
			"sortname":"account_id",
			"scrollrows":true,
			"treeGrid":true,
			"treedatatype":"json",
			"treeGridModel":"adjacency",
			"loadonce":true,
			"rowNum":1000,
			"treeReader":{
				"parent_id_field":"parent_id",
				"level_field":"level",
				"leaf_field":"isLeaf",
				"expanded_field":"expanded",
				"loaded":"loaded",
				"icon_field":"icon"
			},
			"datatype":"json",
			"colModel":[
				{
					"name":"account_id",
					"key":true,
					"hidden":true
				},{
					"name":"name",
					"label":"Name",
					"width":170,
					"editable":true
				},{
					"name":"acc_num",
					"label":"Number",
					"width":170,
					"editable":true
				},{
					"name":"debit",
					"sorttype":"numeric",
					"label":"Debit",
					"width":90,
					"formatter": "number",
					"align":"right",
					"editable":true
				},{
					"name":"credit",
					"sorttype":"numeric",
					"formatter": "number",
					"label":"Credit",
					"width":90,
					"align":"right",
					"editable":true
				},{
					"name":"balance",
					"sorttype":"numeric",
					"formatter": "number",
					"label":"Balance",
					"width":90,
					"align":"right",
					"editable":true
				},{
					"name":"parent_id",
					"hidden":true
				}
			],
			"pager":"#pager"
		});
		// nable add
		jQuery('#tree').jqGrid('navGrid','#pager',
		{
			"edit":true,
			"add":true,
			"del":true,
			"search":false,
			"refresh":true,
			"view":false,
			"excel":false,
			"pdf":false,
			"csv":false,
			"columns":false
		},
		{"drag":true,"resize":true,"closeOnEscape":true,"dataheight":150},
		{"drag":true,"resize":true,"closeOnEscape":true,"dataheight":150}
		);
		jQuery('#tree').jqGrid('bindKeys');
	});
    </script>

    
</body>
</html>