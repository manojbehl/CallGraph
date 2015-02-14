/**
 * 
 */
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
				"label":"frequency",
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
		"loadonce":false,
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
		"datatype":"json",
		"pager":"#pager",
		caption: "Full control"
			
		
	});
	
	jQuery('#tree').jqGrid('navGrid','#pager',
			{
				"edit":true,
				"add":true,
				"del":true,
				"search":true,
				"refresh":true,
				"view":true,
				"excel":true,
				"pdf":true,
				"csv":true,
				"columns":false
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
	 loadEmptyGrid();
	 loadTableData();
	 
});
