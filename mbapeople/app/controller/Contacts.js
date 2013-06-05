Ext.define('MBAPeople.controller.Contacts', {
    extend:'Ext.app.Controller',

    config:{
        stores:[
            'ClassStore'
        ],
        refs:{
            posthtml:{
                autoCreate:true,
                selector:'posthtml',
                xtype:'posthtml'
            },
            mainview:{
                autoCreate:true,
                selector:'mainview',
                xtype:'mainview'
            },
            userlist:{
                autoCreate:true,
                selector:'userlist',
                xtype:'userlist'
            },
            postlist:{
                autoCreate:true,
                selector:'postlist',
                xtype:'postlist'
            },
            infotab:{
                autoCreate:true,
                selector:'infotab',
                xtype:'infotab'
            },
            settinglist:{
                autoCreate:true,
                selector:'settinglist',
                xtype:'settinglist'
            },
            loginin:{
                autoCreate:true,
                selector:'loginin',
                xtype:'loginin'
            },

            suggestion:{
                autoCreate:true,
                selector:'suggestion',
                xtype:'suggestion'
            },
            register:{
                autoCreate:true,
                selector:'register',
                xtype:'register'
            },
            reg:{
                autoCreate:true,
                selector:'reg',
                xtype:'reg'
            },
            regmail:{
                autoCreate:true,
                selector:'regmail',
                xtype:'regmail'
            },
            reginfo:{
                autoCreate:true,
                selector:'reginfo',
                xtype:'reginfo'
            },
            classlist:{
                autoCreate:true,
                selector:'classlist',
                xtype:'classlist'
            },
            postform:{
                autoCreate:true,
                selector:'postform',
                xtype:'postform'
            },
            guide:{
                autoCreate:true,
                selector:'guide',
                xtype:'guide'
            },
            adlist:{
                autoCreate:true,
                selector:'adlist',
                xtype:'adlist'
            },
            about:{
                autoCreate:true,
                selector:'about',
                xtype:'about'
            },
            adpanel:{
                autoCreate:true,
                selector:'adpanel',
                xtype:'adpanel'
            },
            postpanel:{
                autoCreate:true,
                selector:'postpanel',
                xtype:'postpanel'
            },
            changepassword:{
                autoCreate:true,
                selector:'changepassword',
                xtype:'changepassword'
            },

            posthtml:{
                autoCreate:true,
                selector:'posthtml',
                xtype:'posthtml'
            },
            commentsas:{
                autoCreate:true,
                selector:'commentsas',
                xtype:'commentsas'
            },
            postStyleBubble:{
                autoCreate:true,
                selector:'postpicker',
                xtype:'postpicker'
            },
            postStyleList:'postpicker list',
            styleBubble:{
                autoCreate:true,
                selector:'stylepicker',
                xtype:'stylepicker'
            },
            styleList:'stylepicker list',
            fileBtn:'infotab #fileBtn'
        },
        control:{
            "button#infoBackBtn":{
                tap:'BackBtnTap'
            }
        }
    },
    onbackInfoFormBtnTap:function (button, e, options) {
        this.BackBtnTap();
    },
    BackBtnTap:function (button, e, options) {
        var main = this.getMainview();
        Ext.Viewport.setActiveItem(main);
    },
    onListActivate1:function (container, newActiveItem, oldActiveItem, options) {
        var du = Ext.StoreManager.lookup('UserStore');
        this.getStyleBubble().hide();
    },
    onEditInfoBtnTap:function (button, e, options) {
        var info = this.getInfotab();
        var record = Ext.StoreMgr.lookup('LocalStore').getData().getAt(0);
        //console.dir(record);
        if (record) {
            info.setRecord(record);
            info.setReadonly(false);
            Ext.Viewport.setActiveItem(info);
        }
    },
    // setting form to logout
    loginout:function () {
        var form = this.getLoginin();
        var localstore = Ext.StoreMgr.lookup('LocalStore');
        var record = localstore.getAt(0);
        record.set('autologin', false);
        //console.dir(record);
        record.dirty = true;
        //localstore.clearData();
        localstore.setData(record);
        localstore.sync();
        form.down('#mobile').setValue(Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('mobile'));
        Ext.Viewport.setActiveItem(form);
    },
})
;