Ext.define('MBAPeople.store.Styles', {
    extend: 'Ext.data.Store',

    config: {
        fields: ['id', 'name'],
        data: [{
            id:   'all',
            name: '全校班级列表'
        },{
            id:   'sameyear',
            name: '同年级班级'
        },
            {
            id:   'myclass',
            name: '本班'
        },{
            id:   'favor',
            name: '收藏'
        },{
                id:   'myinfo',
                name: '我的资料'
            }]
    }
});
