var myTemplate = new Ext.XTemplate(
    '<table width="100%" border="0"><tr><td class="post_title">{post_title}</td><td rowspan="2"><img src="resources/img/{icon}" width="100" height="40" align="right" /></td></tr><tr>' +
        '<td class="brief">{brief}</td></tr></table>'
);


Ext.define('MBAPeople.view.AdList', {
    extend:'Ext.dataview.List',
    alias:'widget.adlist',
    config:{
        style:'border: none; font: 13px Arial black',
        ui:'round',
        cls:'Setting',
        emptyText:'No Result',
        store:'AdStore',
        disableSelection:true,
        itemTpl:myTemplate
        //[            '<div>{name} {mobile} {classno} {company}</div>'        ]
    }
});