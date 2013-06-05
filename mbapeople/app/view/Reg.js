Ext.define('MBAPeople.view.Reg', {
    extend:'Ext.form.Panel',
    alias:'widget.reg',
    requires:[
        'Ext.field.Number',
        'Ext.field.Select'
    ],

    config:{
        padding:'10px',
        scrollable:true,
        masked:{
            xtype:'loadmask',
            message:'读取中...'
        },
        items:[
            {
                xtype:'toolbar',
                docked:'top',
                items:[
                    {
                        xtype:'button',
                        itemId:'RegBackBtn',
                        ui:'decline',
                        iconMask:true,
                        text:'返回登录'
                    },
                    {
                        xtype:'spacer'
                    },
                    {
                        xtype:'button',
                        itemId:'RegBtn',
                        ui:'action',
                        iconCls:'',
                        iconMask:true,
                        text:'认证注册'
                    }
                ]
            },

            {
                xtype:'fieldset',
                required:true,
                instructions: '我们将发送激活码邮件到您的邮箱，请获取激活码后完成注册。',
                items:[
                    {
                        xtype:'textfield',
                        itemId:'account',
                        id:'regaccount',
                        label:'@sem.tsinghua.edu.cn',
                        name:'account',
                        labelAlign: 'right',
                        required:true,
                        placeHolder:'MBA邮箱',
                        readOnly:false
                    },
                    {
                        xtype:'textfield',
                        itemId:'verifycode',
                        id:'verifycode',
                        name:'verifycode',
                        required:true,
                        placeHolder:'激活码',
                        readOnly:false
                    },
                    {
                        xtype: 'button',
                        itemId: 'vertifyBtn',
                        ui: 'round',
                        iconCls: '',
                        margin: '10px 0 0 0',
                        iconMask: true,
                        text: '还没激活码？点击获取'
                    },
                    {
                        xtype: 'button',
                        id: 'regBtn',
                        itemId: 'regBtn',
                        margin: '10px 0 0 0',
                        ui: 'action-round',
                        iconMask: true,
                        text: '认证注册'
                    }
                ]
            }
        ]
    },

    getValidationErrors:function () {
        var errors = [];
        var reqFields = this.query('field[required=true]');
        var i = 0, ln = reqFields.length, field;
        for (; i < ln; i++) {
            field = reqFields[i];
            if (!field.getValue()) {
                errors.push(field.getLabel() + '不能为空');
            }
        }
        return errors;
    },
    getJsonValues:function () {
        //text filed
        var reqFields = this.query('field');
        var i = 0, ln = reqFields.length, field, value;
        var ed = Ext.create('MBAPeople.model.UserModel');
        for (; i < ln; i++) {
            field = reqFields[i].getItemId();
            value = reqFields[i].getValue();
            if (value != '') {
                ed.set(field, value);
            }
        }
        var re = Ext.JSON.encode(ed.getData());
        return(re);
    }
})
;