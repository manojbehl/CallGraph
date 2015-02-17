/**
 * 
 */
var subChildData = null;
function loadDummyTable(){
	var topicjson ={"response": [{"id":"54df0ce70364853700354912","routineName":"PDJA016B",level:"0",parent:"0",isLeaf:false,expanded:true,loaded:true},{"id":"null","routineName":"BJAC027A",level:"1",parent:"1",isLeaf:false,expanded:true,loaded:true},{"id":"null","routineName":"BQMQFM31",level:"2",parent:"2",isLeaf:true,expanded:true,loaded:true},{"id":"null","routineName":"BQMQFM23",level:"2",parent:"2",isLeaf:true,expanded:true,loaded:true},{"id":"null","routineName":"BQMQFM25",level:"2",parent:"2",isLeaf:true,expanded:true,loaded:true},]}
	
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
		"height":"300",
		"sortname":"routineName",
		"scrollrows":true,
		"scroll":true,
		"treeGrid":false,
		"treedatatype":"json",
		"treeGridModel":"nested",
		"loadonce":false,
		rowNum:550, 
//		rowList:[10,20,30],
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
	
	
	jQuery("#tree")
	.navGrid('#pager',{edit:true,add:false,del:false,search:true, csv:true})
	.navButtonAdd('#pager',{
	   caption:"Export To Excel", 
//	   buttonicon:"ui-icon-add", 
	   onClickButton: function(){ 
		   $("#dialog-confirm").dialog({
	            height:300,
	            modal:true,
	            buttons:{
	                'Cancel': function(){
	                    $(this).dialog('close');
	                },
	                'Confirm': function(){
	                	
	                	jQuery.ajax(
	           				 {
	           					 url: _context+"/writeExcel?filePath=C:\\workspace", 
	           					 success: function(result){
	           						 alert("Exported Successfuly");
	           						$(this).dialog('close');
	           					 }
	           					 
	           				 });
	                }
	            }
	        });
	   }, 
	   position:"last"
	});
	
}

function populateAdditionalInfo(reqId,reqType){
	var selRowId = jQuery("#tree").jqGrid('getGridParam', 'selrow');
	 if(selRowId!=null && selRowId!=""){
		 
		 var cellValue = jQuery("#tree").jqGrid ('getCell', selRowId, 'id');
		 $("#remoteinfinite").jqGrid("clearGridData", true).trigger("reloadGrid");
		 jQuery.ajax(
				 {
					 url: _context+"/loadChild?id="+cellValue, 
					 success: function(result){
						  subChildData = result.toString();
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
