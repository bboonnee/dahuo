Ext.define('MBAPeople.view.Suggestion', {
    extend:'Ext.form.Panel',
    alias:'widget.suggestion',
    config:{
        padding:'10px',
        style:'font: 14px Arial black',

        tabBar:{
            border:'',
            docked:'top',
            layout:{
                pack:'center',
                type:'hbox'
            }
        },
        items:[
            {
                xtype:'toolbar',
                docked:'top',
                items:[
                    {
                        xtype:'button',
                        itemId:'backSuggestionBtn',
                        ui:'action',
                        iconCls:'',
                        iconMask:true,
                        text:'返回'
                    },
                    {
                        xtype:'label',
                        html:'意见反馈'
                    },
                    {
                        xtype:'button',
                        itemId:'saveSuggestionBtn',
                        ui:'action',
                        iconCls:'',
                        iconMask:true,
                        text:'提交反馈',
                        right:'0%',
                        top:'15%'
                    }
                ]
            },
            {   xtype:'textareafield',
                maxRows:15,
                name:'content',
                itemId:'content',
                PlaceHolder:'请输入您的反馈意见'
            },
            {
                xtype:'button',
                itemId:'saveSuggestionBtn',
                ui:'action',
                height:'35px',
                text:'提交反馈'
            }
        ]
    },

    setRecord:function (record) {
        this.callParent(arguments);
        if (record) {
            //var name = record.get('name') + ' ' + (record.get('lastName') || '');
            var name = record.get('name');
            var isFavorite = record.get('isFavorite');
            this.down('#nameTxt').setHtml(name);
            //this.down('#favoriteBtn')[isFavorite ? 'addCls' : 'removeCls']('x-button-pressed');
            //this.down('contactpic').setData(record.data);
        }
    },
    clearField:function () {
        this.down('#content').setValue('');
        this.down('#content').blur();
    }

});