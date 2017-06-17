var MONITORED_RESOURCES_BASE_URL = "http://localhost:8081/monitored-sites";
var monitoredResourcesIds = [];

var initializeMonitoredResources = function(){
    console.log("Initializing monitored resources");
    $.get(MONITORED_RESOURCES_BASE_URL,function(response){
        console.log(response);
        for(monitoredResource of response.monitoredSites){
            // create dom node for displaying monitored resource data
            var domNodeForResource =
                $("<div id='monitoredResource_" + monitoredResource.id + "'>" +
                "<h4>" + monitoredResource.url + "</h4>" +
                "<did id='monitoredResource_" + monitoredResource.id + "_data'>data</div>" +
                "</div>");
            $("#monitoredResources").append(domNodeForResource);
            monitoredResourcesIds.push(monitoredResource.id);
        }
    });
}

var updatedMonitoredResources = function(){
    console.log("Updating resources");
    for(monitoredResourceId of monitoredResourcesIds){
        console.log(monitoredResourceId);
        $.get(MONITORED_RESOURCES_BASE_URL + "/" + monitoredResourceId,
        function(response){
            console.log(response);
            $("#monitoredResource_" + response.id + "_data").html(JSON.stringify(response.monitoringResults));
        });
    }
};

$(document).ready(function(){

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
            // TODO: add id for monitored resources
          }
        });
    });
})
