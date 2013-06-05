
Ext.define('MBAPeople.proxy.ProxySearch', {
    extend: 'Ext.data.proxy.JsonP',
    alias: 'proxy.searchproxy',
    id:'jsonproxy',
    
    config: {
      //url: 'http://192.168.1.8/mba/',
      url: 'http://54.251.118.17/mba/',
        reader: {
            type: 'json'
           // rootProperty: 'results'
        }
    }
});