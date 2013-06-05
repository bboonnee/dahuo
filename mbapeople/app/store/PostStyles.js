Ext.define('MBAPeople.store.PostStyles', {
    extend:'Ext.data.Store',

    config:{
        fields:['id', 'name'],
        data:[
            {
                id:'all',
                name:'所有帖子'
            },
            {
                id:'notice',
                name:'通知'
            },
            {
                id:'help',
                name:'帮帮忙'
            },
            {
                id:'org',
                name:'活动'
            },
            {
                id:'coop',
                name:'合作'
            },
            {
                id:'job',
                name:'求职招聘'
            },
            {
                id:'ads',
                name:'广告推广'
            }
        ]
    }
});
