const pharmacy_name = $('#phName')
const pharmacy_address = $('#phAddress')
const pharmacy_desc = $('#phDesc')
const pharmacy_grade = $('#phGrade')
const subscribed_to_actions = $('#profile_slider')

subscribed_to_actions[0].checked = true // this is how to check the checkbox

$(document).ready(function() {

    reloadPharmacyProfile()
})

function reloadPharmacyProfile() {
    console.log('profile')
}

// NOTE : This page should only be allowed to get to when the url contains the ID of the pharmacy
// for example : /pharmacy/pharmacyprofile.html?id=1  is VALID , but if it does not contain the id then send him back to the pharmacies.html page
// ADD PROTECTION TO ALL THE BUTTONS WHERE IF THE USER IS NOT LOGGED IN SEND HIM BACK TO THE LOGIN PAGE OR SHOW HIM A DIALOGUE THAT HE HAS TO LOG IN