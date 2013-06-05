var myTemplate = new Ext.XTemplate(
    '<table width="100%" border="0"><tr>',
    '<td width="40" rowspan="3">' ,
    '<tpl if="picture==\'avatar\'">',
    '<img src="resources/img/ic_contact.png" onerror="noimage(this)" width="36" "/>',
    '<tpl else>',
    '<img src="http://54.251.118.17/avatar/{account}.png" onerror="noimage(this)" width="36" "/>',
    '</tpl>',
    '</td><td width="38%">{name}</td>',
    '<td class="classno">{classno}</td></tr> <tr>',
    ' <td colspan="2">{mobile}</td> </tr><tr>',
    '<td colspan="2">{company}</td></tr> </table>'
);

Ext.define('MBAPeople.view.UserList', {
    extend:'Ext.dataview.List',
    alias:'widget.userlist',

    config:{
        style:'border: none; font: 13px Arial black',
        ui:'round',
        cls:'Setting',
        emptyText:'No Result',
        store:'UserStore',
        disableSelection:true,
        itemTpl:myTemplate
        //[            '<div>{name} {mobile} {classno} {company}</div>'        ]
    }
});