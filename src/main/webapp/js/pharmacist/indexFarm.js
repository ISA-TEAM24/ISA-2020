$(document).ready(function() {
    getMe();
})

function getMe() {
    $.ajax({
        type:'GET',
        url: '/pharmacist/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(pharmacist) {
            if(pharmacist.prvoLogovanje == true) {
                console.log('Prvi put je logovan.')
                window.location.href = '/pharmacist/mojProfilFarmaceut.html'
            }

            console.log('Promenjena je lozinka');
        },
        error : function() {
            console.log('Error')
        }    
    })
}