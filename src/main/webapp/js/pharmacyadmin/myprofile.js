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
            addProfileData(phadmin);
            if(phadmin.prvoLogovanje == true) {
                console.log('Prvi put je logovan.')
                window.location.href = 'index.html';
            }
            refreshToken();
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

    if (validateUserFields(obj) == false){
        return;
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
            refreshToken();
        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
            $('#successmsg').text("");
            $('#errormsg').text("Old password is not correct");

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

function validateUserFields(obj) {

    var pattern = $("#nameid").attr("pattern");
    var re = new RegExp(pattern);
    if (!re.test($("#nameid").val())) {
        alert('Forbidden character in name field')
        console.log('name does not match pattern')
        return false;
    }

    if (!re.test($("#lastnameid").val())) {
        console.log('last name does not match pattern')
        alert('Forbidden character in last name field')
        return false;
    }

    pattern = $("#addressid").attr("pattern")
    re = new RegExp(pattern)
    if (!re.test($("#addressid").val())) {
        console.log('address does not match pattern')
        alert('Forbidden character in address field')
        return false;
    }

    pattern = $("#phoneid").attr("pattern")
    re = new RegExp(pattern)
    if (!re.test($("#phoneid").val())) {
        console.log('phone does not match pattern')
        alert('Forbidden character in phone field')
        return false;
    }

    pattern = $("#cityid").attr("pattern")
    re = new RegExp(pattern)
    if (!re.test($("#cityid").val())) {
        console.log('city does not match pattern')
        alert('Forbidden character in city field')
        return false;
    }

    if (!re.test($("#countryid").val())) {
        console.log('country does not match pattern')
        alert('Forbidden character in country field')
        return false;
    }

    
    return true;
}