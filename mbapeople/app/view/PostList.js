var postTemplate = new Ext.XTemplate(
    '<table width="100%" border="0"><tr>',
    '<td width="40" rowspan="3"><img src="resources/img/tasklist.png" width="36" height="36" />',
    '</td><td >{title}</td></tr><tr>',
    '<td class="classno">{author}</td></tr> <tr>',
    '<td>{posttime}</td ><td colspan="2"></td></tr> </table>'
);
Ext.define('MBAPeople.view.PostList', {
    extend:'Ext.dataview.List',
    alias:'widget.postlist',
    requires:[
        'Ext.plugin.PullRefresh', 'Ext.plugin.ListPaging'
    ],

    config:{
        style:'border: none; font: 13px Arial black',
        scrollable:'vertical',
        emptyText:'没有帖子',
        store:'PostStore',
        ui:'round',
        itemTpl:postTemplate,
        /*plugins:[
          *//*  {
                type:'listpaging',
                autoPaging:false,
                loadMoreText:'更多...',
                noMoreRecordsText:'没有更新记录了'
            },*//*
            {
                type:'pullrefresh',
                itemId:'pullrefresh',
                pullRefreshText:'下拉可以更新',
                releaseRefreshText:'松开开始更新',
                loadingText:'正在刷新...',
                refreshFn:null
            }
        ]*/

    }

});