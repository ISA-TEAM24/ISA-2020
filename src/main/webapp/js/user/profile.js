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

//$('#profile-email-input').
const name_field = $('#profile-name-input')
const lastname_field = $('#profile-lastname-input')
const address_field = $('#profile-address-input')
const country_field = $('#profile-country-input')
const city_field = $('#profile-city-input')
const phone_field = $('#profile-phone-input')

const alert_box = $('#alert_modal')
const alert_title = $('#alert_title')
const alert_body = $('#alert_body')

$(document).ready(function() {
    checkReservationPenalties()
    //resetPenaltiesIfNeeded()
    reloadProfile()
    refreshToken()
    limitDatePicker('consult-date-picker')

    var url = window.location.href
    var tabby = url.split("#")[1]
    if (tabby != undefined) {
        if (tabby == 'consult') {
            // redirect to that tab
            $('#v-pills-pickup-tab').click()
        }
        else if(tabby == 'reservenew') {
            $('#v-pills-reservemeds-tab').click()
            $('#new_reserved_input').val(localStorage.getItem('looking_for_med'))
            $('#findmed_xyz').click()

        }
    }
    
})

function checkReservationPenalties() {

    $.ajax({
        type:'GET',
        url: '/reservation/penaltycheck',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data, statusText, xhr) {
            if (xhr.status == 202) {
                console.log('youve been penalized')
            }
            else {
                console.log('no penalty for reservation')
            }
            resetPenaltiesIfNeeded()
        },
        error : function() {
            console.log('Could not reset penalties')
        }
        
    })

}

function resetPenaltiesIfNeeded() {

    $.ajax({
        type:'GET',
        url: '/api/user/penalty/reset',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data, statusText, xhr) {
            if (xhr.status == 202) {
                showError('Penalty reset', 'Your penalties have been reset!')
            }
            console.log('success')
            //fillProfile(user);
        },
        error : function() {
            console.log('Could not reset penalties')
        }
        
    })

}

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

    if (validateUserFields(obj) == false){
        return;
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
            showError('Information change', 'You have successfully edited your information!')
            console.log('success')
            fillProfile(user);
        },
        error : function() {
            console.log('DAMN SON')
        }
        
    })


    
}

/*function test_login() {
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
            showError('Error message', 'Could not load profile information.')
        }
        
    })
} */


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
            showError('Error message', 'Could not load profile information.')
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
        showError('Error message', 'Current password field is empty')
        return;
    }
    

    if ($('#profile-password-new-input').val() == "") {
        showError('Error message', 'New password field is empty')
        return;
    }

    if ($('#profile-password-confirm-input').val() == "") {
        showError('Error message', 'Confirm password field is empty')
        return;
    }

    if ($('#profile-password-new-input').val().length < 4) {
        showError('Error message', 'New password has to be at least 4 characters or longer')
        return;
    }



    if ($('#profile-password-new-input').val() != $('#profile-password-confirm-input').val()) {
        //$('#profile-change-password-button').html('Passwords must match!')
        console.log('bad match')
        //countDownToButtonEdit(2)
        showError('Error message', 'New password and Confirm password fields do not match')   
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
            //$('#profile-change-password-button').html('Successfully changed pw!')
            //countDownToReload(2);
            showError('Password change', 'You have successfully changed your password!')

        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
            //$('#profile-change-password-button').html('Failed to change pw!')
           // countDownToReload(2);
           showError('Password change Error', 'There has been an error changing your password please try again.')
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

function validateUserFields(obj) {

    var pattern = name_field.attr("pattern");
    var re = new RegExp(pattern);
    if (!re.test(name_field.val())) {
        showError('Error message', 'Forbidden character in name field')
        console.log('name does not match pattern')
        return false;
    }

    if (!re.test(lastname_field.val())) {
        console.log('last name does not match pattern')
        showError('Error message', 'Forbidden character in last name field')
        return false;
    }

    pattern = address_field.attr("pattern")
    re = new RegExp(pattern)
    if (!re.test(address_field.val())) {
        console.log('address does not match pattern')
        showError('Error message', 'Forbidden character in address field')
        return false;
    }

    pattern = phone_field.attr("pattern")
    re = new RegExp(pattern)
    if (!re.test(phone_field.val())) {
        console.log('phone does not match pattern')
        showError('Error message', 'Forbidden character in phone field')
        return false;
    }

    pattern = name_field.attr("pattern")
    re = new RegExp(pattern)
    if (!re.test(city_field.val())) {
        console.log('city does not match pattern')
        showError('Error message', 'Forbidden character in city field')
        return false;
    }

    if (!re.test(country_field.val())) {
        console.log('country does not match pattern')
        showError('Error message', 'Forbidden character in country field')
        return false;
    }

    
    return true;
}

function logMeOut() {

    $.ajax({
        type:'POST',
        url: '/auth/logout',
        contentType : 'application/json',
        success : function() {
            console.log('I have been logged out')
            location.reload()
            localStorage.removeItem('myToken')
        },
        error : function() {
            console.log('FAIL LOGOUT')
            //alert('Bad credentials')
        }
        
    })
}

