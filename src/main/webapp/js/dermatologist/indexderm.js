$(document).ready(function() {
    getMe();
})

function getMe() {
    $.ajax({
        type:'GET',
        url: '/dermatologist/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(dermatologist) {
            if(dermatologist.prvoLogovanje == true) {
                console.log('Prvi put je logovan.')
                window.location.href = '/dermatologist/mojProfilDermatolog.html'
            }

            console.log('Promenjena je lozinka');
        },
        error : function() {
            console.log('Error')
        }    
    })
}