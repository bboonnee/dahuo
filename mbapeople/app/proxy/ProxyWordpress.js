
Ext.define('MBAPeople.proxy.ProxyWordPress', {
    extend: 'Ext.data.proxy.JsonP',
    alias: 'proxy.wpproxy',
    id:'jsonproxy',
    
    config: {
       //url: 'http://192.168.1.4/wordpress/',
       url: 'http://54.251.118.17/home/',
        reader: {
            type: 'json',
            rootProperty: 'post'
        }
    }
});