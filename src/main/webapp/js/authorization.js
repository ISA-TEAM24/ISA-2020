$(document).ready(function() {
    var phadmin_check = ['pharmacyadmin/absencerequestpage.html', 'pharmacyadmin/allmeds.html', 'pharmacyadmin/appointments.html', 'pharmacyadmin/dermatologistslist.html',
        'pharmacyadmin/dermatologworktime.html', 'pharmacyadmin/examinationpricelist.html', 'pharmacyadmin/hiredermatologist.html', 'pharmacyadmin/index.html',
        'pharmacyadmin/inquiries.html', 'pharmacyadmin/meds.html', 'pharmacyadmin/medspricelist.html', 'pharmacyadmin/myprofile.html', 'pharmacyadmin/newactionpromotion.html',
        'pharmacyadmin/orderslist.html', 'pharmacyadmin/pharmacistslist.html', 'pharmacyadmin/pharmacyedit.html', 'pharmacyadmin/purchaseorder.html', 'pharmacyadmin/registerpharmacist.html'];
 
    var url = window.location.pathname.substring(1);
    var role = localStorage.getItem('role');

    if (phadmin_check.includes(url)) {
        if (role != 'phadmin') {
            window.location.href = '../index.html'; 
        }
    }
})
