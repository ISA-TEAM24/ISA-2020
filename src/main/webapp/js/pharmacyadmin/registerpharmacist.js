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

function register() {
    var pharmacist = {
        email : $('#email').val().trim(),
        username :  $('#username').val().trim(),
        password :  $('#password').val().trim(),
        ime :  $('#ime').val().trim(),
        prezime : $('#prezime').val().trim(),
        adresa :  $('#adresa').val().trim(),
        grad :  $('#grad').val().trim(),
        drzava :  $('#drzava').val().trim(),
        telefon : $('#telefon').val().trim(),
        odDatum : $('#odDatum').val(),
        doDatum : $('#doDatum').val(),
        odVreme : $('#odVreme').val(),
        doVreme : $('#doVreme').val(),
        neradniDani : $('#neradniDani').val()
    }

    $.ajax({
        type:'POST',
        url: '/pharmacist/addpharmacist',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(pharmacist),
        success : function(pharmacy) {
            console.log('yes')
        },
        error : function() {
            console.log('error occured')
        }
    })   
}

