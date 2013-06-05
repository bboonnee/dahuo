Ext.define('MBAPeople.view.PostForm', {
    extend:'Ext.form.Panel',
    alias:'widget.postform',

    config:{
        items:[
            {
                xtype:'toolbar',
                docked:'top',
                items:[
                    {
                        xtype:'button',
                        itemId:'postformBackBtn',
                        ui:'plain',
                        iconCls:'home',
                        iconMask:true
                    },
                    {
                        xtype:'button',
                        itemId:'savePostbtn',
                        right:'0%',
                        top:'10%',
                        text:'发表'
                    }
                ]
            },
            {
                xtype:'fieldset',
                items:[
                    {
                        xtype:'textfield',
                        itemId:'title',
                        clearIcon:false,
                        label:'标题',
                        labelAlign:'left',
                        required:true,
                        name:'title'
                    },
                    {
                        xtype:'textfield',
                        itemId:'otherauthor',
                        label:'发布人',
                        labelAlign:'left',
                        hidden:'true'
                    },
                    {
                        xtype:'textfield',
                        itemId:'otherauthorid',
                        label:'发布人账号',
                        labelAlign:'left',
                        hidden:'true'
                    }
                ]
            },
            {
                xtype:'textfield',
                itemId:'id',
                hidden:true,
                name:'id'
            },
            {
                xtype:'textareafield',
                itemId:'content',
                clearIcon:false,
                required:true,
                label:'内容',
                maxRows:10,
                labelAlign:'left',
                name:'content'
            },
            {
                xtype:'fieldset',
                items:[
                    {
                        xtype:'selectfield',
                        label:'分类',
                        itemId:'type',
                        name:'type',
                        options:[
                            {text:'通知', value:'notice'},
                            {text:'帮帮忙', value:'help'},
                            {text:'活动', value:'org'},
                            {text:'合作', value:'coop'},
                            {text:'求职招聘', value:'job'},
                            {text:'广告推广', value:'ads'},

                        ]
                    },
                    {
                        xtype:'selectfield',
                        label:'发送圈子',
                        itemId:'catalog',
                        name:'catalog',
                        options:[
                            {text:'公开', value:'public'},
                            {text:'本班', value:'class'},
                        ]
                    }
                ]
            },
            {
                xtype:'button',
                itemId:'savePostbtn',
                text:'发表'
            },
            {
                xtype:'button',
                itemId:'deltePostbtn',
                ui:'decline',
                hidden:true,
                text:'删除'
            }

        ]
    },
    setHiddenfield:function (owner) {
        if(owner)
        {
            this.down('#deltePostbtn').setHidden(false);
        }else
        {
            this.down('#deltePostbtn').setHidden(true);
        }
        if (Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account') == 'yib.11') {
            this.down('#otherauthor').setHidden(false);
            this.down('#otherauthorid').setHidden(false);
        }
        else {
            this.down('#otherauthor').setHidden(true);
            this.down('#otherauthorid').setHidden(true);
        }
    },
    clearField:function () {
        this.down('#title').setValue('');
        this.down('#content').setValue('');
        this.down('#title').blur();
        this.down('#content').blur();

        this.down('#id').setValue('');
        this.down('#id').blur();

    },
    getValues:function () {
        //text filed
        var reqFields = this.query('field');
        var i = 0, ln = reqFields.length, field, value;
        var ed = Ext.create('MBAPeople.model.PostModel');
        for (; i < ln; i++) {
            field = reqFields[i].getItemId();
            value = reqFields[i].getValue();
            if (value != '') {
                ed.set(field, value);
                // console.log(field + "  is " + value + " " + i);
            }
        }
        var localstore = Ext.StoreMgr.lookup('LocalStore').getData().getAt(0);
        if (localstore) {
            ed.set('author', autoLogin = localstore.getData()['name']);
            //ed.set('id', "");
            ed.set('author_id', userAccount = localstore.getData()['account']);
        }
        var re = Ext.JSON.encode(ed.getData());
        return(re);
    },
    getValidationErrors:function () {
        var errors = [];
        var reqFields = this.query('field[required=true]');
        var i = 0, ln = reqFields.length, field;
        for (; i < ln; i++) {
            field = reqFields[i];
            if (!field.getValue()) {
                errors.push(field.getLabel() + '不能为空');
            } else if (field.getItemId() == 'mobile') {
                if (field.getValue() < 10000000000) {
                    errors.push('手机号码格式不正确.');
                }
            }
        }
        return errors;
    },
    setRecord:function (record) {
        this.callParent(arguments);
        if (record) {
            var data = record.data;
            this.setData(data);
            var reqFields = this.query('field');
            var i = 0, ln = reqFields.length, field;
            for (; i < ln; i++) {
                field = record.get(reqFields[i].getItemId());
                reqFields[i].setValue(field);
            }
        }
    },
});