/*
 This file is generated and updated by Sencha Cmd. You can edit this file as
 needed for your application, but these edits will have to be merged by
 Sencha Cmd when it performs code generation tasks such as generating new
 models, controllers or views and when running "sencha app upgrade".

 Ideally changes to this file would be limited and most work would be done
 in other places (such as Controllers). If Sencha Cmd cannot merge your
 changes and its generated code, it will produce a "merge conflict" that you
 will need to resolve manually.
 */

Ext.application({
    name: 'AM',
    requires : [ 'Ext.MessageBox', 'AM.service.BaseService',
        'AM.view.ColorPatterns','AM.view.common.TimeSelect' ],
    views : [ 'Main', 'Login','common.Grid', 'dashboard.Main', 'Home', 'graph.PieChart',
        'analysis.Main', 'analysis.ConfigList', 'analysis.ReportList',
        'analysis.Browser', 'analysis.OS','analysis.RelativeSite'
        ,'analysis.Map'
        //,'admin.Main','admin.PersonalInfo','admin.SystemInfo','admin.SystemUser'
    ],
    stores : [ 'Pie', 'analysis.ConfigList' ],
    controllers : [ 'Login', 'Analysis' ],

    icon: {
        '57': 'resources/icons/Icon.png',
        '72': 'resources/icons/Icon~ipad.png',
        '114': 'resources/icons/Icon@2x.png',
        '144': 'resources/icons/Icon~ipad@2x.png'
    },

    isIconPrecomposed: true,

    startupImage: {
        '320x460': 'resources/startup/320x460.jpg',
        '640x920': 'resources/startup/640x920.png',
        '768x1004': 'resources/startup/768x1004.png',
        '748x1024': 'resources/startup/748x1024.png',
        '1536x2008': 'resources/startup/1536x2008.png',
        '1496x2048': 'resources/startup/1496x2048.png'
    },
//    viewPort : {
//        autoMaximize : !Ext.os.is.iOS7
//    },
//    eventPublishers : {
//        touchGestures : {
//            moveThrottle : 5
//        }
//    },
    launch: function() {
        // Destroy the #appLoadingIndicator element
        Ext.fly('appLoadingIndicator').destroy();

        //rootAjaxUrl = "http://ec2-54-213-158-121.us-west-2.compute.amazonaws.com:8080/WebAnalyticsWebConsole/";
        rootAjaxUrl = "http://localhost:8080/WebAnalyticsWebConsole/";
        // Initialize the main view
        ajaxService = new AM.service.BaseService();
        var next = Ext.create('Ext.Panel', {
            layout : 'fit',
            items : [ {
                xtype : 'main',
                id : 'main'
            } ],
            fullscreen : true

        });
        Ext.Viewport.add(next);
    },

    onUpdated: function() {
        Ext.Msg.confirm(
            "Application Update",
            "This application has just successfully been updated to the latest version. Reload now?",
            function(buttonId) {
                if (buttonId === 'yes') {
                    window.location.reload();
                }
            }
        );
    }
});
