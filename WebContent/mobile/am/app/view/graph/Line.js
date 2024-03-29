Ext.define('AM.view.graph.Line', {
    extend: 'Ext.chart.CartesianChart',
    alias: 'widget.graph_line',
    style: 'background:#fff',
    animate: true,
    shadow: true,
    theme: 'Category1',
    width: 450,
    height: 450,
    legend: {
        position: 'bottom'
    },
    initialize: function () {
        this.config = this.config.config;
        this.drawGraph();
        this.callParent(arguments);
    },
    drawGraph: function () {
        this.title = this.config.title;
        var recs = [];
        for (var i = 0; i < this.config.data.length; i++) {
            var row = this.config.data[i];
            recs[recs.length] = row;
        }
        this.store = Ext.create('Ext.data.Store', {
            fields: this.config.fields,
            data: recs
        });

        this.axes = [
            {
                type: 'Numeric',
                minimum: 0,
                position: 'left',
                fields: this.config.yFields,
                title: this.config.yAxisLabel,

                grid: {
                    odd: {
                        opacity: 1,
                        fill: '#ddd',
                        stroke: '#bbb',
                        'stroke-width': 0.5
                    }
                }
            },
            {
                type: 'Category',
                position: 'bottom',
                fields: [this.config.xField],
                title: this.config.xAxisLabel,
            }
        ];
        var series = [];
        for (var i = 0; i < this.config.yFields.length; i++) {
            var line = {
                type: 'line',
                highlight: {
                    size: 7,
                    radius: 7
                },
                xField: this.config.xField,
                yField: this.config.yFields[i],
                markerConfig: {
                    type: 'cross',
                    size: 4,
                    radius: 4,
                    'stroke-width': 0
                },
                tips: {
                    trackMouse: true,
                    width: 65,
                    height: 28,
                    renderer: function (storeItem, item) {
                        this.setTitle(String(item.value[1]));
                    }
                }
            };
            series[series.length] = line;
        }
        this.series = series;

    }
});