Ext.define('MBAPeople.view.Main', {
    extend:'Ext.tab.Panel',
    xtype:'main',
    alias:'widget.mainview',
    requires:[
        'MBAPeople.view.UserList',
        'MBAPeople.view.ClassList',
        'MBAPeople.view.PostList',
        'MBAPeople.view.SettingList',
        'MBAPeople.view.InfoTab',
        'MBAPeople.view.Register',
        'MBAPeople.view.Reg',
        'MBAPeople.view.Reginfo',
        'MBAPeople.view.Suggestion',
        'MBAPeople.view.AdPanel',
        'MBAPeople.view.PostPanel',
        'MBAPeople.view.Guide',
        'MBAPeople.view.AdList',
        'MBAPeople.view.PostForm',
        'MBAPeople.view.map',
        'Ext.Label', 'Ext.field.Search', 'Ext.form.FieldSet', 'Ext.Img', 'Ext.ActionSheet'
    ],
    config:{
        id:'mainview',
        activeItem:0,
        masked:{
            xtype:'loadmask',
            message:'读取中...'
        },
        tabBar:{
            border:'',
            docked:'bottom',
            layout:{
                pack:'center',
                type:'hbox'
            }
        },
        listeners:{
            'initialize':function (carousel) {
                this.setMasked(false);
            },
            activeitemchange:function (tabPanel, tab, oldTab) {
                if (tab.config.title == '论坛') {
                    this.loadPostdata();
                } else if (tab.config.title == '企业') {
                    this.loadAddata();
                }
                else if (tab.config.title == '地图') {
                    this.loadAddata();
                }
                else if (tab.config.title == '人脉') {
                    this.loadFavorData();
                }
            } // activeitemchange
        }, // listeners
        items:[
            {
                xtype:'panel',
                title:'人脉',
                iconCls:'team',
                items:[
                    {
                        xtype:'userlist',
                        style:'border: none; font: 13px Arial black;height: 95%',
                        docked:'bottom'
                    },
                    {
                        xtype:'toolbar',
                        docked:'top',
                        items:[
                            {
                                ui:'plain',
                                itemId:'classbtn',
                                iconCls:'list',
                                iconMask:true
                            },
                            {
                                xtype:'searchfield',
                                id:'mysearchfield',
                                width:'60%',
                                placeHolder:'输入关键字',
                                listeners:{
                                    clearicontap:function () {
                                        this.loadFavorData();
                                        this.setValue('');
                                        this.blur();
                                    }
                                }
                            },
                            {
                                ui:'plain',
                                itemId:'searchbtn',
                                iconCls:'search',
                                iconMask:true
                            }
                        ]
                    },
                    {
                        xtype:'label',
                        html:'没有结果',
                        itemId:'signInFailedLabel',
                        hidden:true,
                        hideAnimation:'fadeOut',
                        showAnimation:'fadeIn',
                        style:'color:#990000;margin:5px 0px;',
                        docked:'top'
                    }
                ]
            },
            {
                xtype:'panel',
                title:'论坛',
                iconCls:'home',
                items:[
                    {
                        xtype:'postlist',
                        style:'border: none; font: 13px Arial black;height: 95%',
                        docked:'bottom'
                    },
                    {
                        xtype:'toolbar',
                        docked:'top',
                        title:'论坛',
                        items:[
                            {
                                ui:'plain',
                                itemId:'postsytlebtn',
                                iconCls:'list',
                                iconMask:true
                            },
                            {
                                xtype:'button',
                                itemId:'Postnewbtn',
                                right:'0%',
                                top:'10%',
                                text:'发表新帖'
                            }
                            /* {
                             xtype:'searchfield',
                             itemId:'postsearchfield',
                             width:'80%',
                             placeHolder:'搜索论坛内容'
                             }*/
                        ]
                    },
                    {
                        xtype:'label',
                        html:'没有结果',
                        itemId:'signInFailedLabel',
                        hidden:true,
                        hideAnimation:'fadeOut',
                        showAnimation:'fadeIn',
                        style:'color:#990000;margin:5px 0px;',
                        docked:'top'
                    }
                ]
            },


            /*{
             title:'地图',
             iconCls:'locate',
             styleHtmlContent:true,
             scrollable:true,
             items:[
             {   docked:'top',
             xtype:'titlebar',
             title:'校友地图'
             },
             {
             xtype: 'maps',
             docked:'bottom',
             height:'100%',
             useCurrentLocation: {
             autoUpdate : false
             },
             html:'地图正在加载中...',
             mapOptions: {
             zoom: 12,
             mapTypeId : google.maps.MapTypeId.ROADMAP,
             navigationControl: true,
             scaleControl:true,
             zoomControl:true,
             panControl:true,
             overviewMapControl:true,
             ZoomControlOptions :{
             style: google.maps.ZoomControlStyle .LARGE
             },
             navigationControlOptions: {
             style: google.maps.NavigationControlStyle.DEFAULT
             }
             }
             }
             ]
             },*/
            {
                xtype:'panel',
                title:'企业',
                iconCls:'info',
                items:[
                    {
                        xtype:'adlist',
                        title:'企业',
                        style:'border: none; font: 13px Arial black;height: 95%',
                        iconCls:'info'
                    },
                    {
                        xtype:'toolbar',
                        docked:'top',
                        title:'校友企业'
                    }
                ]
            },
            {
                xtype:'panel',
                title:'设置',
                iconCls:'settings',
                items:[
                    {
                        xtype:'settinglist',
                        style:'border: none; font: 13px Arial black;height: 95%',
                        title:'设置'
                    },
                    {
                        xtype:'toolbar',
                        docked:'top',
                        title:'设置'
                    }
                ]
            }
        ]
    },
    loadFavorData:function () {
        var user = Ext.StoreMgr.lookup('UserStore');
        var p = user.getProxy();
        p.setExtraParams({'do':'getfavorlist', 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'), 'uid':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('id'), 'device':Ext.os.name});
        user.load(function (records, operation, success) {
            //读取user成功则跳转到main
            if (!success) {
                Ext.Msg.alert('网络读取数据失败：', '请检查网络连接');
            }
        }, this);
    },
    loadData:function () {
        var localstore = Ext.StoreMgr.lookup('LocalStore').getData().getAt(0);
        var classno = '11P2';
        if (localstore) {
            classno = localstore.getData()['classno'];
        }
        var du = Ext.StoreMgr.lookup('UserStore');
        var p = du.getProxy();
        p.setExtraParams({'do':'searchuser', "keyword":[classno], "limit":'100', 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'), 'device':Ext.os.name});
        du.load(function (records, operation, success) {
            //console.log('main loaded records ' + success);
        }, this);
    },
    haveNoData:function (count) {
        var label = this.down('#signInFailedLabel');
        if (count < 1) {
            label.show();
        } else {
            label.hide();
        }
    },
    /*    doTabChange:function (tabBar, newTab, oldTab) {
     *//* var index = tabBar.indexOf(newTab);
     Window.PostNotloaded = true;
     if (index == 1) {
     }
     this.setActiveItem(index);*//*
     },*/
    loadPostdata:function () {
        this.setMasked(true);
        var post = Ext.StoreMgr.lookup('PostStore');
        var p = post.getProxy();
        p.setExtraParams({'do':'getpost', "limit":'10', 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'), 'device':Ext.os.name});
        post.load(function (records, operation, success) {
            //读取成功则跳转到main
            /*            console.dir(records);
             console.dir('post.load');*/
            this.setMasked(false);
        }, this);
    },
    loadAddata:function () {
        //
        var store = Ext.StoreMgr.lookup('AdStore');
        var p = store.getProxy();
        p.setExtraParams({'do':'getadlist', 'device':Ext.os.name, 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account')});
        store.load(function (records, operation, success) {
        })
    },
    loadMapdata:function () {
        //
        var store = Ext.StoreMgr.lookup('MapStore');
        var p = store.getProxy();
        p.setExtraParams({'do':'getmaplist', 'device':Ext.os.name, 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account')});
        store.load(function (records, operation, success) {
        })
    }

})
;