Ext.define('MBAPeople.view.PostHtml', {
    extend:'Ext.form.Panel',
    alias:'widget.posthtml',
    config:{
        fullscreen:true,
        minHeight:'100px',
        scrollable:{
            direction:'vertical',
            directionLock:true
        },
        items:[
            {
                scrollable:{
                    direction:'vertical',
                    directionLock:true
                },
                items:[
                    {
                        xtype:'panel',
                        style:'font: 15px Arial black',
                        margin:'10 10 10 15',
                        padding:'10 10 10 15',
                        border:1,
                        itemId:'text'
                    },

                    {
                        xtype:'commentlist',
                        margin:'10 10 10 15',
                        //padding:'10 10 10 15',
                        itemId:'commentlist'
                    },
                    {
                        xtype:'fieldset',
                        border:1,
                        margin:'10 10 10 15',
                        padding:'10 10 10 15',
                        layout:{
                            type:'hbox',
                            align:'stretch'
                        },
                        items:[
                            {
                                border:1,
                                style:'font: 13px Arial black',
                                xtype:'textfield',
                                itemId:'commentinput',
                                width:'80%',
                                listeners:{
                                    keyup:function (textfield, e, options) {
                                        var keyCode = e.event.keyCode,
                                            value = this.getValue(), panel = this.getParent().getParent();
                                        if (keyCode == 13) {
                                            if (value.trim() != "") {
                                                panel.sendComment(panel, value);
                                                textfield.setValue('');
                                                textfield.blur();
                                            } else {
                                                console.dir('html keyup');
                                                Ext.Msg.alert('评论为空', '请输入哈', Ext.emptyFn);
                                            }

                                        }
                                    }
                                }
                            },
                            {
                                xtype:'button',
                                itemId:'commentbtn',
                                iconCls:'action',
                                iconMask:true,
                                handler:function () {
                                    var panel = this.getParent().getParent();
                                    var content = panel.down('#commentinput').getValue();
                                    if(content.trim()!='')
                                    {
                                        panel.sendComment(panel, content);
                                        panel.down('#commentinput').setValue('');
                                        panel.down('#commentinput').blur();
                                    }else {
                                        console.dir('html handler');
                                        Ext.Msg.alert('评论为空', '请输入哈', Ext.emptyFn);
                                    }

                                }
                            }
                        ]

                    }
                ]

            },
            {
                xtype:'textfield',
                itemId:'postid',
                hidden:true
                //value:record.get('id')
            },
            {
                xtype:'textfield',
                itemId:'author_id',
                hidden:true
                //value:record.get('author_id')
            }
        ]
    },
    setRecord:function(record){
        var html =   '<style type="text/css"> .fenge {height:1px; width:100%; background:#00CCFF; overflow:hidden;}.content{text-indent:2em;font-size:13px}</style>'
            + '<div align="center"><h1 class="page-title">' + [record.get('title')] + '</h1></div>'
            + '<div align="right"><strong>' + [record.get('author')] + "  " + [record.get('author_id')] + '</strong></div>'
            + '<div align="right"><strong>' + [record.get('posttime')] + '</strong></div>'
            + '<div> </div>'
            + '<div class="fenge">' + [record.get('content')] + '</div>'
            + '<div class="content">' + [record.get('content')] + '</div>';
        this.down('#text').setHtml(html);
        this.down('#postid').setValue(record.get('id'));
        this.down('#author_id').setValue(record.get('author_id'));
    },


})
;