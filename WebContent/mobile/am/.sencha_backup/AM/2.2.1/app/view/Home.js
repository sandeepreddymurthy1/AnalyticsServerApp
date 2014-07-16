Ext
		.define(
				'AM.view.Home',
				{
					extend : 'Ext.Panel',
					alias : 'widget.home',
					requires : [ 'AM.view.analysis.Main' ],
					config : {

						layout : 'card'
					},
					initialize : function() {
						this.callParent(arguments);
						this.on('painted', this.loadGridData, this);
					},
					loadGridData : function() {
						this.gridView = Ext
								.create(
										'Ext.dataview.DataView',
										{
											store : {
												fields : [ 'name', 'imageUrl' ],
												data : [
														{
															name : 'Dashboard',
															imageUrl : '../images/dashboard/dashboard_icon.jpg'
														},
														{
															name : 'Analysis',
															imageUrl : '../images/dashboard/analysis_icon.jpg'
														},
														{
															name : 'Setup',
															imageUrl : '../images/dashboard/setting_icon.png'
														},
														{
															name : 'Admin',
															imageUrl : '../images/dashboard/setting_icon.png'
														},
														{
															name : 'LogOff',
															imageUrl : '../images/dashboard/logoff_icon.gif'
														} ]
											},
											itemTpl : '<button id="{name}" class="dashboardButton" type="button"><img src="{imageUrl}"></img> <span id="internal_text">{name}</span>'
													+ '</button> </br>',
											scope : this,
											itemSelector : 'button.dashboardButton'
										});
						this.gridView
								.on('itemtap', this.handleClickEvent, this);
						this.optionsPanel = Ext.create('Ext.Panel', {
							items : [ {
								xtype : 'toolbar',
								title : 'Home',
								docked : 'top',
								items : [ {
									xtype : 'spacer'
								} ]
							}, this.gridView ],
							layout : 'fit'
						});
						this.add([ this.optionsPanel ]);
						this.setActiveItem(this.optionsPanel);
					},
					handleClickEvent : function(grid, index, target, record, e,
							eOpts) {
						if (record.data.name == 'Dashboard') {
							if (this.dashboardPanel == undefined) {
								this.dashboardPanel = Ext
										.create('AM.view.dashboard.Main');
								this.add([ this.dashboardPanel ]);
							}
							this.setActiveItem(this.dashboardPanel);
						} else if (record.data.name == 'LogOff') {
							this.up('main').showLoginForm();
						} else if (record.data.name == 'Analysis') {
							if (this.analysisPanel == undefined) {
								this.analysisPanel = Ext
										.create('AM.view.analysis.Main');
								this.add([ this.analysisPanel ]);
							}
							this.setActiveItem(this.analysisPanel, {
								type : 'slide',
								direction : 'left',
								duration : 250
							});
						} else if (record.data.name == 'Setup') {
							Ext.Msg
									.alert('Info',
											'Please use web console to setup new configuration');
						} else if (record.data.name == 'Admin') {
							if (this.adminPanel == undefined) {
								this.adminPanel = Ext
										.create('AM.view.admin.Main');
								this.add([ this.adminPanel ]);
							}
							this.setActiveItem(this.adminPanel);
						}
					},
					showOptionPanel : function() {
						this.setActiveItem(this.optionsPanel, {
							type : 'slide',
							direction : 'right',
							duration : 250
						});
					}

				});
