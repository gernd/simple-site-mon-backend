var refreshMessages = function(){
    console.log("Refreshing messages");
    $.get("http://localhost:8080/messages",function(response){
    var messagesHtml = "";
    for(messageObject of response){
        messagesHtml += messageObject.message + '<br>';
    }
        $('#messages').html(messagesHtml);
    });
};

$(document).ready(function(){
    console.log("Ready");
    /*
    refreshMessages();

    $("#sendMessageButton").click(function(){
        var message = $("#messageInput").val()
        console.log("Sending message " + message);

        $.ajax({
          url:"http://localhost:8080/messages",
          type:"POST",
          data:JSON.stringify({'message' : message}),
          contentType:"application/json; charset=utf-8",
          dataType:"json",
          complete: function(){
              refreshMessages();
          }
        });

    });
    */
})
