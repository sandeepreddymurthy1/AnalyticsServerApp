Ext.define('AM.view.analysis.Map', {
	extend : 'Ext.Panel',
	alias : 'widget.analysis_map',

	config : {
		layout : 'card',
		mapMarkers : [],
		data : null,
		firstRender : false,
		style : 'background: white',
		items : [ {
			xtype : 'toolbar',
			top : 0,
			left : 0,
			zIndex : 50,
			style : {
				background : 'none'
			},
			items : [ {
				iconCls : 'back',
				text : 'Back',
				ui : 'back',
				handler : function() {
					this.up('analysis_main').showReportList();
				}
			}, {
				xtype : 'segmentedbutton',
				items : [ {
					text : 'Map',
					pressed : true,
					handler : function() {
						 this.up('analysis_map').setActiveItem(0);
					}
				}, {
					text : 'List',
					handler : function() {
						 this.up('analysis_map').setActiveItem(1);
					}
				} ]
			}, {
				xtype : 'spacer'
			}, {
				xtype : 'common_timeselect',
				width : '25%'
			} ]
		}, {
			xtype : 'map',
			layout : 'fit',
			mapOptions : {
				center : new google.maps.LatLng(34.33, -101.135672),
				zoom : 5,
				mapTypeId : google.maps.MapTypeId.ROADMAP,
				navigationControl : true,
				navigationControlOptions : {
					style : google.maps.NavigationControlStyle.DEFAULT
				}
			},

			listeners : {
				maprender : function(comp, map) {
					var analysisMap = this.up('analysis_map');
					analysisMap.setFirstRender(true);
					analysisMap.infowindow = new google.maps.InfoWindow({
						content : ''
					});
					analysisMap.drawMarkers();
				}

			}
		}, {
			layout : 'fit',
			items : [ {
				xtype : 'common_grid',
				title : 'Grid',
				store : {
					fields : ['city','noOfHits']
				},
				layout : 'fit',
				columns : [ {
					header : 'Name',
					dataIndex : 'city',
					width : '70%'
				}, {
					header : 'Hits',
					dataIndex : 'noOfHits',
					width : '30%'
				} ]
			} ]
		} ]

	},
	initialize : function() {
		this.callParent();
		// Register for event on select
		this.down('common_timeselect').on('change', function(field,newValue){
			ajaxService.processRequest(this.getServiceName(),newValue,{
				success : function(data){
					this.loadData(data);
				},
				scope : this
			});
		},this);

	},
	// remove all markers
	removeAllMarkers : function() {
		for ( var i = 0; i < this.getMapMarkers().length; i++) {
			this.getMapMarkers()[i].setMap(null);
		}
		this.setMapMarkers(new Array());
	},
	setData : function(data) {
		this.data = data;
		if( this.data.latLngData != undefined ){
			this.down('common_grid').getStore().setData(this.data.latLngData);
		}
		if (this.getFirstRender()) {
			this.drawMarkers();
		}
	},
	drawMarkers : function() {
		if (this.data != null) {
			var gMap = this.down('map').getMap();
			for ( var i = 0; i < this.data.latLngData.length; i++) {
				var rec = this.data.latLngData[i];
				var marker = new google.maps.Marker({
					map : gMap,
					animation : google.maps.Animation.DROP,
					recInfo : rec,
					position : new google.maps.LatLng(rec.lat, rec.lng)
				});
				this.getMapMarkers().push(marker);
				google.maps.event.addListener(marker, 'click', function() {
					 Ext.Msg.alert('Information', 'City :'+ this.recInfo.city+' </br>No of Visits :'+ this.recInfo.noOfHits+'</br>');
				});
			}
		}

	}
});
// </feature>
