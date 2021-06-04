const phadmin_check = ['pharmacyadmin/absencerequestpage.html', 'pharmacyadmin/allmeds.html', 'pharmacyadmin/appointments.html', 'pharmacyadmin/dermatologistslist.html',
                        'pharmacyadmin/dermatologworktime.html', 'pharmacyadmin/examinationpricelist.html', 'pharmacyadmin/hiredermatologist.html', 'pharmacyadmin/index.html',
                        'pharmacyadmin/inquiries.html', 'pharmacyadmin/meds.html', 'pharmacyadmin/medspricelist.html', 'pharmacyadmin/myprofile.html', 'pharmacyadmin/newactionpromotion.html',
                        'pharmacyadmin/orderslist.html', 'pharmacyadmin/pharmacistslist.html', 'pharmacyadmin/pharmacyedit.html', 'pharmacyadmin/purchaseorder.html', 'pharmacyadmin/registerpharmacist.html'];

const patient_check = ['user/me.html']

$(document).ready(function() {
    
    checkAuthority()
})

function checkAuthority() {

    var url = window.location.pathname.substring(1);

    if(patient_check.includes(url)) {
        console.log('patient includes')
        runPatientCheck()
    }

}
// patient check start
function runPatientCheck() {

    $.ajax({
        type:'GET',
        url: '/api/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(user) {
            checkPatientAuthority(user)
        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
            showError('Error message', 'Could not load profile information.')
            window.location.href = '../index.html'
        }
        
    })
}

function checkPatientAuthority(user) {

    console.log(user)
    // presume that we shouldn't be allowed
    var shouldReturn = true
    user.authorities.forEach(function(a) {
        if(a.authority == 'ROLE_USER') {
            shouldReturn = false
        }
    })

    if (shouldReturn) {
        window.location.href = '../index.html'
    }
    console.log('You are a patient, you are allowed on this page')
} 

// patient check end