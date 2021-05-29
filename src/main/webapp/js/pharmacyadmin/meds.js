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

$("#AddMedBtn").click(function() {
    document.location.href = 'allmeds.html';
});

$("#OrderMedBtn").click(function() {
    document.location.href = 'allmeds.html';
});

$("#MedsPricelistBtn").click(function() {
    document.location.href = 'allmeds.html';
});