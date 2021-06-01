var pharmacy_name = $('#phName')
var pharmacy_address = $('#phAddress')
var pharmacy_desc = $('#phDesc')
var pharmacy_grade = $('#phGrade')
var subscribed_to_actions = $('#profile_slider')

subscribed_to_actions[0].checked = true // this is how to check the checkbox

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
}

// NOTE : This page should only be allowed to get to when the url contains the ID of the pharmacy
// for example : /pharmacy/pharmacyprofile.html?id=1  is VALID , but if it does not contain the id then send him back to the pharmacies.html page
// ADD PROTECTION TO ALL THE BUTTONS WHERE IF THE USER IS NOT LOGGED IN SEND HIM BACK TO THE LOGIN PAGE OR SHOW HIM A DIALOGUE THAT HE HAS TO LOG IN