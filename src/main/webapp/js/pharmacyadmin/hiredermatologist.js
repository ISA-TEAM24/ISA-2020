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

$("#Hiredermatolgbtn1").click(function() {
    document.location.href = 'dermatologworktime.html';
});

$("#Hiredermatolgbtn2").click(function() {
    document.location.href = 'dermatologworktime.html';
});

$("#Hiredermatolgbtn3").click(function() {
    document.location.href = 'dermatologworktime.html';
});