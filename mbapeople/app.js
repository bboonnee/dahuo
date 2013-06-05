//<debug>
Ext.Loader.setPath({
    'Ext': 'touch/src',
    'MBAPeople': 'app'
});
//</debug>

Ext.application({
    name: 'MBAPeople',

    requires: [
        'Ext.MessageBox',
        'MBAPeople.proxy.ProxySearch',
        'MBAPeople.proxy.ProxyPost',
        'MBAPeople.proxy.ProxyWordPress'

    ],
    models:[
        'UserModel',
        'ClassModel',
        'PostModel',
        'WpModel',
        'CommentModel'
    ],
    stores:[
        'ClassStore',
        'UserStore',
        'UserUpdateStore',
        'PostUpdateStore',
        'LocalStore',
        'AdStore',
        'PostStore',
        'MapStore',
        'WPReadStore',
        'Styles','PostStyles','CommentStore'
    ],
    controllers:[
        'Contacts','Ad','Logreg','Post','Setting','User'
    ],
    views: ['Main','Loginin','SettingList','StylePicker','PostPicker','PostHtml'],

    icon: {
        '57': 'resources/icons/Icon.png',
        '72': 'resources/icons/Icon~ipad.png',
        '114': 'resources/icons/Icon@2x.png',
        '144': 'resources/icons/Icon~ipad@2x.png'
    },

    isIconPrecomposed: true,

    startupImage: {
        '320x460': 'resources/startup/320x460.jpg',
        '640x920': 'resources/startup/640x920.png',
        '768x1004': 'resources/startup/768x1004.png',
        '748x1024': 'resources/startup/748x1024.png',
        '1536x2008': 'resources/startup/1536x2008.png',
        '1496x2048': 'resources/startup/1496x2048.png'
    },

    launch: function() {
        // Destroy the #appLoadingIndicator element
        Ext.fly('appLoadingIndicator').destroy();

        //
/*        this.getApplication().getHistory().add(Ext.create('Ext.app.Action', {
         url: 'home'
         }));*/
       /* function onBackKeyDown(e) {
            e.preventDefault();
            console.dir('onBackKeyDown');
            // you are at the home screen
            if (Ext.Viewport.getActiveItem().xtype == loginForm.xtype ) {
                navigator.app.exitApp();
            }else {
                this.getApplication().getHistory().add(Ext.create('Ext.app.Action', {
                    url: 'Mainview'
                }));
                history.back();
            }
        }
        document.addEventListener("backbutton", Ext.bind(onBackKeyDown, this), false);*/
        //首先读取class的列表，成功的时候读取local的列表，local成功的时候判断是否自动登录

        var du = Ext.StoreMgr.lookup('ClassStore');
        du.load(function (records, operation, success) {
            //成功的时候读取local的列表，local成功的时候判断是否自动登录
            //console.log('class loaded records ' + success);
            var localstore=Ext.StoreMgr.lookup('LocalStore').getData().getAt(0);
            if(localstore)
            {
                var autoLogin =localstore.getData()['autologin'];
                var userAccount=localstore.getData()['account'];
                if (autoLogin) {
                    //local成功的时候判断是否自动登录
                  var user = Ext.StoreMgr.lookup('UserStore');
                    var p = user.getProxy();
                    p.setExtraParams({'do': 'getfavorlist','account': Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'),'uid': Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('id'),'device': Ext.os.name});
                    user.load(function (records, operation, success) {
                        //读取user成功则跳转到main
                        if(!success){
                            Ext.Msg.alert('网络读取数据失败：', '请检查网络连接');
                        }
                        Ext.create('MBAPeople.view.Main', {fullscreen:true});
                    }, this);
                    //
                   /* var post = Ext.StoreMgr.lookup('PostStore');
                    var p = post.getProxy();
                    p.setExtraParams({'do':'getpost', "limit":'10', 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'), 'device':Ext.os.name});
                    post.load(function (records, operation, success) {
                        if(!success){
                            Ext.Msg.alert('网络读取数据失败：', '请检查网络连接');
                        }
                        Ext.create('MBAPeople.view.Main', {fullscreen:true});
                    }, this);*/
                }else
                {
                    Ext.create('MBAPeople.view.Loginin', {fullscreen:true});
                }
            }else
            {
                Ext.create('MBAPeople.view.Loginin', {fullscreen:true});
            }

        }, this);
        if (!du.getCount()) {
            du.add({classno:'11P2'});
        } else {
            //console.log(du.getCount());
        }
    },

    onUpdated: function() {
        window.location.reload();
    }
});
