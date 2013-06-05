Ext.define('MBAPeople.view.ChangePassword', {
    extend:'Ext.form.Panel',
    alias:'widget.changepassword',
    config:{
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
                        ui:'plain',
                        iconCls:'home',
                        iconMask:true,
                        text:'返回',
                        handler: function() {
                            //history.back();
                        }
                    },
                    {
                        xtype:'label',
                        centered:true,
                        html:'修改密码'
                    },
                ]
            },
            {
                xtype: 'passwordfield',
                itemId: 'newpassword',
                margin: '10px 0 0 0',
                placeHolder:'请设置登录密码',
                labelAlign: 'left',
                name: 'password',
                required: true,
                ui: 'plain',
                readOnly: false
            },
            {
                xtype: 'passwordfield',
                itemId: 'confirmpassword',
                margin: '10px 0 0 0',
                placeHolder:'请再次输入',
                labelAlign: 'left',
                name: 'password',
                required: true,
                ui: 'plain',
                readOnly: false
            },
            {
                xtype:'button',
                itemId:'savePasswordBtn',
                ui:'action',
                height:'35px',
                text:'完成'
            }
        ]
    },
    getValidationErrors:function () {
        var errors = [];
        var reqFields = this.query('field[required=true]');
        var i = 0, ln = reqFields.length, field,newpass,confirmpass;
        for (; i < ln; i++) {
            field = reqFields[i];
            if (!field.getValue()) {
                errors.push(field.getPlaceHolder() + '不能为空');
            }else if(field.getItemId()=='newpassword')
            {
                newpass =field.getValue();
            }
            else if(field.getItemId()=='confirmpassword')
            {
                confirmpass =field.getValue();
            }
        }
        if(newpass!=confirmpass)
        {
            errors.push('两次输入密码不同，请重新输入');
        }
        return errors;
    }
});