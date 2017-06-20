var MONITORED_RESOURCES_BASE_URL = "http://localhost:8081/monitored-sites";
var monitoredResourcesIds = [];
var chart;

var initializeChart = function(){
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

var getChartDataFromMonitoringData = function(monitoringDataArr){
    return monitoringDataArr.map(function(monitoringResult){
        return [monitoringResult.timestamp, monitoringResult.timeNeededForRequest];
    });
};

var initializeChartForResource = function(resource){
    // fetch monitored data for resource and add series in chart
    // with existing monitoring data
    $.get(MONITORED_RESOURCES_BASE_URL + "/" + resource.id,
    function(response){
        var chartData = getChartDataFromMonitoringData(response.monitoringResults);
        var chartSeries = {
            name : resource.url,
            data: chartData
        };
        chart.addSeries(chartSeries);
    });
}

var initializeMonitoredResources = function(){
    console.log("Initializing monitored resources");
    $.get(MONITORED_RESOURCES_BASE_URL,function(response){
        for(var monitoredResource of response.monitoredSites){
            console.log(monitoredResource);
            initializeChartForResource(monitoredResource);
        }
    });
};

var updatedMonitoredResources = function(){
    console.log("Updating resources");
    for(var monitoredResourceId of monitoredResourcesIds){
        console.log(monitoredResourceId);
        $.get(MONITORED_RESOURCES_BASE_URL + "/" + monitoredResourceId,
        function(response){
            console.log(response);
            // $("#monitoredResource_" + response.id + "_data").html(JSON.stringify(response.monitoringResults));
        });
    }
};

$(document).ready(function(){

    initializeChart();

    // initialize functionality for updating the monitored resource
    setInterval(updatedMonitoredResources, 3000);

    initializeMonitoredResources();

    // register click listener
    $("#addUrlButton").click(function(){
        var url = $("#urlInput").val()
        console.log("Adding URL for monitoring " + url);

        $.ajax({
          url:MONITORED_RESOURCES_BASE_URL,
          type:"POST",
          data:JSON.stringify({'url' : url}),
          contentType:"application/json; charset=utf-8",
          dataType:"json",
          complete: function(data){
            console.log("Added");
            console.log(data);
            var monitoredResourceId = data.responseJSON.id;
            monitoredResourcesIds.push(monitoredResourceId);
            // TODO: add id for monitored resources
          }
        });
    });
})
