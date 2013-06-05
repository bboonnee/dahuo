Ext.define('MBAPeople.view.Fileup', {
    extend: 'Ext.Button',
    xtype: 'fileupload',
    
    requires: [
        'Ext.MessageBox',
        'Ext.device.Notification'
    ],
    
    template: [
        
        // Default button elements (do not change!)
        {
            tag: 'span',
            reference: 'badgeElement',
            hidden: true
        },
        {
            tag: 'span',
            className: Ext.baseCSSPrefix + 'button-icon',
            reference: 'iconElement',
            hidden: true
        },
        {
            tag: 'span',
            reference: 'textElement',
            hidden: true
        },
        
        // Loading spinner
        {
            tag: 'div',
            className: Ext.baseCSSPrefix + 'loading-spinner',
            reference: 'loadingElement',
            hidden: true,
            
            children: [
                {
                    tag: 'span',
                    className: Ext.baseCSSPrefix + 'loading-top'
                },
                {
                    tag: 'span',
                    className: Ext.baseCSSPrefix + 'loading-right'
                },
                {
                    tag: 'span',
                    className: Ext.baseCSSPrefix + 'loading-bottom'
                },
                {
                    tag: 'span',
                    className: Ext.baseCSSPrefix + 'loading-left'
                }
            ]
        },
                
        // Hidden file element
        {
            tag: 'form',
            reference: 'formElement',
            hidden: false,            
            
            children: [
                {
                    tag: 'input',
                    reference: 'fileElement',
                    type: 'file',
                    name: 'userfile',
                    tabindex: -1,
                    hidden: false,
                    style: 'opacity:0;position:absolute;top:-3px;right:-3px;bottom:-3px;left:-3px;z-index:16777270;'
                }
            ]
        }
    ],
    
    // Default button states config
    defaultStates: {
        browse: {
            text: '选择照片',
            cls: Ext.baseCSSPrefix + 'fileup',
            ui: 'filebrowse'
        },

        ready: {
            text: '上传',
            cls: Ext.baseCSSPrefix + 'fileup-ready',
            ui: 'fileready'
        },

        uploading: {
            text: '上传中...',
            cls: Ext.baseCSSPrefix + 'fileup-uploading',
            ui: 'fileupload',
            loading: true
        }
    },
    
    // Current button state
    currentState: null,
    
    config: {
        cls: Ext.baseCSSPrefix + 'fileup',
        
        /**
         * @cfg {String} name Input element name, check on server for $_FILES['userfile']
         */        
        name: 'userfile',
        
        /**
         * @cfg {Boolean} autoUpload 
         * If true then "uploading" state will start after "ready" event automatically
         */
        autoUpload: false,
        
        /**
         * @cfg {Object} states 
         */
        states: true,
        
        /**
         * @cfg {Boolean} loadAsDataUrl
         */
        loadAsDataUrl: false,
        
        /**
         * @cfg {String} url URL to uploading handler script on server
         */
        url: ''
    },
    
    // @private
    applyStates: function(states) {
        var me = this;
        
        if (states) {
            
            if (Ext.isObject(states)) {
                
                // Merge custom config with default
                return Ext.merge({}, me.defaultStates, states);
            } else {
                return me.defaultStates;
            }
        } else {
            return me.defaultStates;
        }
    },
    
    // @private
    initialize: function() {
        var me = this;
        me.callParent();
        
        me.fileElement.dom.onchange = function() {
            me.onChanged.apply(me, arguments);
        };
        
        me.on({
            scope: me,
            buffer: 250,// Avoid multiple tap 
            tap: me.onButtonTap
        });
        
        // Stup initial button state
        me.changeState('browse');
    },
    
    // @private
    onButtonTap: function() {
        var me = this;
        
        switch (me.currentState) {
            
            // Currently we handle tap event while button in ready state
            // because in all other states button is not accessible
            case 'ready':                
                me.changeState('uploading');
                var file = me.fileElement.dom.files[0];
                                
                if (!me.getLoadAsDataUrl()) {
                    me.fireEvent('uploadstart', file);
                    me.doUpload(file);

                } else {
                    me.doLoad(file);
                }
                break;
        }
    },
    
    // @private
    onChanged: function(e) {
        var me = this;
        
        if (e.target.files.length > 0) {
            me.fireAction('ready', [e.target.files[0]], function() {
                me.changeState('ready');
            }, me);
        } else {
            Ext.device.Notification.show({
                title: 'Error',
                message: 'File selected but not accessible',
                buttons: Ext.MessageBox.OK,
                callback: function() {
                    me.changeState('browse');
                }
            });
        }
    },
    
    // @private
    changeState: function(state) {
        var me = this;
        var states = me.getStates();
        
        if (Ext.isDefined(states[state])) {
            
            // Common tasks for all states
            if (states[state].text) {
                me.setText(states[state].text);
            } else {
                me.setText('');
            }
            
            if (states[state].cls) {
                me.setCls(states[state].cls);
            } else {
                me.setCls('');
            }
            
            if (states[state].ui) {
                me.setUi(states[state].ui);
            } else {
                me.setUi('normal');
            }
            
            if (states[state].loading) {
                me.loadingElement.show();
            } else {
                me.loadingElement.hide();
            }
            
            // State specific tasks
            switch (state) {
                case 'browse':
                    me.currentState = 'browse';
                    me.reset();                    
                    break;
                    
                case 'ready':
                    me.currentState = 'ready';
                    me.fileElement.hide();
                    
                    if (me.getAutoUpload()) {
                        me.onButtonTap();
                    }                    
                    break;
                    
                case 'uploading':
                    me.currentState = 'uploading';
                    break;
            }
        } else {
            // <debug>
            Ext.Logger.warn('Config for FileUp state "'+ state +'" not found!');
            // </debug>
        }
    },
    
    /**
     * @private
     * @method doLoad
     * Read selected file as dataUrl value.
     * If you wish to get dataUrl content 
     * then you should listen for "loadsuccess" event
     * @param {Object} file Link to loaded file element
     */
    doLoad: function(file) {
        var me = this;                
        var reader = new FileReader();

        reader.onerror = function(e) {
            var message;
            switch (e.target.error.code) {
                case e.target.error.NOT_FOUND_ERR:
                    message = 'File Not Found';
                    break;

                case e.target.error.NOT_READABLE_ERR:
                    message = 'File is not readable';
                    break;

                case e.target.error.ABORT_ERR:
                    break;

                default:
                    message = 'Can not read file';
            };
            me.fireEvent('loadfailure', message, this, e);
        };

        reader.onload = function(e) {
            me.fireEvent('loadsuccess', this.result, this, e);
            me.changeState('browse');
        };

        // Read image file
        reader.readAsDataURL(file);
    },
    
    /**
     * @private
     * @method doUpload
     * Upload selected file using XMLHttpRequest.
     * @param {Object} file Link to loaded file element
     */
    doUpload: function(file) {
        var me = this;        
        var http = new XMLHttpRequest();
        
        if (http.upload && http.upload.addEventListener) {
            
            // Uploading progress handler
            http.upload.onprogress = function(e) {
                if (e.lengthComputable) {
                    var percentComplete = (e.loaded / e.total) * 100; 
                    me.setBadgeText(percentComplete.toFixed(0) + '%');
                }
            };
            
            // Response handler
            http.onreadystatechange = function (e) {
                if (this.readyState == 4) {
                    if(this.status == 200) {
                        var response = Ext.decode(this.responseText, true)
                        if (response && response.success) {
                            // Success
                            me.fireEvent('success', response, this, e);
                        } else if (response && response.message) {
                            // Failure
                            me.fireEvent('failure', response.message, response, this, e);
                        } else {
                            // Failure
                            me.fireEvent('failure', 'Unknown error', response, this, e);
                        }
                    } else {
                        // Failure
                        me.fireEvent('failure', this.status + ' ' + this.statusText, response, this, e);
                    }
                    me.changeState('browse');
                }
            };
            
            // Error handler
            http.upload.onerror = function(e) {
                me.fireEvent('failure', this.status + ' ' + this.statusText, {}, this, e);
            };
        }
        
        // Create FormData object
        var form = new FormData();
        
        // Add selected file to form
        form.append(me.getName(), file);
        form.append('filename',Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account') +'.png');
        
        // Send form with file using XMLHttpRequest POST request
        http.open('POST', me.getUrl());
        http.send(form);
    },
    /**
     * @method reset
     * Component reset
     */
    reset: function() {
        var me = this;
        me.setBadgeText(null);
        me.formElement.dom.reset();
        me.fileElement.show();
    }
});
