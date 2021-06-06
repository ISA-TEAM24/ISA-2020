var today = new Date().toISOString().split('T')[0];
document.getElementsByName("setTodaysDate1")[0].setAttribute('min', today);
document.getElementsByName("setTodaysDate2")[0].setAttribute('min', today);

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
            refreshToken();
        }, error : function() {
            //alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
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

    if (validateUserFields(pharmacist) == false) {
        return;
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
            document.location.href = 'pharmacistslist.html';
            refreshToken();
        },
        error : function() {
            console.log('error occured')
        }
    })   
}

function validateUserFields(pharmacist) {

    var pattern = $('#ime').attr("pattern");
    var re = new RegExp(pattern);
    if (!re.test($('#ime').val())) {
        alert('Forbidden character in name field')
        return false;
    }

    pattern = $('#prezime').attr("pattern");
    re = new RegExp(pattern);
    if (!re.test($("#prezime").val())) {
        alert('Forbidden character in last name field')
        return false;
    }

    pattern = $("#email").attr("pattern")
    re = new RegExp(pattern)
    if (!re.test($("#email").val())) {
        alert('Email must be in format "name@domain.com"')
        return false;
    }

    pattern = $("#username").attr("pattern")
    re = new RegExp(pattern)
    if (!re.test($("#username").val())) {
        alert('Forbidden character in username field')
        return false;
    }

    pattern = $("#drzava").attr("pattern")
    re = new RegExp(pattern)
    if (!re.test($("#drzava").val())) {
        alert('Forbidden character in country field')
        return false;
    }

    pattern = $('#grad').attr("pattern");
    re = new RegExp(pattern);
    if (!re.test($("#grad").val())) {
        alert('Forbidden character in city field')
        return false;
    }

    pattern = $("#adresa").attr("pattern")
    re = new RegExp(pattern)
    if (!re.test($("#adresa").val())) {
        alert('Forbidden character in address field')
        return false;
    }

    pattern = $("#telefon").attr("pattern")
    re = new RegExp(pattern)
    if (!re.test($("#telefon").val())) {
        alert('Forbidden character in phone field')
        return false;
    }

    if ($("#password").val().length < 4) {
        alert('Password has to be at least 4 characters or longer')
        return false;
    }

    if ($("#password").val() != $("#cpassword").val()) {
        alert('Passwords are not same')
        return false;
    }

    if (!$('#odDatum').val()) {
        alert('Start date not setted correctly')
        return false; 
    }

    if (!$('#doDatum').val()) { 
        alert('End date not setted correctly')
        return false; 
    }

    if (!$('#odVreme').val()) { 
        alert('Start time not setted correctly')
        return false;
    }

    if (!$('#doVreme').val()) { 
        alert('End time not setted correctly')
        return false;
    }

    if ($('#odDatum').val() > $('#doDatum').val()) { 
        alert("End date can't be before start date")
        return false;
    }

    return true;
}


