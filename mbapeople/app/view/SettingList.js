Ext.define('MBAPeople.view.SettingList', {
    extend:'Ext.dataview.List',
    alias:'widget.settinglist',
    requires:[
       'MBAPeople.view.About','MBAPeople.view.ChangePassword'
    ],
    config:{
        ui: 'round',
        scroll : false,
        margin : '10 15 10 15',
        cls : 'list2',
        itemCls : 'list2_item',
        store:{
            fields:['name','icon','needsIcon'],
            autoLoad:true,
            data:[
                {"name" : "个人资料","icon":'info_plain',"needsIcon":true},
                {"name" : "修改密码","icon":'brightness1',"needsIcon":true},
                {"name" : "退出登录","icon":'power_on',"needsIcon":true},
                //{"name" : "使用帮助","icon":'compose2',"needsIcon":true},
                {"name" : "问题反馈","icon":'help',"needsIcon":true},
                {"name" : "<strong>关于我们</strong>","icon":'',"needsIcon":false},
                {"name" : "版本更新说明","icon":'speedometer2',"needsIcon":true},
                {"name" : "软件版本1.1.1","icon":'mouse',"needsIcon":true},
                {"name" : "<strong>客户端下载</strong>","icon":'',"needsIcon":false},
                {"name" : '<a href="http://54.251.118.17/down/thumba.apk" target="_blank"><img src="resources/img/android.png" width="170" height="40" /></a>',"icon":'',"needsIcon":false},
                {"name" : '<a href="https://itunes.apple.com/us/app/mba-ren-mai/id620438205" target="_blank"><img src="resources/img/iphone.png" width="170" height="40" /></a>',"icon":'',"needsIcon":false},
            ]
        },

        itemTpl:'{name}',
        itemTpl: '<tpl if="needsIcon"><img width="26" height="26" src="resources/img/{icon}.png" align="absmiddle" /></tpl>{name}'

    }


})
;