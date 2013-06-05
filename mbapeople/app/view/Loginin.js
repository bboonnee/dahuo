Ext.define('MBAPeople.view.Loginin', {
    extend:'Ext.form.Panel',
    alias:'widget.loginin',
    requires:[
        'Ext.field.Text',
        'Ext.field.Password',
        'MBAPeople.view.RegMail',
    ],
    config:{
        id:'loginin',
        padding:'10px',
        scrollable:true,
        masked:{
            xtype:'loadmask',
            message:'登陆中..'
        },
        listeners:{
            'initialize':function (carousel) {
                this.setMasked(false);
                //console.dir(Ext.StoreMgr.lookup('LocalStore').getData().getAt(0));
                if (Ext.StoreMgr.lookup('LocalStore').getData().getAt(0)) {
                    this.down('#mobile').setValue(Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('mobile'));
                }
            }
        },
        items:[
            {
                xtype:'toolbar',
                docked:'top',
                items:[
                    {
                        xtype:'label',
                        style:'font: 24px Arial white;color:white',
                        html:'MBA人脉',
                        centered:true
                    }
                ]
            },

            {
                xtype:'numberfield',
                itemId:'mobile',
                margin:'10px 0 0 0',
                //label: '手机号',
                placeHolder:'登录手机',
                labelAlign:'left',
                name:'mobile',
                required:true,
                ui:'plain',
                readOnly:false
            },
            {
                xtype:'passwordfield',
                itemId:'password',
                margin:'10px 0 0 0',
                //label: '密码',
                placeHolder:'密码',
                labelAlign:'left',
                name:'password',
                required:true,
                ui:'plain',
                readOnly:false
            },
            {
                xtype:'spacer'
            },
            {
                xtype:'button',
                itemId:'loginBtn',
                ui:'action-round',
                margin:'10px 0 0 0',
                iconCls:'',
                iconMask:true,
                text:'登录'
            },
            {
                xtype:'spacer'
            },
            {
                xtype:'button',
                itemId:'registermailBtn',
                margin:'10px 0 0 0',
                ui:'round',
                iconMask:true,
                text:'邮件验证注册'
            },
            {
                xtype:'spacer'
            },
            {
                xtype:'button',
                itemId:'registerBtn',
                margin:'10px 0 0 0',
                ui:'round',
                iconMask:true,
                text:'资料验证注册'
            },
            {
                xtype:'spacer'
            },
            {
                xtype:"label",
                margin:"10px",
                html:'<p style="text-align: right;">version:1.1.1</p>'
            },
            {
                xtype:"label",
                margin:"10px",
                html:'<a href="http://54.251.118.17/down/thumba.apk" target="_blank"><img src="resources/img/android_small.png" width="40" height="40" /></a> ' +
                    ' <a href="https://itunes.apple.com/us/app/mba-ren-mai/id620438205" target="_blank"><img src="resources/img/iphone_small.png" width="40" height="40" /></a>'
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
                errors.push(field.getPlaceHolder() + '不能为空.');
            } else if (field.getItemId() == 'mobile') {
                if (field.getValue() < 10000000000) {
                    errors.push('手机号码格式不正确.');
                }
            }
        }
        return errors;
    }

});