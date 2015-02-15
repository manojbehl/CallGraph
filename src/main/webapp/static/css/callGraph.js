/**
 * 
 */
var subChildData = null;
function loadDummyTable(){
	var topicjson ={"response": [{"id":"54df0ce70364853700354912","routineName":"PDJA016B",level:"0",parent:"0",isLeaf:false,expanded:true,loaded:true},{"id":"null","routineName":"BJAC027A",level:"1",parent:"1",isLeaf:false,expanded:true,loaded:true},{"id":"null","routineName":"BQMQFM31",level:"2",parent:"2",isLeaf:true,expanded:true,loaded:true},{"id":"null","routineName":"BQMQFM23",level:"2",parent:"2",isLeaf:true,expanded:true,loaded:true},{"id":"null","routineName":"BQMQFM25",level:"2",parent:"2",isLeaf:true,expanded:true,loaded:true},]}
	var topicjson11 =
	{
			"response":
				[
				 {
					 	"id":"1","routineName":"PDJA016B",
					 level:"0",parent:"",isLeaf:false,expanded:true,loaded:true
				},
				{
					 "id":"101","routineName":"BJAC027A",
					 level:"1",parent:"1",isLeaf:false,expanded:true,loaded:true
				},
				{
						"id":"201","routineName":"BQMQFM25",
						level:"2",parent:"101",isLeaf:true,expanded:true,loaded:true
				},
				{
						"id":"202","routineName":"BQMQFM23",
						level:"2",parent:"101",isLeaf:true,expanded:true,loaded:true
				},
				{	
					"id":"203","routineName":"BQMQFM31",
					level:"2",parent:"101",isLeaf:true,expanded:true,loaded:true
				},
				]
	}
	var topicjson1={
			
			
			
		    "response": [
		           {
		               "id": "1",
		               "elementName": "Grouping",
		               level:"0", parent:"", isLeaf:false, expanded:false, loaded:true
		           },
		           {
		               "id": "1_1",
		               "elementName": "Simple Grouping",
		               level:"1", parent:"1", isLeaf:true, expanded:false, loaded:true
		           },
		           {
		               "id": "1_2",
		               "elementName": "May be some other grouping",
		               level:"1", parent:"1", isLeaf:true, expanded:false, loaded:true
		           },
		           {
		               "id": "2",
		               "elementName": "CustomFormater",
		               level:"0", parent:"", isLeaf:false, expanded:true, loaded:true
		           },
		           {
		               "id": "2_1",
		               "elementName": "Image Formatter",
		               level:"1", parent:"2", isLeaf:true, expanded:false, loaded:true
		           },
		           {
		               "id": "2_1",
		               "elementName": "Anchor Formatter",
		               level:"1", parent:"2", isLeaf:true, expanded:false, loaded:true
		           }
		       ]
		    }
		   

		

		grid = jQuery("#remoteinfinite");
		grid.jqGrid({
		    datastr: subChildData,
		    datatype: "jsonstring",
		    height: "auto",
		    loadui: "disable",
		    colNames: [/*"id",*/"Items","Frequency","Input","Output","Remarks"],
		    colModel: [
		        //{name: "id",width:1, hidden:true, key:true},
		        {name: "routineName", width:250, resizable: false},
		        {name:'frequency',index:'frequency', width:80, align:"center"}, 
		        {name:'input',index:'remarks', width:80, align:"right"},
		        {name:'output',index:'remarks', width:80, align:"right"},
		        {name:'remarks',index:'remarks', width:80, align:"right"},
		        
		    ],
		    treeGrid: true,
		    treeGridModel: "adjacency",
		    caption: "jqGrid Demos",
		    ExpandColumn: "routineName",
		    //autowidth: true,
		    rowNum: 10000,
		    
		    //ExpandColClick: true,
		    treeIcons: {leaf:'ui-icon-document-b'},
		    jsonReader: {
		        repeatitems: false,
		        root: "response"
		    }
		});
}


function loadTreeGrid(){
		jQuery("#remoteinfinite").jqGrid(
					{  
						 
						 
						colNames:["id","RoutineName","Frequency", "Debit"], 
						colModel:[ 
						           {name:'id',index:'id', width:1,hidden:true,key:true}, 
						           {name:'routineName',index:'routineName', width:180}, 
						           {name:'frequency',index:'frequency', width:80, align:"center"}, 
						           {name:'remarks',index:'remarks', width:80, align:"right"} ],
						           treeGrid: true,
						           treeGridModel: "adjacency",
						           caption: "jqGrid Demos",
						           ExpandColumn: "elementName",
						           //autowidth: true,
						           rowNum: 10000,
						           height: "auto",
						           loadui: "disable",
						           datatype: "jsonstring",
						           //ExpandColClick: true,
						           treeIcons: {leaf:'ui-icon-document-b'},
						           jsonReader: {
						               repeatitems: false,
						              
						           }
					});
}

