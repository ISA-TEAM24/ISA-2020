$(document).ready(function() {
    getMe();
})

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