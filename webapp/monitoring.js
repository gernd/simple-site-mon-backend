$(document).ready(function(){
    console.log("Ready");

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
          }
        });

    });
})
