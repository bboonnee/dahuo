Ext.define('MBAPeople.controller.User', {
    extend:'MBAPeople.controller.Contacts',
    config:{
        control:{
            //
            styleList:{
                itemtap:'changeClassno'
            },
            fileBtn:{
                success:'onFileUploadSuccess',
                failure:'onFileUploadFailure',
                ready:'piconReady'
            },
            "button#editContactBtn":{
                tap:'onEditContactBtnTap'
            },
            "button#cancelBtn":{
                tap:'onCancelBtnTap'
            },
            "userlist":{
                itemtap:'onUserlistItemTap'
            },
            "classlist":{
                itemtap:'onClassItemTap'
            },

            "button#backInfoFormBtn":{
                tap:'onbackInfoFormBtnTap'
            },
            "button#saveInfoBtn":{
                tap:'onSaveInfoBtnTap'
            },
            "UserList":{
                activate:'onListActivate1'
            },
            //
            "button#searchbtn":{
                tap:'onSearchbtnTap'
            },
            "searchfield#mysearchfield":{
                keyup:'onSearchfieldKeyup'
            },
            "searchfield#classfilterfield":{
                keyup:'onClassfilterfieldKeyup'
            },
            "button#classbtn":{
                tap:'onClassbtnTap'
            },
        }
    },
    piconReady:function (file) {
        var reader = new FileReader();
        var form = this.getInfotab();
        reader.readAsDataURL(file);
        reader.onload = function (e) {
            form.down('#pic').setSrc(this.result);
        };
    },
    onFileUploadSuccess:function (dataurl) {
        var updatestore = Ext.StoreMgr.lookup('UserUpdateStore');
        var p = updatestore.getProxy();
        if (!p) {
            //console.log('proxy is null');
        } else {
            p.setExtraParams({'do':'updatepic', 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'), 'device':Ext.os.name});
            updatestore.load(function (records, operation, success) {
                if (success) {
                    Ext.device.Notification.show({
                        title:'All right',
                        message:'头像上传成功',
                        buttons:Ext.MessageBox.OK,
                        callback:Ext.emptyFn
                    });
                } else {
                    Ext.device.Notification.show({
                        title:'All right',
                        message:'网络传输出错',
                        buttons:Ext.MessageBox.OK,
                        callback:Ext.emptyFn
                    });
                }
            }, this);
        }
    },

    onFileUploadFailure:function (message) {
        Ext.device.Notification.show({
            title:'头像上传失败',
            message:message,
            buttons:Ext.MessageBox.OK,
            callback:Ext.emptyFn
        });
    },
    onClassfilterfieldKeyup:function (field, e, options) {
        //get the store and the value of the field
        var value = field.getValue(), //field为传回来的按键值，通过field.getValue来获得
            store = Ext.getStore('ClassStore');    //通过getStore('course')获得store文件夹下course.js数据

        //first clear any current filters on thes tore
        store.clearFilter();     //取消当前对数据的过滤

        //check if a value is set first, as if it isnt we dont have to do anything
        if (value) {
            //the user could have entered spaces, so we must split them so we can loop through them all
            var searches = value.split(' '), //去除搜索栏用户输入的空格
                regexps = [],
                i;

            //loop them all
            //开始搜索
            for (i = 0; i < searches.length; i++) {
                //if it is nothing, continue
                if (!searches[i]) continue;
                //if found, create a new regular expression which is case insenstive
                //new RegExp为创立新的正则表达式 将得到的文本忽略大小写 形成新的数组regexps
                regexps.push(new RegExp(searches[i], 'i'));    //i的意思为ignore case（忽略大小写）
            }

            //now filter the store by passing a method
            //the passed method will be called for each record in the store
            //添加数据过滤器
            store.filter(function (record) {
                var matched = [];
                //loop through each of the regular expressions
                for (i = 0; i < regexps.length; i++) {
                    var search = regexps[i],
                        didMatch = record.get('classno').match(search);
                    //if it matched the coursename, push it into the matches array
                    matched.push(didMatch);
                }
                //if nothing was found, return false (dont so in the store)
                if (regexps.length > 1 && matched.indexOf(false) != -1) {
                    return false;
                } else {
                    //else true true (show in the store)
                    return matched[0];
                }
            });
        }
    },
    onSearchbtnTap:function (button, e, options) {
        var textfield = Ext.getCmp('mysearchfield');
        if (textfield.getValue().trim() != "") {
            this.searchUser(textfield.getValue());
        } else {
            Ext.Msg.alert('搜索框为空', '请输入想查询姓名、公司名称、班级、手机号、邮箱等的关键字', Ext.emptyFn);
        }
        /* textfield.setValue('');
         textfield.blur();*/
    },
    onClassbtnTap:function (button, e, options) {
        this.getStyleBubble().showBy(button);
    },
    onSearchfieldKeyup:function (textfield, e, options) {
        var keyCode = e.event.keyCode;
        var value = textfield.getValue();
        //Ext.Msg.alert('key',keyCode);
        //the return keyCode is 13.
        if (keyCode == 13) {
            if (value.trim() != "") {
                this.searchUser(value);
            } else {
                Ext.Msg.alert('搜索框为空', '请输入想查询姓名、公司名称、班级、手机号、邮箱等的关键字', Ext.emptyFn);
            }
            /* textfield.setValue('');
             textfield.blur();*/
        }
    },
    searchUser:function (value) {
        var store = Ext.StoreMgr.lookup('UserStore');
        store.clearData();
        var p = store.getProxy();
        if (!p) {
            //console.log('proxy is null');
        } else {
            p.setExtraParams({'do':'searchuser', "keyword":[value], "limit":'100', 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'), 'device':Ext.os.name});
            store.load(function (records, operation, success) {
                //读取成功则跳转到
                var count = store.getCount();
                this.getMainview().haveNoData(count);
                //console.log('search finish' + count);
            }, this);

        }
    },

    onSaveInfoBtnTap:function (button, e, options) {
        var form = this.getInfotab();
        var values = form.getValues().trim();
        //console.dir(values);
        //如果可以读到model的值，否则新增
        var updatestore = Ext.StoreMgr.lookup('UserUpdateStore');
        var p = updatestore.getProxy();
        if (!p) {
            //console.log('proxy is null');
        } else {
            p.setExtraParams({'do':'updateuserinfo', "data":values, 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'), 'device':Ext.os.name});
            updatestore.load(function (records, operation, success) {
                //console.dir(records);
                if (records[0].get('name')) {
                    //console.log('updatestore finish');
                    var local = Ext.StoreMgr.lookup('LocalStore');
                    var record = local.getAt(0);
                    record.set(values);
                    record.dirty = true;
                    local.sync();
                    //console.log('update local store finish');
                    Ext.Msg.alert('保存成功', '', Ext.emptyFn);
                    this.BackBtnTap();
                } else {
                    Ext.Msg.alert('保存失败', '', Ext.emptyFn);
                    //this.onInfoBackBtnTap();
                }
            }, this);
        }
    },
     onEditContactBtnTap:function (button, e, options) {
        var referrer = Ext.Viewport.getActiveItem();
        var form = this.getContactform();
        var info = this.getContactinfo();
        form.referrer = referrer;
        Ext.Viewport.setActiveItem(form);
        form.setRecord(info.getRecord());
    },

    onCancelBtnTap:function (button, e, options) {
        var form = this.getContactform();
        Ext.Viewport.setActiveItem(form.referrer);
        delete form.referrer;

    },
    onUserlistItemTap:function (dataview, index, target, record, e, options) {

        var info = this.getInfotab();
        //var info = this.getContactinfo();
        info.setRecord(record);
        info.setReadonly(true);
        Ext.Viewport.setActiveItem(info);
    },
    // class form to tap the item
    onClassItemTap:function (dataview, index, target, record, e, options) {
        this.SeachClassData(record.getData()['classno']);
    },
   SeachClassData:function (classno) {
        var form = this.getMainview();
        var du = Ext.StoreMgr.lookup('UserStore');
        var p = du.getProxy();
        p.setExtraParams({'do':'searchuser', "keyword":[classno], 'field':'classno',
            'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'),
            'device':Ext.os.name});
        form.setMasked(true);
        du.load(function (records, operation, success) {
            if (records[0].get('name')) {

                form.setActiveItem(0);
                form.haveNoData(du.getCount());
                Ext.Viewport.setActiveItem(form);
                form.setMasked(false);
            } else {
            }
        }, this);
    },
    changeClassno:function (dataview, index, target, record, e, options) {
        var target = record.data.id,
            classlist = this.getClasslist(),
            classtore = Ext.StoreManager.lookup('ClassStore'),
            userstore = Ext.StoreMgr.lookup('UserStore'),
            main = this.getMainview();

        main.setMasked({
            xtype:'loadmask',
            message:'Loading...',
            indicator:false
        });
        switch (target) {
            case 'all':
                classtore.clearFilter();
                Ext.Viewport.setActiveItem(classlist);
                break;
            case 'sameyear':
                classtore.filter("startyear", Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('startyear'));
                Ext.Viewport.setActiveItem(classlist);
                break;
            case 'myclass':
                var p = userstore.getProxy();
                p.setExtraParams({'do':'getmyclass',
                    "account":Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'),
                    "uid":Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('id'),
                    'limit':100,
                    'device':Ext.os.name});
                userstore.load(function (records, operation, success) {
                    //读取user成功则跳转到main
                    if (!success) {
                        Ext.Msg.alert('网络读取数据失败：', '请检查网络连接');
                    }
                    Ext.Viewport.setActiveItem(main);
                }, this);
                break;
            case 'favor':
                var p = userstore.getProxy();
                p.setExtraParams({'do':'getfavorlist',
                    "account":Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'),
                    "uid":Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('id'),
                    'device':Ext.os.name});
                userstore.load(function (records, operation, success) {
                    //读取user成功则跳转到main
                    if (!success) {
                        Ext.Msg.alert('网络读取数据失败：', '请检查网络连接');
                    }
                    Ext.Viewport.setActiveItem(main);
                }, this);
                break;
            case 'myinfo':
                this.onEditInfoBtnTap();
                break;
        }
        main.unmask();
        this.getStyleBubble().hide();

    },
});