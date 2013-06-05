Ext.define('MBAPeople.view.Reginfo', {
    extend:'Ext.form.Panel',
    alias:'widget.reginfo',
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
                        itemId:'saveReginfoBtn',
                        ui:'action',
                        iconCls:'',
                        iconMask:true,
                        text:'完成注册'
                    }
                ]
            },

            {
                xtype:'fieldset',
                required:true,
                items:[
                    {
                        xtype:'textfield',
                        itemId:'name',
                        margin:'10px 0 0 0',
                        label:'姓名',
                        name:'name',
                        required:true,
                        placeHolder:'真实姓名',
                        readOnly:false
                    },
                    {
                        xtype:'selectfield',
                        itemId:'startyear',
                        name:'startyear',
                        label:'入学年份',
                        displayField:"startyear",
                        valueField:"startyear",
                        required:true,
                        triggerAction:"all",
                        listeners:{
                            change:function (selectbox, newValue, oldValue) {
                                var comboCity = Ext.getCmp('classno');
                                var store = comboCity.getStore().filter('startyear', this.getValue());
                            }
                        }
                    },

                    {
                        xtype:'selectfield',
                        itemId:'classno',
                        name:'classno',
                        id:'classno',
                        label:'班级',
                        store:'ClassStore',
                        displayField:"classno",
                        valueField:"classno",
                        required:true,
                        triggerAction:"all"
                    },
                    {
                        xtype:'textfield',
                        itemId:'account',
                        label:'MBA账号',
                        name:'account',
                        required:true,
                        placeHolder:'如yib.11,不要写邮箱@后缀',
                        readOnly:false
                    }
                ]
            },
            {
                xtype:'fieldset',
                required:true,
                items:[
                    {
                        xtype:'textfield',
                        itemId:'mobile',
                        label:'手机',
                        name:'mobile',
                        required:true,
                        placeHolder:'手机是您的登录账号',
                        readOnly:false
                    },
                    {
                        xtype:'spacer'
                    },
                    {
                        xtype:'passwordfield',
                        id:'password',
                        itemId:'password',
                        label:'密码',
                        name:'password',
                        required:true,
                        placeHolder:'设置登录密码',
                        readOnly:false
                    }
                ]
            } ,
            {
                xtype:'fieldset',
                items:[
                    {
                        xtype:'selectfield',
                        label:'权限',
                        itemId:'permission',
                        name:'permission',
                        options:[
                            {text:'允许MBA同学公开查找我的名片', value:'999'},
                            {text:'仅允许同年级同学查看我的名片', value:'11'}

                        ]
                    }
                ]
            }

        ]
    },
    setReadonly:function (readonly) {
        var reqFields = this.query('field');
        //console.dir(reqFields);
        var i = 0, ln = reqFields.length, field;
        for (; i < ln; i++) {
            if (reqFields[i].getItemId() == 'account')
            {
                reqFields[i].setDisabled(true);
                continue;
            } else if ((reqFields[i].getItemId() == 'name')
                || (reqFields[i].getItemId() == 'classno')
                || (reqFields[i].getItemId() == 'startyear'))
            {
                reqFields[i].setReadOnly(readonly);
                reqFields[i].setDisabled(readonly);
            }
            reqFields[i].setPlaceHolder("请填写");
        }
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
    },
    getStartyear:function () {
        var classstore = Ext.data.StoreManager.lookup('ClassStore');
        var newstore = Ext.data.StoreManager.lookup('ClassStore');
        var B = new Array();
        var classnum, lastclassnum, j = 0;
        for (var i = 0; i < classstore.getCount(); i++) { // loop through store records
            classnum = classstore.getAt(i).data['startyear']; //grab the value for the series field
            if (classnum != lastclassnum) {
                B[j] = {"startyear":classnum, "startyear":classnum};
                j = j + 1;
                lastclassnum = classnum;
            }
        }
        this.down('#startyear').setOptions(B);
    }
})
;