Ext.define('MBAPeople.view.RegMail', {
    extend:'Ext.form.Panel',
    alias:'widget.regmail',
    requires:[
        'Ext.field.Number',
        'Ext.field.Select'
    ],
    config:{
        padding:'10px',
        scrollable:true,
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
                ]
            },

            {
                xtype:'fieldset',
                required:true,
                instructions: '我们将发送密码邮件到您的邮箱，请获取密码后登录',
                items:[
                    {
                        xtype: 'numberfield',
                        itemId: 'mobile',
                        margin: '10px 0 0 0',
                        placeHolder:'手机号码',
                        label:'手机',
                        labelAlign: 'right',
                        name: 'mobile',
                        required: true
                    },
                    {
                        xtype:'textfield',
                        itemId:'account',
                        label:'@sem.tsinghua.edu.cn',
                        name:'account',
                        labelAlign: 'right',
                        required:true,
                        placeHolder:'MBA邮箱'
                    },
                    {
                        xtype: 'button',
                        itemId: 'regmailBtn',
                        margin: '10px 0 0 0',
                        ui: 'action-round',
                        iconMask: true,
                        text: '注册'
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
                errors.push(field.getPlaceHolder() + '不能为空');
            }else if(field.getItemId()=='mobile')
            {
                if(field.getValue()<10000000000)
                {
                    errors.push('手机号码格式不正确.');
                }
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