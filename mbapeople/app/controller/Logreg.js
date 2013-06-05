Ext.define('MBAPeople.controller.Logreg', {
    extend:'MBAPeople.controller.Contacts',
    config:{
        control:{
            "image#enterBtn":{
                tap:'onEnter'
            },
            "button#loginBtn":{
                tap:'onloginBtnTap'
            },
            "button#registerBtn":{
                tap:'onregisterBtnTap'
            },
            "button#registermailBtn":{
                tap:'registermailBtn'
            },
            "button#RegBackBtn":{
                tap:'onRegBackBtnTap'
            },
            "button#saveRegBtn":{
                tap:'onSaveRegBtnTap'
            },
            "button#vertifyBtn":{
                tap:'onVertifyBtnTap'
            },
            "button#regmailBtn":{
                tap:'onRegmailBtnTap'
            },

            "button#regBtn":{
                tap:'onRegBtnTap'
            },
            "button#saveReginfoBtn":{
                tap:'onSaveReginfoBtnTap'
            },
        }
    },

    onloginBtnTap:function (button, e, options) {
        this.checkLogin();
    },

    // login form controller
    gotoMainView:function () {
        var form = this.getMainview();
        form.loadPostdata();
        Ext.Viewport.setActiveItem(form);
    },
    gotInfoView:function (record) {
        var info = this.getInfotab();
        var form = this.getMainview();
        form.loadData();
        info.setRecord(record);
        info.setReadonly(false);
        Ext.Viewport.setActiveItem(info);
    },
    onEnter:function (obj) {
        var load = sessionStorage.getItem('isnew');
        if (load == '1') {
            var info = this.getInfotab();
            sessionStorage.setItem('isnew', '0');
            Ext.Viewport.setActiveItem(info);
        } else {
            var info = this.getMainview();
            Ext.Viewport.setActiveItem(info);
        }
    },
    setInfoView:function (record) {
        var info = this.getInfotab();
        var form = this.getMainview();
        form.loadData();
        info.setRecord(record);
        info.setReadonly(false);
        //Ext.Viewport.setActiveItem(info);
    },

    onregisterBtnTap:function (button, e, options) {
        var form = this.getRegister();
        //var form = this.getReg();
        form.getStartyear();
        Ext.Viewport.setActiveItem(form);
    },
    registermailBtn:function (button, e, options) {
        var form = this.getRegmail();
        Ext.Viewport.setActiveItem(form);
    },
    onRegBackBtnTap:function (button, e, options) {
        var form = this.getLoginin();
        Ext.Viewport.setActiveItem(form);
    },
    //when the reg save button
    onSaveRegBtnTap:function (button, e, options) {
        //this.checkReg();
        this.regcheck();
    },
    //when the reg  button
    onVertifyBtnTap:function (button, e, options) {
        //this.checkReg();
        //console.dir('onVertifyBtnTap');
        this.regMail();
    },
    //when the reg  button
    onRegBtnTap:function (button, e, options) {
        //console.dir('onRegBtnTap');
        this.verifyCheck();
    },
    onRegmailBtnTap:function (button, e, options) {
        var form = this.getRegmail();
        var errors = form.getValidationErrors();
        if (errors.length) {
            Ext.Msg.alert('注意：', errors.join('<br/>'));
        } else {
            var account = form.down('#account').getValue();
            var mobile = form.down('#mobile').getValue();
            //如果可以读到model的值，否则新增
            var update = Ext.StoreMgr.lookup('UserUpdateStore');
            var p = update.getProxy();
            if (!p) {
                //console.log('proxy is null');
            } else {
                p.setExtraParams({'do':'sendregmail',
                    "mobile":mobile,
                    'account':account,
                    'device':Ext.os.name});
                update.load(function (records, operation, success) {
                    /* if (!success) {
                     Ext.Msg.alert('发送连接服务器失败', '请检查网络后重试');
                     }*/
                }, this);
                Ext.Msg.alert('正在将登录密码发送到经管邮箱', '请收到后按照账号密码登录');
                var login = this.getLoginin();
                Ext.Viewport.setActiveItem(login);
            }
        }
    },
    regcheck:function () {
        var form = this.getRegister();
        var errors = form.getValidationErrors();
        if (errors.length) {
            Ext.Msg.alert('注意：', errors.join('<br/>'));
        } else {
            var values = form.getJsonValues();
            //如果可以读到model的值，否则新增
            var updatestore = Ext.StoreMgr.lookup('UserUpdateStore');
            var p = updatestore.getProxy();
            if (!p) {
                //console.log('proxy is null');
            } else {
                form.setMasked(true);
                p.setExtraParams({'do':'regcheck',
                    "data":values,
                    'device':Ext.os.name});
                updatestore.load(function (records, operation, success) {
                    form.setMasked(false);
                    if (records[0].get('name')) {
                        //验证成功
                        records[0].set('autologin', true);
                        var local = Ext.StoreMgr.lookup('LocalStore');
                        local.setData(records[0]);
                        local.sync();
                        var cstore = Ext.StoreMgr.lookup('ClassStore');
                        cstore.clearFilter();
                        sessionStorage.setItem('isnew', '1');
                        Ext.Msg.alert('注册成功', '');
                        this.setInfoView(records[0]);
                        //this.gotGuideView();
                        this.onEnter();
                    } else {
                        Ext.Msg.alert('亲~认证有错误：', '检查下填写的各项资料');
                    }
                }, this);
            }
        }
    },
    onSaveReginfoBtnTap:function () {
        var form = this.getReginfo();
        var errors = form.getValidationErrors();
        if (errors.length) {
            Ext.Msg.alert('注意：', errors.join('<br/>'));
        } else {
            var values = form.getJsonValues();
            //如果可以读到model的值，否则新增
            var updatestore = Ext.StoreMgr.lookup('UserUpdateStore');
            var p = updatestore.getProxy();
            if (!p) {
                //console.log('proxy is null');
            } else {
                p.setExtraParams({'do':'regchecknewuser',
                    "data":values,
                    'device':Ext.os.name});
                updatestore.load(function (records, operation, success) {
                    if (records[0].get('name')) {
                        //验证成功
                        records[0].set('autologin', true);
                        var local = Ext.StoreMgr.lookup('LocalStore');
                        local.setData(records[0]);
                        local.sync();
                        var cstore = Ext.StoreMgr.lookup('ClassStore');
                        cstore.clearFilter();
                        sessionStorage.setItem('isnew', '1');
                        Ext.Msg.alert('注册成功', '');
                        this.setInfoView(records[0]);
                        //this.gotGuideView();
                        this.onEnter();
                    } else {
                        Ext.Msg.alert('系统里没有您的账户信息', '请发邮件给yib.11');
                    }
                }, this);
            }
        }
    },
    regMail:function () {
        var form = this.getReg();
        var email = Ext.getCmp('regaccount').getValue();
        if (email == "") {
            Ext.Msg.alert('邮箱账号不能为空', '请填写');
            return;
        }
        //如果可以读到model的值，否则新增
        var updatestore = Ext.StoreMgr.lookup('UserUpdateStore');
        var p = updatestore.getProxy();
        if (!p) {
            //console.log('proxy is null');
        } else {
            p.setExtraParams({'do':'regmail',
                "email":email,
                'device':Ext.os.name});

            updatestore.load(function (records, operation, success) {
                if (!success) {
                    Ext.Msg.alert('连接服务器失败', '请检查网络后重试');
                }
            }, this);
            Ext.Msg.alert('正在将验证码已发送到您的邮箱', '请收到邮件后填写验证码完成注册');
        }
    },
    //检测verify code是否正确，检测完成后查询此用户信息是否富足，不富足需要等级班级、手机、密码等信息
    verifyCheck:function () {
        var form = this.getReg();
        var errors = form.getValidationErrors();
        if (errors.length) {
            Ext.Msg.alert('注意：', errors.join('<br/>'));
        } else {
            var account = Ext.getCmp('regaccount').getValue();
            var code = Ext.getCmp('verifycode').getValue();
            //如果可以读到model的值，否则新增
            var updatestore = Ext.StoreMgr.lookup('UserUpdateStore');
            var p = updatestore.getProxy();
            if (!p) {
                //console.log('proxy is null');
            } else {
                p.setExtraParams({'do':'verifycheck',
                    "account":account,
                    "code":code,
                    'device':Ext.os.name});
                form.setMasked(true);
                updatestore.load(function (records, operation, success) {
                    //form.setMasked(false);
                    if (success) {
                        if (records[0].get('account')) {
                            //验证成功 读取用户信息，如果存在，则跳转到注册页面，不考虑不存在的问题，则需要填写信息,匹配到用户
                            var reginfo = this.getReginfo();
                            reginfo.getStartyear();
                            reginfo.setRecord(records[0]);
                            reginfo.down('#classno').setValue(records[0].get('classno'));
                            if (records[0].get('name')) {
                                reginfo.setReadonly(true);
                            } else {
                                reginfo.setReadonly(false);
                            }
                            Ext.Viewport.setActiveItem(reginfo);
                        } else {
                            Ext.Msg.alert('亲~验证码有错误：', '请检查下重试');
                        }
                    } else {
                        Ext.Msg.alert('连接服务器失败', '请检查网络后重试');
                    }
                }, this);
            }
        }
    },
    checkLogin:function () {
        var form = this.getLoginin();
        form.setMasked(true);
        var errors = form.getValidationErrors();
        var result = false;
        if (errors.length) {
            form.setMasked(false);
            Ext.Msg.alert('注意：', errors.join('<br/>'));
        } else {
            var du = Ext.StoreMgr.lookup('UserStore').getAt(0);
            var values = form.getValues();
            var record = form.getRecord();
            if (values) {
                var du = Ext.StoreMgr.lookup('UserStore');
                var p = du.getProxy();
                p.setExtraParams({'do':'loginin',
                    // "account":  values['account'],
                    "password":values['password'],
                    "mobile":values['mobile'],
                    'device':Ext.os.name});

                var b = du.isLoaded();
                du.load(function (records, operation, success) {
                    form.setMasked(false);
                    if (success) {
                        if (records[0].get('name')) {
                            //验证成功
                            records[0].set('autologin', true);
                            var local = Ext.StoreMgr.lookup('LocalStore');
                            local.clearData();
                            local.setData(records[0]);
                            local.sync();
                            this.gotoMainView();
                        } else {
                            Ext.Msg.alert('密码错误：', '请检查账号密码是否正确');
                        }
                    } else {
                        Ext.Msg.alert('连接服务器失败', '请检查网络后重试');
                    }
                }, this);
            }

        }
    },
});