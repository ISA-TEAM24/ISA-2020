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
            localStorage.setItem('role', 'phadmin');
            var message = document.getElementById("welcomeMessage");
            message.innerHTML = `Welcome back ${phadmin.ime} ${phadmin.prezime}`;
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
        $('#errormsg').text("Old password field empty");
        $('#successmsg').text("");

        return;
    }

    if (newpw1 == "") {
        $('#errormsg').text("New password field empty");
        $('#successmsg').text("");

        return;
    }

    if (newpw2 == "") {
        $('#errormsg').text("Confirm password field empty");
        $('#successmsg').text("");

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

    if (newpw1.length < 4) {

        $('#errormsg').text("New password has to be at least 4 characters or longer");
        $('#successmsg').text("");
 
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
            $('#successmsg').text("Successfully changed password");

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
            $('#errormsg').text("Old password is not correct");

        }
    })
}