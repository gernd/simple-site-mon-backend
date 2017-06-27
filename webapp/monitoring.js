var MONITORED_RESOURCES_BASE_URL = "http://localhost:8081/monitored-sites";
var monitoredResourcesIds = [];
var chart;

var initializeChart = function () {
    chart = Highcharts.chart('monitoredResourcesChart', {

        title: {
            text: 'Monitored Resources Response Time'
        },
        subtitle: {
            text: 'Refresh Interval: 3 seconds'
        },
        yAxis: {
            title: {
                text: 'Response time (ms)'
            }
        },
        xAxis: {
            title: {
                text: 'timestamp (seconds)'
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        }
    });
};

var getChartDataFromMonitoringData = function (monitoringDataArr) {
    return monitoringDataArr.map(function (monitoringResult) {
        return [monitoringResult.timestamp, monitoringResult.timeNeededForRequest];
    });
};

var initializeChartForResource = function (resource) {
    // fetch monitored data for resource and add series in chart
    // with existing monitoring data
    $.get(MONITORED_RESOURCES_BASE_URL + "/" + resource.id,
        function (response) {
            var chartData = getChartDataFromMonitoringData(response.monitoringResults);
            var chartSeries = {
                name: resource.url,
                resourceId: resource.id,
                data: chartData
            };
            chart.addSeries(chartSeries);
        });
}

var initializeMonitoredResources = function () {
    console.log("Initializing monitored resources");
    $.get(MONITORED_RESOURCES_BASE_URL, function (response) {
        for (var monitoredResource of response.monitoredSites) {
            console.log(monitoredResource);
            initializeChartForResource(monitoredResource);
            monitoredResourcesIds.push(monitoredResource.id);
        }
    });
};

var updateChartForResource = function (monitoredResourceId) {
    $.get(MONITORED_RESOURCES_BASE_URL + "/" + monitoredResourceId,
        function (response) {
            // get corresponding chart object identified by URL of monitored resource
            var seriesForResources = chart.series.filter(function (e) {
                return e.options.resourceId === monitoredResourceId;
            })[0];
            // add the latest measurement assuming it has already been updated
            var lastMeasurement = response.monitoringResults[response.monitoringResults.length - 1];
            seriesForResources.addPoint([lastMeasurement.timestamp, lastMeasurement.timeNeededForRequest]);
        });
};

var updatedMonitoredResources = function () {
    for (var monitoredResourceId of monitoredResourcesIds) {
        updateChartForResource(monitoredResourceId);
    }
};

$(document).ready(function () {

    initializeChart();

    // initialize functionality for updating the monitored resource
    setInterval(updatedMonitoredResources, 3000);

    initializeMonitoredResources();

    // register click listener
    $("#addUrlButton").click(function () {
        var url = $("#urlInput").val()
        console.log("Adding URL for monitoring " + url);

        $.ajax({
            url: MONITORED_RESOURCES_BASE_URL,
            type: "POST",
            data: JSON.stringify({'url': url}),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            complete: function (data) {
                console.log("Added");
                console.log(data);
                var monitoredResourceId = data.responseJSON.id;
                monitoredResourcesIds.push(monitoredResourceId);
                // TODO use URL from created resource, as it could have been normalized
                var chartSeries = {
                    name: url,
                    resourceId: monitoredResourceId,
                    data: []
                };
                chart.addSeries(chartSeries);
            }
        });
    });
})
