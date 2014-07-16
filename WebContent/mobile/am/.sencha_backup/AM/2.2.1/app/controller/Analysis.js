Ext
		.define(
				'AM.controller.Analysis',
				{
					extend : 'Ext.app.Controller',
					config : {
						refs : {
							analysisView : 'analysis_main',
							configList : 'analysis_configList',
							reportList : 'analysis_reportList',
							browserView : 'analysis_browser',
							osView : 'analysis_os',
							relativeAccessView : 'analysis_relativeSite',
							mapView : 'analysis_map'
						},
						control : {
							'analysis_configList' : {
								itemtap : 'configListClicked'
							},
							'analysis_reportList' : {
								itemtap : 'reportListClicked'
							}
						}
					},
					init : function() {
						this.callParent(arguments);
						console.log('Login Controller Initialized');

					},

					reportListClicked : function(it, idx, dv, rec) {
						switch (rec.raw.reportType) {
						case 'browserUsage':
							ajaxService.processRequest('browserUsage',
									'getUsageComplete', {
										success : function(data) {
											this.getBrowserView()
													.loadData(data);
											this.getAnalysisView()
													.showBrowserPanel();
										},
										scope : this
									});
							break;
						case 'osUsage':
							ajaxService.processRequest('osUsage',
									'getUsageComplete', {
										success : function(data) {
											this.getOsView().loadData(data);
											this.getAnalysisView()
													.setActiveItem(
															this.getOsView());
										},
										scope : this
									});
							break;
						case 'siteRelativeUsage':
							ajaxService
									.processRequest(
											'relativeSiteUsage',
											'getUsageComplete',
											{
												success : function(data) {
													this
															.getRelativeAccessView()
															.loadData(data);
													this
															.getAnalysisView()
															.setActiveItem(
																	this
																			.getRelativeAccessView());
												},
												scope : this
											});
							break;
						case 'locationVisit':
							ajaxService.processRequest("location","getSiteAccessGeoData", {
										success : function(data) {
											this.getMapView().setData(data);
											this.getAnalysisView()
													.setActiveItem(
															this.getMapView());
										},
										scope : this
									});
							break;
						}
					},

					configListClicked : function(it, idx, dv, rec) {

						ajaxService
								.processRequest(
										'analysis',
										'getAnalyticsForConfig',
										{
											success : function(data) {
												this.getReportList().getStore()
														.setData(data);
												this.getAnalysisView()
														.showReportList();
											},

											failure : function(data) {
												Ext.Msg
														.alert('Failed',
																"Email and password didnot match our records, please re-enter or register ");

											},
											args : rec.raw,
											scope : this,
											loadMaskEl : Ext.getBody(),
											loadMaskMessage : 'Logging in.... Please wait'
										});
					}
				});