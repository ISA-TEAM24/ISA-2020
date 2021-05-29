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
            localStorage.setItem('role', 'phadmin');
            if(phadmin.prvoLogovanje == true) {
                console.log('Prvi put je logovan.')
                requirePwChange(phadmin.username.toString());
            }
        },
        error : function() {
            window.location.href = '../index.html';
        }    
    })
}

function requirePwChange(username) {
    $('#errormsg').text("You have to change password on your first log");
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

            $.ajax({
                type:'POST',
                url: '/phadmin/firstlogpwchange',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function() {
                console.log('success in firstlogpwchange')
                },
                error : function() {
                    console.log(username)
                    console.log('error in firstlogpwchange')
                }
                
            })

        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
            $('#successmsg').text("");
            $('#errormsg').text("Greška!");

        }
        
    })
}