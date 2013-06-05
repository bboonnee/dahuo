var myTemplate = new Ext.XTemplate(
    '<table width="100%" border="0"><tr>',
    '<td width="40" rowspan="3">',
    '<img src="http://54.251.118.17/avatar/{account}.png" onerror="noimage(this)" width="36" "/>',
    '</td><td width="38%">{author}</td>',
    '<td class="classno">{content}</td></tr> <tr>',
    ' <td colspan="2">{date}</td> </tr></table>'
);

Ext.define('MBAPeople.view.CommentList', {
    extend:'Ext.dataview.List',
    alias:'widget.commentlist',

    config:{
        style:'border: 1; font: 11px Arial',
        ui:'round',
        cls:'Setting',
        emptyText:'暂时没有评论',
        //store:'CommentStore',
        //disableSelection:true,
        itemTpl:myTemplate,
        scrollable:{
            scroller:{
                disabled:true
            }
        }
    },
    refreshlist:function (postid) {
        var comment = Ext.create('MBAPeople.store.CommentStore');
        comment.clearData();
        var p = comment.getProxy();
        p.setExtraParams({'do':'getcommentlist', 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'), 'postid':postid, 'device':Ext.os.name});
        comment.load(function (records, operation, success) {
            //读取comment成功则跳转到main
            if (!success) {
                this.setStore(comment);
                //Ext.Msg.alert('网络读取评论数据失败：', '请检查网络连接');
            } else {
                this.setStore(comment);
                var itemheight = 60;
                this.setHeight(this.getStore().getData().items.length * itemheight);
            }
        }, this);
    }


});