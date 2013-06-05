Ext.define('MBAPeople.proxy.ProxyPost', {
    extend:'Ext.data.proxy.JsonP',
    alias:'proxy.postproxy',
    id:'postproxy',

    config:{
        //url:'http://192.168.1.8/mba/',
        url: 'http://54.251.118.17/mba/',
        //cors:true,
        //withCredentials:false,
        //useDefaultXhrHeader:false,
        //method:'post',

        reader:{
            type:'json'
            // rootProperty: 'results'
        }
    }
});