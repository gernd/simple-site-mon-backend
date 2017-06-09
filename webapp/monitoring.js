var updatedMonitoredResources = function(){
    console.log("Updating monitored resources");
    $.get("http://localhost:8081/monitored-sites",function(response){
        console.log(response);
        var html = "";
        for(monitoredResource of response.monitoredSites){
            html += monitoredResource.url + '<br>';
        }
        console.log(html);
        $('#monitoredResources').html(html);
    });
};

$(document).ready(function(){
    console.log("Ready");
    updatedMonitoredResources();

    $("#addUrlButton").click(function(){
        var url = $("#urlInput").val()
        console.log("Adding URL for monitoring " + url);

        $.ajax({
          url:"http://localhost:8081/monitored-sites",
          type:"POST",
          data:JSON.stringify({'url' : url}),
          contentType:"application/json; charset=utf-8",
          dataType:"json",
          complete: function(){
            console.log("Added");
            updatedMonitoredResources();
          }
        });
    });
})
