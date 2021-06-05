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
        }, error : function() {
            alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
        }    
    })
}

function createAction() {

    if (!validateFields()) {
        alert("You must fill all input fields");
        return;
    }

    var promotion = {
        naslov : $('#naslov').val(),
        datum : $('#datum').val(),
        tekst : $('#tekst').val()
    }
    
    $.ajax({
        type:'POST',
        url: '/promotion/create',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(promotion),
        success : function() {
            alert("Promotion successfully created");
            location.reload();
        },
        error : function() {
            console.log('error occured')
        }
    })
}

function validateFields() {
    
    if ($('#naslov').val() == "") return false;
    if ($('#tekst').val() == "") return false;
    if (!$('#datum').val()) return false;

    return true;
}