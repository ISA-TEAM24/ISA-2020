$(document).ready(function () {

    $("#Reject1ph").click(function() {
        var textarea = "";
        textarea += '<div class="form-group">' +
                        '<textarea class="form-control" rows="5" id="field3" placeholder="Enter reason"></textarea>' +
                    '</div>' +
                    '<button type="button" id="SendAnsw1" class="btn btn-primary">Send answer</button>';
        
        $("#insertreject1").append(textarea);
    });

    $("#Reject2ph").click(function() {
        var textarea = "";
        textarea += '<div class="form-group">' +
                        '<textarea class="form-control" rows="5" id="field3" placeholder="Enter reason"></textarea>' +
                    '</div>' +
                    '<button type="button" id="SendAnsw1" class="btn btn-primary">Send answer</button>';
        
        $("#insertreject1").append(textarea);
    });



    $(document).on('click', '#SendAnsw1', function(){ 
        location.reload(true);
   });

   $("#Reject1derm").click(function() {
    var textarea = "";
    textarea += '<div class="form-group">' +
                    '<textarea class="form-control" rows="5" id="field3" placeholder="Enter reason"></textarea>' +
                '</div>' +
                '<button type="button" id="SendAnsw2" class="btn btn-primary">Send answer</button>';
    
    $("#insertreject2").append(textarea);
    });

    $("#Reject2derm").click(function() {
        var textarea = "";
        textarea += '<div class="form-group">' +
                        '<textarea class="form-control" rows="5" id="field3" placeholder="Enter reason"></textarea>' +
                    '</div>' +
                    '<button type="button" id="SendAnsw2" class="btn btn-primary">Send answer</button>';
        
        $("#insertreject2").append(textarea);
    });

    $(document).on('click', '#SendAnsw2', function(){ 
        location.reload(true);
    });
});