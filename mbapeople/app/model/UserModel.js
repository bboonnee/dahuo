Ext.define('MBAPeople.model.UserModel', {
    extend:'Ext.data.Model',

    config:{
        fields:[
            {name:'id', type:'string'},
            {name:'name'},
            {name:'classno'},
            {name:'startyear'},
            {name:'account'},
            {name:'gender'},
            {name:'birthday'},
            {name:'mobile'},
            {name:'email'},
            {name:'marriage'},
            {name:'hobby'},
            {name:'sports'},
            {
                name:'picture'},
               // defaultValue:'resources/img/ic_contact.png'},
            //work
            {name:'company'},
            {name:'title'},
            {name:'industry'},
            {name:'city'},
            {name:'address'},
            {name:'telephone'},
            {name:'seekjob'},
            {name:'seektarget'},
            {name:'jobability'},
            //network
            {name:'sinaweibo'},
            {name:'qq'},
            {name:'renren'},
            {name:'facebook'},
            {name:'blog'},
            {name:'website'},
            //education
            {name:'college'},
            {name:'collegeyear'},
            {name:'collegemajor'},
            {name:'mastercollege'},
            {name:'masteryear', type:'string'},
            {name:'mastermajor', type:'string'},
            //for
            {name:'autologin'},
            {name:'updatetime'},
            {name:'permissions'},
            {name:'password'},
            {name:'weight'},
            {name:'studentno'},
            {
                name:'isFavorite',
                type:'boolean'
            },
            {name:'recenttime'},
            {name:'latitude'},
            {name:'longitude'},
        ]
    }
});