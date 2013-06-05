Ext.define('MBAPeople.model.CommentModel', {
    extend:'Ext.data.Model',

    config:{
        fields:[
            {name:'id'},
            {name:'postid'},
            {name:'author'},
            {name:'account'},
            {name:'authorurl'},
            {name:'date'},
            {name:'content'},
            {name:'parent'},
        ]
    }
});