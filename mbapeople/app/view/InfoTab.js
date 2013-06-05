Ext.define('MBAPeople.view.InfoTab', {
    extend:'Ext.tab.Panel',
    xtype:'main',
    alias:'widget.infotab',
    requires:[
        'MBAPeople.model.UserModel', 'Ext.field.Hidden', 'MBAPeople.view.Fileup'
    ],
    config:{
        id:'infotab',
        activeItem:0,
        scrollable:true,
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
                        itemId:'infoBackBtn',
                        ui:'plain',
                        iconCls:'home',
                        iconMask:true
                    },
                    {
                        xtype:'component',
                        flex:1,
                        cls:[
                            'contact-name'
                        ],
                        style:'font: 24px Arial white;color:white',
                        html:'姓名',
                        itemId:'nameTxt',
                        centered:true
                    },
                    {
                        xtype:'button',
                        itemId:'favoriteBtn',
                        iconCls:'favorites',
                        iconMask:true,
                        right:'0%',
                        top:'10%',
                        text:''
                    },
                    {
                        xtype:'button',
                        itemId:'saveInfoBtn',
                        ui:'action',
                        iconCls:'',
                        iconMask:true,
                        text:'保存',
                        right:'0%',
                        hidden:true,
                        top:'10%'
                    }
                ]
            },
            {
                xtype:'panel',
                title:'基本信息',
                iconCls:'user',
                scrollable:{
                    direction:'vertical',
                    directionLock:true
                },
                items:[
                    {
                        xtype:'fieldset',
                        title:'基本信息',
                        margin: '0 10 10 10',
                        padding:'10 10 0 10',
                        items:[
                            {
                                layout:{
                                    type:'hbox',
                                    align:'stretch'
                                },
                                items:[
                                    {
                                        itemId:'pic',
                                        xtype:'img',
                                        //mode : 'image',
                                        margin: '0 0 0 10',
                                        height:72,
                                        width:72,
                                        //src:'resources/img/ic_contact.png',
                                        listeners:{
                                            load: function(img,e,opts) {
                                            },
                                            error: function(img,e,opts) {
                                                this.setSrc('resources/img/ic_contact.png');
                                            },
                                            tap:function(img){
                                                var isPhone = Ext.os.deviceType == 'Phone';
                                                overlay = Ext.Viewport.add({
                                                    xtype: 'panel',

                                                    // We give it a left and top property to make it floating by default
                                                    left: 0,
                                                    top: 0,

                                                    // Make it modal so you can click the mask to hide the overlay
                                                    modal: true,
                                                    hideOnMaskTap: true,

                                                    // Make it hidden by default
                                                    hidden: true,

                                                    // Set the width and height of the panel
                                                    width: isPhone ? 260 : 400,
                                                    height: isPhone ? '70%' : 400,

                                                    // Here we specify the #id of the element we created in `index.html`
                                                    contentEl: 'content',

                                                    // Style the content and make it scrollable
                                                    styleHtmlContent: true,
                                                    scrollable: true,
                                                    items:[
                                                        {
                                                            xtype:'img',
                                                            src:img.getSrc(),
                                                            width: isPhone ? 260 : 400,
                                                            height: isPhone ? '70%' : 400
                                                        }
                                                    ]
                                                });
                                                overlay.showBy(img);
                                            }
                                        }
                                        // For success and failure callbacks setup look into controller
                                    },
                                    {
                                        itemId:'fileBtn',
                                        xtype:'fileupload',
                                        margin: '0 0 0 10',
                                        autoUpload:false,
                                        hidden:true,
                                        url:'http://mbarenmai.com/getfile.php'
                                    },
                                    {
                                        margin: '0 0 0 10%',
                                        padding:'0 0 0 10%',
                                        layout:{
                                            type:'vbox',
                                            align:'stretch'
                                        },
                                        items:[
                                            {
                                                xtype:'textfield',
                                                itemId:'name',
                                                clearIcon:false,
                                                readOnly:true
                                            },
                                            {
                                                xtype:'textfield',
                                                itemId:'classno',
                                                clearIcon:false,
                                                readOnly:true
                                            }
                                        ]
                                    }

                                ]
                            },



                            {
                                xtype:'textfield',
                                itemId:'account',
                                clearIcon:false,
                                label:'经管账号',
                                labelAlign:'left',
                                readOnly:true,

                                name:'account'
                            },
                            {
                                xtype:'textfield',
                                itemId:'mobile',
                                clearIcon:false,
                                label:'手机',
                                labelAlign:'left',
                                readOnly:true,
                                name:'mobile'

                            },
                            {
                                xtype:'textfield',
                                itemId:'company',
                                clearIcon:false,
                                label:'公司名称',
                                labelAlign:'left',
                                name:'company',
                                readOnly:true
                            },
                            {
                                xtype:'textfield',
                                itemId:'title',
                                clearIcon:false,
                                label:'职位',
                                labelAlign:'left',
                                name:'title',
                                readOnly:true
                            },
                            {
                                xtype:'textfield',
                                itemId:'email',
                                clearIcon:false,
                                label:'私人电子邮件',
                                labelAlign:'left',
                                readOnly:true,
                                name:'email'
                            }
                        ]
                    }
                ]
            },
            {
                xtype:'panel',
                title:'详细信息',
                iconCls:'bookmarks',
                scrollable:{
                    direction:'vertical',
                    directionLock:true
                },
                items:[
                    {
                        xtype:'textfield',
                        itemId:'startyear',
                        clearIcon:false,
                        label:'入学年份',
                        labelAlign:'left',
                        readOnly:true,
                        hidden:true,
                        name:'startyear'
                    },
                    {
                        xtype:'textfield',
                        itemId:'id',
                        clearIcon:false,
                        label:'id',
                        labelAlign:'left',
                        readOnly:true,
                        hidden:true,
                        name:'id'
                    },
                    {
                        xtype:'selectfield',
                        label:'性别',
                        itemId:'gender',
                        name:'gender',
                        readOnly:true,
                        options:[
                            {text:'男', value:'男'},
                            {text:'女', value:'女'},
                            {text:'其他', value:'其他'}
                        ]
                    },
                    {
                        xtype:'textfield',
                        itemId:'birthday',
                        clearIcon:false,
                        label:'生日',
                        labelAlign:'left',
                        readOnly:true,
                        name:'birthday'
                    },

                    {
                        xtype:'selectfield',
                        label:'婚恋状态',
                        itemId:'marriage',
                        name:'marriage',
                        readOnly:true,
                        options:[
                            {text:'保密', value:'0'},
                            {text:'单身', value:'1'},
                            {text:'恋爱中', value:'2'},
                            {text:'已婚', value:'3'},
                            {text:'二次单身', value:'4'},
                            {text:'其他', value:'5'}
                        ]
                    },
                    /* {
                     xtype:'textfield',
                     itemId:'marriage',
                     clearIcon:false,
                     label:'婚恋状态',
                     labelAlign:'left',
                     readOnly:true,
                     name:'marriage'

                     },*/
                    {
                        xtype:'textfield',
                        itemId:'hobby',
                        clearIcon:false,
                        label:'兴趣爱好',
                        labelAlign:'left',
                        readOnly:true,
                        name:'hobby'

                    },
                    {
                        xtype:'textfield',
                        itemId:'sports',
                        clearIcon:false,
                        label:'喜爱运动',
                        labelAlign:'left',
                        readOnly:true,
                        name:'sports'

                    },
                    {
                        xtype:'fieldset',
                        title:'社交网络',
                        items:[
                            {
                                xtype:'textfield',
                                itemId:'sinaweibo',
                                clearIcon:false,
                                label:'新浪微博',
                                labelAlign:'left',
                                name:'sinaweibo',
                                readOnly:true
                            },
                            {
                                xtype:'textfield',
                                itemId:'qq',
                                clearIcon:false,
                                label:'QQ',
                                labelAlign:'left',
                                name:'qq',
                                readOnly:true

                            },
                            {
                                xtype:'textfield',
                                itemId:'renren',
                                clearIcon:false,
                                label:'人人',
                                labelAlign:'left',
                                name:'renren',
                                readOnly:true
                            },
                            {
                                xtype:'textfield',
                                itemId:'facebook',
                                clearIcon:false,
                                label:'Facebook',
                                labelAlign:'left',
                                name:'facebook',
                                readOnly:true
                            }
                        ]
                    },
                    {
                        xtype:'fieldset',
                        title:'个人网络',
                        items:[
                            {
                                xtype:'textfield',
                                itemId:'blog',
                                clearIcon:false,
                                label:'博客',
                                labelAlign:'left',
                                name:'blog',
                                readOnly:true
                            },
                            {
                                xtype:'textfield',
                                itemId:'website',
                                clearIcon:false,
                                label:'个人主页',
                                labelAlign:'left',
                                name:'website',
                                readOnly:true
                            }
                        ]
                    },
                    {
                        xtype:'fieldset',
                        title:'现职公司',
                        items:[

                            {
                                xtype:'textfield',
                                itemId:'industry',
                                clearIcon:false,
                                label:'行业',
                                labelAlign:'left',
                                name:'industry',
                                readOnly:true
                            },
                            {
                                xtype:'textfield',
                                itemId:'city',
                                clearIcon:false,
                                label:'城市',
                                labelAlign:'left',
                                name:'city',
                                readOnly:true
                            },
                            {
                                xtype:'textfield',
                                itemId:'address',
                                clearIcon:false,
                                label:'地址',
                                labelAlign:'left',
                                name:'address',
                                readOnly:true
                            },
                            {
                                xtype:'textfield',
                                itemId:'telephone',
                                clearIcon:false,
                                readOnly:true,
                                label:'固定电话',
                                labelAlign:'left',
                                name:'telephone'

                            }
                        ]
                    },

                    {
                        xtype:'fieldset',
                        title:'本科院校',
                        items:[
                            {
                                xtype:'textfield',
                                itemId:'college',
                                clearIcon:false,
                                label:'学校',
                                labelAlign:'left',
                                name:'college',
                                readOnly:true
                            },
                            {
                                xtype:'textfield',
                                itemId:'collegeyear',
                                clearIcon:false,
                                label:'入学时间',
                                labelAlign:'left',
                                name:'collegeyear',
                                readOnly:true
                            },
                            {
                                xtype:'textfield',
                                itemId:'collegemajor',
                                clearIcon:false,
                                label:'专业',
                                labelAlign:'left',
                                name:'collegemajor',
                                readOnly:true
                            }
                        ]
                    },
                    {
                        xtype:'fieldset',
                        title:'硕士院校',
                        items:[
                            {
                                xtype:'textfield',
                                itemId:'mastercollege',
                                clearIcon:false,
                                label:'学校',
                                labelAlign:'left',
                                name:'mastercollege',
                                readOnly:true
                            },
                            {
                                xtype:'textfield',
                                itemId:'masteryear',
                                clearIcon:false,
                                label:'入学时间',
                                labelAlign:'left',
                                name:'masteryear',
                                readOnly:true
                            },
                            {
                                xtype:'textfield',
                                itemId:'mastermajor',
                                clearIcon:false,
                                label:'专业',
                                labelAlign:'left',
                                name:'mastermajor',
                                readOnly:true
                            }
                        ]
                    },
                    {
                        xtype:'fieldset',
                        title:'求职意向',
                        items:[
                            {
                                xtype:'selectfield',
                                label:'状态',
                                itemId:'seekjob',
                                name:'seekjob',
                                readOnly:true,
                                options:[
                                    {text:'不考虑换工作', value:'-1'},
                                    {text:'不会找工作但会考虑更好的机会', value:'0'},
                                    {text:'正在找工作', value:'1'},
                                    {text:'应届毕业找工作', value:'11'}
                                ]
                            },
                            {
                                xtype:'textareafield',
                                itemId:'jobability',
                                clearIcon:false,
                                label:'工作能力简述',
                                maxRows:3,
                                labelAlign:'left',
                                name:'jobability',
                                readOnly:true
                            },
                            {
                                xtype:'textareafield',
                                itemId:'seektarget',
                                clearIcon:false,
                                maxRows:3,
                                label:'期望工作方向',
                                labelAlign:'left',
                                name:'seektarget',
                                readOnly:true
                            }
                        ]
                    }
                ]
            }
        ],
        listeners:[
            {
                fn:'onFavoriteBtnTap',
                event:'tap',
                delegate:'#favoriteBtn'
            }
        ]
    },
    setRecord:function (record) {
        this.callParent(arguments);
        //console.log('info tab setdata');
        //console.dir(record);
        if (record) {
            //var name = record.get('name') + ' ' + (record.get('lastName') || '');
            var data = record.data;
            this.setData(data);
            this.setActiveItem(0);
            var name = record.get('name');
            this.down('#nameTxt').setHtml(name);

            var reqFields = this.query('field');
            var i = 0, ln = reqFields.length, field;
            for (; i < ln; i++) {
                field = record.get(reqFields[i].getItemId());
                reqFields[i].setValue(field);
            }
            var isFavorite = record.get('isFavorite');
            //console.dir(isFavorite);
            this.down('#favoriteBtn')[isFavorite ? 'addCls' : 'removeCls']('x-button-pressed');
            if (isFavorite)
                this.down('#favoriteBtn').setText("取消收藏")
            else this.down('#favoriteBtn').setText("收藏");
            if(record.get('picture')!='avatar'){
                this.down('#pic').setSrc('http://54.251.118.17/avatar/'+[record.get('account')] +'.png');
            }
            else{
                this.down('#pic').setSrc('resources/img/ic_contact.png');
            }
        }
    },
    applyItems:function (items, collection) {
        var i = 0,
            iNum = items.length,
            record = this.getRecord(),
            data = this.getData();
        for (; i < iNum; i++) {
            items[i].data = data;
        }
        return this.callParent([items, collection]);
    },
    setReadonly:function (readonly) {
        var reqFields = this.query('field');
        var i = 0, ln = reqFields.length, field;
        for (; i < ln; i++) {
            if ((reqFields[i].getItemId() == 'name') || (reqFields[i].getItemId() == 'classno') || (reqFields[i].getItemId() == 'mobile')
                || (reqFields[i].getItemId() == 'startyear') || (reqFields[i].getItemId() == 'account')) {
                reqFields[i].setDisabled(true);
                continue;
            }
            else {
                reqFields[i].setReadOnly(readonly);
                reqFields[i].setDisabled(readonly);
                this.down('#saveInfoBtn').setHidden(readonly);
                if(( Ext.os.deviceType == 'Phone')&&(Ext.os.name='Android'))
                {
                    this.down('#fileBtn').setHidden(readonly);
                }else
                {
                    this.down('#fileBtn').setHidden(readonly);
                }

                this.down('#favoriteBtn').setHidden(!readonly);
                if (readonly) {
                    reqFields[i].setPlaceHolder("未公开");
                } else {
                    reqFields[i].setPlaceHolder("请填写");
                }
            }
        }
    },
    getValues:function () {
        //text filed
        var reqFields = this.query('field');
        var i = 0, ln = reqFields.length, field, value;
        var ed = Ext.create('MBAPeople.model.UserModel');
        for (; i < ln; i++) {
            field = reqFields[i].getItemId();
            value = reqFields[i].getValue();
            if (value != '') {
                ed.set(field, value);
                // console.log(field + "  is " + value + " " + i);
            }
        }
        var re = Ext.JSON.encode(ed.getData());
        return(re);
    },
    onFavoriteBtnTap:function (button, e, options) {
        var pressingCls = 'x-button-pressed';
        button.element.toggleCls(pressingCls);
        var isPressed = button.element.hasCls(pressingCls);
        this.savefavor(isPressed);
        if (isPressed)
            this.down('#favoriteBtn').setText("取消收藏")
        else this.down('#favoriteBtn').setText("收藏")
        var record = this.getRecord();
        record.set('isFavorite', isPressed);
    },
    savefavor:function (isPressed) {
        var updatestore = Ext.StoreMgr.lookup('UserUpdateStore');
        var p = updatestore.getProxy();
        if (!p) {
            //console.log('proxy is null');
        } else {
            p.setExtraParams({'do':'favor', 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'),
                'uid':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('id'), 'device':Ext.os.name});
            p.setExtraParam("favorPressed", isPressed);
            p.setExtraParam("favoruid", this.getRecord().get("id"));
            //console.dir(values);
            updatestore.load(function (records, operation, success) {
                if (success) {
                    if (isPressed) {
                        Ext.Msg.alert('收藏成功', '');
                    } else {
                        Ext.Msg.alert('取消收藏成功', '');
                    }
                } else {
                    Ext.Msg.alert('连接服务器失败', '请检查网络后重试');
                }
            }, this);
        }
    }

});