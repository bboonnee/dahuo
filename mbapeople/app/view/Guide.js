Ext.define('MBAPeople.view.Guide', {
    extend:'Ext.Carousel',
    alias:'widget.guide',
    config:{
        defaults: {
            styleHtmlContent: true
        },
        items: [
           /* {
                html : 'SEM',
                html : '<img src="resources/img/renmai.png"  width="250" height="250" /></a>',
                style: 'background-color: #5E99CC'
            },*/
            {
                html : '搜索',
                html : '<img src="resources/img/search.png"  width="250" height="250" /></a>',
                style: 'background-color: #5E99CC'
            },
            {
                html : '班级列表',
                html : '<img src="resources/img/classlist.png" width="250" height="250" /></a>',
                style: 'background-color: #5E99CC'
            },
            {
                html : '资料',
                html : '<img src="resources/img/info.png" width="250" height="250" /></a>',
                style: 'background-color: #5E99CC'
            },
            {
                html : '设置',
                html : '<img src="resources/img/setting.png" width="250" height="250" /></a>',
                style: 'background-color: #5E99CC'
            },
            {
                //html: '<table width="100%" style="background-image:url(resources/img/sem.png);background-position:right center;background-repeat:no-repeat;"></table>',
                style: 'background-color: #5E99CC',
                items:[
                    {
                        xtype:'image',
                        itemId:'enterBtn',
                        src:'resources/img/sem.png',
                        height:"100%",
                        html:'点击进入'
                    }
                ]
               // style: 'background-image:url(resources/img/sem.png)'
            }

        ]


    }


});