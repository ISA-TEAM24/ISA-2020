var pharmacyname;

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
            if(phadmin.prvoLogovanje == true) {
                console.log('Prvi put je logovan.')
                window.location.href = 'index.html';
            }
            $.ajax({
                type: 'GET',
                url: '/phadmin/getpharmacy',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(pharmacy) {
                    pharmacyname = pharmacy.naziv;
                    addProfileData(pharmacy);
                }, error : function() {
                    console.log('error occured')
                }
            })
        } 
    })
}

function addProfileData(pharmacy) {
    $('#field1').val(pharmacy.naziv)
    $('#field2').val(pharmacy.adresa)
    $('#field3').val(pharmacy.opis)
}

function save() {

    var mN = true;
    if (pharmacyname == $('#field1').val().trim()) {
        mN = false;
    }

    var obj = {
        starinaziv : pharmacyname,
        naziv :  $('#field1').val().trim(),
        adresa :  $('#field2').val().trim(),
        opis :  $('#field3').val().trim(),
        menjannaziv : mN
    }

    $.ajax({
        type:'PUT',
        url: '/phadmin/editPharmacy',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function(pharmacy) {
            console.log('yes')
        },
        error : function() {
            console.log('error occured')
        }
    })   
}


$("#Edit1").click(function() {
    $('#field1').removeAttr('disabled');
});

$("#Edit2").click(function() {
    $('#field2').removeAttr('disabled');
});

$("#Edit3").click(function() {
    $('#field3').removeAttr('disabled');
});