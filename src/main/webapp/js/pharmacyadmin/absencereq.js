$(document).ready(function() {
    test_login();
})

function test_login() {
    var form = {
        "username" : "phadmin",
        "password" : "test"
    };

    $.ajax({
        type:'POST',
        url: '/auth/login',
        contentType : 'application/json',
        data : JSON.stringify(form),
        success : function(retToken) {
            console.log('LOGGED IN')
            console.log('your token is' + retToken.accessToken)
            localStorage.setItem('myToken', retToken.accessToken);
            getMe();
        },
        error : function() {
            window.location.href = '../index.html'; 
        }
    })
}

function getMe() {
    $.ajax({
        type:'GET',
        url: '/phadmin/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(phadmin) {
            if(phadmin.prvoLogovanje == true) {
                console.log('Prvi put je logovan.')
                window.location.href = 'index.html';
            }
        },
        error : function() {
            window.location.href = '../index.html';
        }    
    })
}

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
