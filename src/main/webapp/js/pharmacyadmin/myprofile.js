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
            addProfileData(phadmin);
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

function addProfileData(phadmin) {
    $('#usernameid').val(phadmin.username)
    $('#emailid').val(phadmin.email)
    $('#nameid').val(phadmin.ime)
    $('#lastnameid').val(phadmin.prezime)
    $('#phoneid').val(phadmin.telefon)
    $('#addressid').val(phadmin.adresa)
    $('#cityid').val(phadmin.grad)
    $('#countryid').val(phadmin.drzava)

}

function saveNewData(){
    var obj = {
     email :  $('#emailid').val().trim(),
     name :  $('#nameid').val().trim(),
     lastName :  $('#lastnameid').val().trim(),
     address :  $('#addressid').val().trim(),
     country :  $('#countryid').val().trim(),
     city :  $('#cityid').val().trim(),
     phoneNumber :  $('#phoneid').val().trim()
    }

    $.ajax({
        type:'PUT',
        url: '/phadmin/editdata',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function(phadmin) {
            addProfileData(phadmin);

            $('#successmsgData').text("Successfully changed data.");

            var millisecondsToWait = 500;
            setTimeout(function() {
                location.reload();
            }, millisecondsToWait);

        },
        error : function() {
            console.log('error occured')
        }
        
    })   
}

function changepw() {
    oldpw = document.getElementById("oldpw").value
    newpw1 = document.getElementById("newpw1").value
    newpw2 = document.getElementById("newpw2").value

    if (oldpw == "") {
        return;
    }

    if (newpw1 == "") {
        return;
    }

    if (newpw2 == "") {
        return;
    }

    if (oldpw == newpw1) {
        $('#errormsg').text("Password can't stay the same.");
        $('#successmsg').text("");

        return;
    }

    if (newpw1 != newpw2) {

        console.log(document.getElementById("newpw1").value)
        console.log(document.getElementById("newpw2").value)

        $('#errormsg').text("Passwords don't match.");
        $('#successmsg').text("");
 
        console.log('bad match')
        return;
    }

    var obj = {
        oldPassword : oldpw,
        newPassword : newpw1
    }

    $.ajax({
        type:'POST',
        url: '/phadmin/changepw',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function() {
            $('#errormsg').text("");
            $('#successmsg').text("Uspešno izmenjena lozinka!");

            var millisecondsToWait = 2000;
            setTimeout(function() {
                $('#myModal').modal('toggle');
            }, millisecondsToWait);
        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
            $('#successmsg').text("");
            $('#errormsg').text("Greška!");

        }
        
    })
}

function clearpwFields() {
    document.getElementById("oldpw").value = '' 
    document.getElementById("newpw1").value = '' 
    document.getElementById("newpw2").value = '' 

    $('#errormsg').text("");
    $('#successmsg').text("");
}

function requirePwChange(username) {
    $('#errormsg').text("");
    $('#myModal').modal('show'); 

    $('#closepwModal').click( function() {
        location.reload();
    });
    $('#closeBtn').click( function() {
        location.reload();
    });

    console.log(username)
    changepw()
}
