var pharmacy_name = $('#phName')
var pharmacy_address = $('#phAddress')
var pharmacy_desc = $('#phDesc')
var pharmacy_grade = $('#phGrade')
var subscribed_to_actions = $('#profile_slider_pharmacy')

//subscribed_to_actions[0].checked = true // this is how to check the checkbox

$(document).ready(function() {

    reloadPharmacyProfile()
})

function reloadPharmacyProfile() {
    const queryString = window.location.search;
    console.log(queryString);

    if (queryString == "") {
        document.location.href="pharmacies.html"
    }

    const urlParams = new URLSearchParams(queryString);
    var id = urlParams.get('id')

    $.ajax({
        type: 'GET',
        url: '/apoteka/' + id,
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(pharmacy) {
            addProfileData(pharmacy);
        }, error : function() {
            console.log('error occured')
        }
    })
}

function addProfileData(pharmacy) {
    pharmacy_name.val(pharmacy.naziv);
    pharmacy_address.val(pharmacy.adresa);
    pharmacy_desc.val(pharmacy.opis);
    pharmacy_grade.val(pharmacy.ocena);

    $.ajax({
        type:'GET',
        url: '/api/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(user) {
            console.log(user)
            checkIfUserIsSubscribed(user, pharmacy.naziv)
        },
        error : function() {
            subscribed_to_actions[0].checked = false
            
            //console.log('An Error has occured while trying to reload the profile')
            //showModal('Error message', 'Could not load profile information.')
        }
        
    })


}

function checkIfUserIsSubscribed(user, pharmacy_name) {

    var map = user.loyaltyInfo.pratiPromocije
    console.log(map)
    if (map[pharmacy_name] == undefined) {
        console.log('undefined')
        showModal('Not logged in', 'You must be logged in to use this functionality.')
        return
    }

    if(map[pharmacy_name] == true) {
        subscribed_to_actions[0].checked = true
        console.log('follows')
        //changeSubStatusPharmacy()
    }
    else {
        subscribed_to_actions[0].checked = false
        console.log('does not follow')
        //changeSubStatusPharmacy()
    }

}

$('#profile_slider_pharmacy').change(function(){
    changeSubStatusPharmacy()
})


function changeSubStatusPharmacy() {


    if($('#profile_slider_pharmacy').is(":checked")) {
        console.log('WE CHECKED')
        follows = true

    }
    else {
        console.log('WE UNCHECKED')
        follows = false
    }

    var obj = {
        naziv : pharmacy_name.val(),
        prati : follows
    }

    $.ajax({
        type:'PUT',
        url: '/api/user/subs/update',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function() {
            console.log('Sub success')
        },
        error : function(xhr) {
            console.log('Could not save subs')
            if (xhr.status == 401) {
                showModal('Not logged in', 'You must be logged in to use this functionality.')
            }
        }
        
    })


}

// NOTE : This page should only be allowed to get to when the url contains the ID of the pharmacy
// for example : /pharmacy/pharmacyprofile.html?id=1  is VALID , but if it does not contain the id then send him back to the pharmacies.html page
// ADD PROTECTION TO ALL THE BUTTONS WHERE IF THE USER IS NOT LOGGED IN SEND HIM BACK TO THE LOGIN PAGE OR SHOW HIM A DIALOGUE THAT HE HAS TO LOG IN