const phadmin_check = ['pharmacyadmin/absencerequestpage.html', 'pharmacyadmin/allmeds.html', 'pharmacyadmin/appointments.html', 'pharmacyadmin/dermatologistslist.html',
                        'pharmacyadmin/dermatologworktime.html', 'pharmacyadmin/examinationpricelist.html', 'pharmacyadmin/hiredermatologist.html', 'pharmacyadmin/index.html',
                        'pharmacyadmin/inquiries.html', 'pharmacyadmin/meds.html', 'pharmacyadmin/medspricelist.html', 'pharmacyadmin/myprofile.html', 'pharmacyadmin/newactionpromotion.html',
                        'pharmacyadmin/orderslist.html', 'pharmacyadmin/pharmacistslist.html', 'pharmacyadmin/pharmacyedit.html', 'pharmacyadmin/purchaseorder.html', 'pharmacyadmin/registerpharmacist.html'];

const patient_check = ['user/me.html']

const dermatologist_check = ['dermatologist/aktivanPregled.html', 'dermatologist/indexDermatolog.html',
                    'dermatologist/mojProfilDermatolog.html', 'dermatologist/pregled.html',
                    'dermatologist/pregledaniKlijenti.html', 'dermatologist/radniKalendar.html',
                    'dermatologist/timeOffZahtev.html', 'dermatologist/zakaziPregled.html']

const pharmacist_check = ['pharmacist/aktivnoSavetovanje.html', 'pharmacist/indexFarmaceut.html',
                    'pharmacist/izdavanjeLeka.html', 'pharmacist/mojProfilFarmaceut.html',
                    'pharmacist/pregledaniKlijentiFarmaceut.html', 'pharmacist/radniKalendar.html',
                    'pharmacist/savetovanje.html', 'pharmacist/timeOffZahtev.html', 'pharmacist/zakaziSavetovanje.html']

$(document).ready(function() {
    
    checkAuthority()
})

function checkAuthority() {

    var url = window.location.pathname.substring(1);
    console.log(url)

    if(patient_check.includes(url)) {
        console.log('patient includes')
        runPatientCheck()
    }

    if(dermatologist_check.includes(url)) {
        console.log('dermatologist includes')
        runDermatologistCheck()
    }

    if(pharmacist_check.includes(url)) {
        console.log('pharmacist includes')
        runPharmacistCheck()
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

// dermatologist check start

function runDermatologistCheck() {

    $.ajax({
        type:'GET',
        url: '/api/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(user) {
            checkDermatologistAuthority(user)
        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
            //alert('Error message', 'Could not load profile information.')
            window.location.href = '../index.html'
        }
        
    })
}

function checkDermatologistAuthority(user) {

    console.log(user)
    // presume that we shouldn't be allowed
    var shouldReturn = true
    user.authorities.forEach(function(a) {
        if(a.authority == 'ROLE_DERMATOLOGIST') {
            shouldReturn = false
        }
    })

    if (shouldReturn) {
        window.location.href = '../index.html'
    }
    console.log('You are a dermatologist, you are allowed on this page')
} 

// dermatologist check end

// pharmacist check start

function runPharmacistCheck() {

    $.ajax({
        type:'GET',
        url: '/api/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(user) {
            checkPharmacistAuthority(user)
        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
            //alert('Error message', 'Could not load profile information.')
            window.location.href = '../index.html'
        }
        
    })
}

function checkPharmacistAuthority(user) {

    console.log(user)
    // presume that we shouldn't be allowed
    var shouldReturn = true
    user.authorities.forEach(function(a) {
        if(a.authority == 'ROLE_PHARMACIST') {
            shouldReturn = false
        }
    })

    if (shouldReturn) {
        window.location.href = '../index.html'
    }
    console.log('You are a pharmacist, you are allowed on this page')
} 

// pharmacist check end