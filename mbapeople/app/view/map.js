Ext.define('MBAPeople.view.map', {
    extend:'Ext.Map',
    xtype:'maps',
    requires:[
        'Ext.TitleBar',
        'Ext.Map'
    ],
    config:{
        listeners:[
            {
                fn:'onMapMaprender',
                event:'maprender'
            }
        ]
    },

    showinfomessage:function (map, markers, index) {
        google.maps.event.addListener(markers[index], 'click', function (event) {
            if (markers) {
                for (i in markers) {
                    //if(markers[i].InfoBox) markers[i].infowindow.close();
                    if(markers[i].InfoBox) markers[i].InfoBox.close();
                }
            }
            markers[index].InfoBox.open(map, markers[index]);
        });
    },
    onMapMaprender:function (mapview, gmap, options) {
        var homeimg = new google.maps.MarkerImage(
            'resources/img/home.png'
        );
        var friendimg = new google.maps.MarkerImage(
            'resources/img/friends.png'
        );

        //read map store
        var store = Ext.StoreMgr.lookup('MapStore');
        var p = store.getProxy();
        p.setExtraParams({'do':'getmaplist', 'latitude':[mapview.getGeo().getLatitude()], 'longitude':[mapview.getGeo().getLongitude()], 'device':Ext.os.name, 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account')});
        var markers = [];
        var i =0;
        markers.push(new google.maps.Marker({
            position:new google.maps.LatLng([mapview.getGeo().getLatitude()], [mapview.getGeo().getLongitude()]),
            map:gmap,
            draggable:false,
            title:'我',
            icon:homeimg,
            animation:google.maps.Animation.DROP
        }));
        store.load(function (records, operation, success) {
            store.each(function (record) {
                i=i+1;
                // add mark in map
                markers[i]= new google.maps.Marker({
                    position:new google.maps.LatLng([record.get('latitude')], [record.get('longitude')]),
                    map:gmap,
                    draggable:false,
                    icon:friendimg,
                    title:record.get('name'),
                    animation:google.maps.Animation.DROP
                });
                var contentString = record.get('name');
                var  d = new Date();
                d.setTime(record.get('recenttime')*1000);
                contentString='<strong>'+record.get('name')+"</strong><div>"+record.get('classno')+"</div><div>"+record.get('company')+"</div><div>"+d.toLocaleString()+"</div>";
                //info box
                var boxText = document.createElement("div");
                boxText.style.cssText = "border: 1px solid black; margin-top: 8px; background: yellow; padding: 5px;";
                boxText.innerHTML = contentString;

                var myOptions = {
                    content: boxText
                    ,disableAutoPan: false
                    ,maxWidth: 0
                    ,pixelOffset: new google.maps.Size(0, -10)
                    ,zIndex: null
                    ,boxStyle: {
                        background: "url('resources/img/friends.png') no-repeat"
                        ,opacity: 0.75
                        ,width: "80px"
                        ,fontSize: "8pt"
                    }
                    ,closeBoxMargin: "10px 2px 2px 2px"
                    ,closeBoxURL: "http://www.google.com/intl/en_us/mapfiles/close.gif"
                    ,infoBoxClearance: new google.maps.Size(1, 1)
                    ,isHidden: false
                    ,pane: "floatPane"
                    ,enableEventPropagation: false
                };
                markers[i].InfoBox = new InfoBox(myOptions);
                mapview.showinfomessage(gmap,  markers,i);
            })
        });
    }

});