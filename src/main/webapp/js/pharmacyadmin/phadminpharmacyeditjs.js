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


$("#Edit1").click(function() {
    $('#field1').removeAttr('disabled');
});

$("#Edit2").click(function() {
    $('#field2').removeAttr('disabled');
});

$("#Edit3").click(function() {
    $('#field3').removeAttr('disabled');
});