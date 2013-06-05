Ext.define('MBAPeople.controller.Setting', {
    extend:'MBAPeople.controller.Contacts',
    config:{
        control:{
            "settinglist":{
                itemtap:'onSettinglistItemTap'
            },
            "button#savePasswordBtn":{
                tap:'onSavePasswordBtnTap'
            },            "button#problemBtn":{
                tap:'onProblemBtnTap'
            },
            "button#backSuggestionBtn":{
                tap:'onBackSuggestionBtnTap'
            },
            "button#saveSuggestionBtn":{
                tap:'onSaveSuggestionBtnTap'
            },
        }
    },
    onSettinglistItemTap:function (dataview, index, target, record, e, options) {
        switch (record.data.name) {
            case '个人资料':
                this.onEditInfoBtnTap();
                break;
            case '修改密码':
                var form = this.getChangepassword();
                Ext.Viewport.setActiveItem(form);
                //this.redirectTo('changepass');
                /* this.getApplication().getHistory().add(Ext.create('Ext.app.Action', {
                 url:'changepass'
                 }));*/

                break;
            case '退出登录':
                this.onLoginOutBtnTap();
                break;
            case '使用帮助':
                this.gotGuideView();
                break;
            case '问题反馈':
                this.onProblemBtnTap();
                break;
            case '版本更新说明':
                //create the navigation view and add it into the Ext.Viewport
                var view = this.getAbout();
                Ext.Viewport.setActiveItem(view);
                break;
        }
    }, //setting form to edit the info form
    //setting button tap
    gotGuideView:function (record) {
        var info = this.getGuide();
        Ext.Viewport.setActiveItem(info);
    },
    onLoginOutBtnTap:function (button, e, options) {
        this.loginout();
    },
    onProblemBtnTap:function (button, e, options) {
        var sg = this.getSuggestion();
        sg.clearField();
        Ext.Viewport.setActiveItem(sg);
    },
    //
    onSavePasswordBtnTap:function (button, e, options) {
        //console.log('onSavePasswordBtnTap');
        var form = this.getChangepassword();
        var main = this.getMainview();
        var errors = form.getValidationErrors();
        if (errors.length) {
            Ext.Msg.alert('注意：', errors.join('<br/>'));
        } else {
            //如果可以读到model的值，否则新增
            var updatestore = Ext.StoreMgr.lookup('UserUpdateStore');
            var p = updatestore.getProxy();
            if (!p) {
                //console.log('proxy is null');
            } else {
                p.setExtraParams({'do':'changepassword', "password":form.down('#newpassword').getValue(),
                    'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'),
                    'device':Ext.os.name});
                updatestore.load(function (records, operation, success) {
                    if (!success) {
                        Ext.Msg.alert('连接服务器失败', '请检查网络后重试');
                    } else {
                        Ext.Msg.alert('密码修改成功', '', function (btn) {
                            if (btn == 'ok') {
                                Ext.Viewport.setActiveItem(main);
                            } else {
                                return false;
                            }
                        });
                    }
                }, this);
            }
        }
    },
    onSaveSuggestionBtnTap:function (button, e, options) {
        var info = this.getSuggestion();
        var values = info.getValues();
        var updatestore = Ext.StoreMgr.lookup('UserUpdateStore');
        var p = updatestore.getProxy();
        if (!p) {
            //console.log('proxy is null');
        } else {
            p.setExtraParams({'do':'addsuggestion', "content":values['content'],
                "uid":Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('id'),
                'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'),
                'device':Ext.os.name});
            updatestore.load(function (records, operation, success) {
                //console.dir(records);
                if (records[0].get('name')) {
                    //console.log('updatestore finish');
                    Ext.Msg.alert('提交成功，感谢您的支持和建议，我们会努力提供更好的产品给您', '', Ext.emptyFn);
                    this.BackBtnTap();
                } else {
                    Ext.Msg.alert('保存失败', '', Ext.emptyFn);
                    //this.onInfoBackBtnTap();
                }
            }, this);
        }

    },
    onBackSuggestionBtnTap:function (button, e, options) {
        this.BackBtnTap();
    },
});