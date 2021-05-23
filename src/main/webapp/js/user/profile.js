// list of IDs
// profile-email-input 
// profile-password-old-input
// profile-name-input
// profile-password-new-input
// profile-lastname-input
// profile-address-input
// profile-phone-input
// profile-save-button - Profile tab - save changes button
// take everything from every field
// perhaps a check for whether we're changing passwords too
// id="v-pills-profile-tab" 
// profile-change-password-button
// #profile-password-confirm-input
$(document).ready(function() {
    test_login();
    
})

function saveUser(){
    
    var obj = {

     email :  $('#profile-email-input').val().trim(),
     name :  $('#profile-name-input').val().trim(),
     lastName :  $('#profile-lastname-input').val().trim(),
     address :  $('#profile-address-input').val().trim(),
     country :  $('#profile-country-input').val().trim(),
     city :  $('#profile-city-input').val().trim(),
     phoneNumber :  $('#profile-phone-input').val().trim()    
    }

    $.ajax({
        type:'PUT',
        url: '/api/user/edit/me',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function(user) {
            fillProfile(user);
        },
        error : function() {
            console.log('DAMN SON')
        }
        
    })


    
}

function test_login() {
    var form = {
        "username" : "test",
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
            reloadProfile();
        },
        error : function() {
            console.log('FAIL LOGIN')
        }
        
    })
}


function reloadProfile() {

    $.ajax({
        type:'GET',
        url: '/api/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(user) {
            fillProfile(user);
        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
        }
        
    })
}

function fillProfile(user) {
    $('#profile-email-input').val(user.email)
    $('#profile-name-input').val(user.ime) 
    $('#profile-lastname-input').val(user.prezime)
    $('#profile-address-input').val(user.adresa)
    $('#profile-country-input').val(user.drzava)
    $('#profile-city-input').val(user.grad)  
    $('#profile-phone-input').val(user.telefon)    
}

function changePassword() {
    // /auth/change-password

    if ($('#profile-password-old-input').val() == "") {
        return;
    }

    if ($('#profile-password-new-input').val() == "") {
        return;
    }

    if ($('#profile-password-confirm-input').val() == "") {
        return;
    }

    if ($('#profile-password-new-input').val() != $('#profile-password-confirm-input').val()) {
        $('#profile-change-password-button').html('Passwords must match!')
        console.log('bad match')
        countDownToButtonEdit(2)   
        return;
    }

    var obj = {
        oldPassword : $('#profile-password-old-input').val(),
        newPassword : $('#profile-password-new-input').val()
    }

    $.ajax({
        type:'POST',
        url: '/auth/change-password',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function() {
            $('#profile-change-password-button').html('Successfully changed pw!')
            countDownToReload(2);

        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
            $('#profile-change-password-button').html('Failed to change pw!')
            countDownToReload(2);
        }
        
    })
}

function countDownToReload(x) {
    var counter = x;
    var interval = setInterval(function() {
        counter--;
        // Display 'counter' wherever you want to display it.
        if (counter < 1) {
            // Display a login box
            window.location.reload();
        }


    }, 1000);
}

function countDownToButtonEdit(x) {
    var counter = x;
    var interval = setInterval(function() {
        counter--;
        // Display 'counter' wherever you want to display it.
        if (counter < 1) {
            // Display a login box
            $('#profile-change-password-button').html('Change Password')
        }


    }, 1000);
}