function loadEmptyGrid(){
	jQuery('#tree').jqGrid({
		"colModel":[
			{
				"name":"id",
				"index":"id",
				"sorttype":"int",
				"key":true,
				"hidden":true,
				"width":50
			},{
				"name":"routineName",
				"index":"routineName",
				"sorttype":"string",
				"label":"JCL",
				"width":170,
				editable:true
			},{
				"name":"frequency",
				"index":"frequency",
				"sorttype":"string",
				"label":"Frequency",
				"width":90,
				"align":"right",
				editable:true
			},{
				"name":"input",
				"index":"input",
				"sorttype":"int",
				"label":"Input",
				"width":90,
				"align":"right",
				editable:true
			},{
				"name":"output",
				"index":"output",
				"sorttype":"string",
				"label":"Output",
				"width":100,
				editable:true
			},
			{
				"name":"remarks",
				"index":"remarks",
				"sorttype":"string",
				"label":"Remarks",
				"width":100,
				editable:true
			},
			{
				"name":"act",
				"index":"act",
				sortable:false,
				"label":"Actions",
				"width":100,
				
			}
			 
		],
		"width":"780",
		"hoverrows":true,
		"viewrecords":true,
		"gridview":true,
		"url":"/CallGraph/load",
		"editurl" : "/CallGraph/update",
		"ExpandColumn":"name",
		"height":"auto",
		"sortname":"routineName",
		"scrollrows":true,
		"treeGrid":false,
		"treedatatype":"json",
		"treeGridModel":"nested",
		"loadonce":true,
		rowNum:10, 
		rowList:[10,20,30],
//		treeReader:{
//			parent_id_field:"parent_id",
//			level_field:"level",
//			leaf_field:"leaf",
//			expanded_field:"expanded",
//			loaded:"loaded",
//			icon_field:"icon"
//		},
		jsonReader : {
			root: "rows",
			page: "page",
			total: "total",
			records: "records",
			repeatitems: false,
			cell: "cell",
			id: "id"
			} ,
		"datatype":"json",
		"pager":"#pager",
		caption: "Full control",
		onSelectRow : function(e) {
			populateAdditionalInfo(null,null);
		}
			
		
	});
	
	jQuery('#tree').jqGrid('navGrid','#pager',
			{
				"edit":true,
				"add":false,
				"del":false,
				"search":true,
				"refresh":false,
				"view":true,
				"excel":false,
				"pdf":false,
				"csv":false,
				"columns":false,
				
			},
			{"drag":true,"resize":true,"closeOnEscape":true,"dataheight":150},
			{"drag":true,"resize":true,"closeOnEscape":true,"dataheight":150}
			);
			jQuery('#tree').jqGrid('bindKeys');
//			jQuery("#tree").jqGrid('filterToolbar',{searchOperators : true});
		// set the width of the grid
//		jQuery("#tree").jqGrid('setGridWidth', '800');
//		jQuery("#tree").jqGrid('setGridHeight', '225');
//		$(".ui-jqgrid-titlebar").hide();// no title pls
}

function populateAdditionalInfo(reqId,reqType){
	var selRowId = jQuery("#tree").jqGrid('getGridParam', 'selrow');
	 if(selRowId!=null && selRowId!=""){
//		 $("#remoteinfinite").jqGrid("clearGridData", true).trigger("reloadGrid");
//		 var cellValue = jQuery("#tree").jqGrid ('getCell', selRowId, 'routineName');
//		 var ChildCollection = Backbone.Collection.extend({
//			 url : function(){
//			 return _context +"/loadChild?jclProgramName="+cellValue;
//			 }
//			 });
//			 var coll = new ChildCollection();
//			 
//			 coll.fetch({
//				 type: 'GET', contentType : 'application/json',
//				 success : function() {
//				 
//				 
//				 coll.each(function(clientData, i){
//						 jQuery("#remoteinfinite").jqGrid('addRowData', (i + 1),
//						 clientData.toJSON());
//				 });
//				 $("#remoteinfinite").trigger("reloadGrid");
//				 }
//			 });
		 
		 
		 
		 var cellValue = jQuery("#tree").jqGrid ('getCell', selRowId, 'id');
		 $("#remoteinfinite").jqGrid("clearGridData", true).trigger("reloadGrid");
		 jQuery.ajax(
				 {
					 url: _context+"/loadChild?id="+cellValue, 
					 success: function(result){
						  subChildData = result.toString();
//						 grid = jQuery("#remoteinfinite");
//							grid.jqGrid({
//							    datastr: resultSet,
//							    datatype: "jsonstring",
//							    height: "auto",
//							    loadui: "disable",
//							    colNames: [/*"id",*/"Items","url"],
//							    colModel: [
//							        //{name: "id",width:1, hidden:true, key:true},
//							        {name: "routineName", width:250, resizable: false},
//							        {name: "url",width:1,hidden:true}
//							    ],
//							    treeGrid: true,
//							    treeGridModel: "adjacency",
//							    caption: "jqGrid Demos",
//							    ExpandColumn: "routineName",
//							    //autowidth: true,
//							    rowNum: 10000,
//							    //ExpandColClick: true,
//							    treeIcons: {leaf:'ui-icon-document-b'},
//							    jsonReader: {
//							        repeatitems: false,
//							        root: "response"
//							    }
//							});
						  // reload Grid for datastr instead of URL value
						  $("#remoteinfinite").setGridParam({datastr: subChildData,datatype: "jsonstring"}).trigger('reloadGrid');
						 
						 
					 }
				 });
		 
	 }
}

function loadTableData(){
	var Collection = Backbone.Collection.extend({
	 url : function(){
	 return _context +"/load";
	 }
	 });
	 var coll = new Collection();
	 
	 coll.fetch({
		 type: 'GET', contentType : 'application/json',
		 success : function() {
		 
		 
		 coll.each(function(clientData, i){
				 jQuery("#tree").jqGrid('addRowData', (i + 1),
				 clientData.toJSON());
		 });
		 $("#tree").trigger("reloadGrid");
		 }
	 });
	 
}

$(document).ready(function() {
	loadDummyTable();
	 loadEmptyGrid();
	 loadTableData();
	 loadTreeGrid();
	 
});
