

Ext.define('MBAPeople.view.ClassList', {
    extend: 'Ext.dataview.List',
    alias: 'widget.classlist',

    config: {
        scrollable: 'vertical',
        emptyText: '没有读取到班级列表',
        inline: true,
        grouped: true,
        store: 'ClassStore',
        itemTpl: [
            '<div>{startyear}  {classno}</div>'
        ],
        items:[
            {
                xtype:'toolbar',
                docked:'top',
                items:[
                    {
                        xtype:'button',
                        itemId:'infoBackBtn',
                        ui:'plain',
                        iconCls: 'home',
                        iconMask: true
                    },
                    {
                        xtype:'searchfield',
                        itemId:'classfilterfield',
                        width:'60%',
                        placeHolder:'筛选班级',
                        listeners:{
                            clearicontap: function() {
                                if (!this.disabled) {
                                    this.setValue('');
                                    var du = Ext.StoreManager.lookup('ClassStore');
                                    du.clearFilter();
                                }
                            }
                        }
                    }
                ]
            }
        ]
    },

    loadData:function (record) {
        var du = Ext.StoreMgr.lookup('ClassStore');
        setMasked(true);
        du.load(function (records, operation, success) {
            setMasked(false);
        }, this);
        if (!du.getCount()) {
            du.add({classno:'11P2'});
        }
    }
});