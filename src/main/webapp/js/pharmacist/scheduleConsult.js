$(document).ready(function() {

    resetFields();

})


function sendSchedulingRequest() {
    var ime = $('#imepac').val().trim();
    var prezime = $('#prezimepac').val().trim();
    var email = $('#mail').val().trim();
    var datum = $('#datumpregleda').val().trim();
    var vreme = $('#vremepregleda').val().trim();

    if(ime == "" || prezime == "" || email == "" || datum =="" || vreme == "" ) {
        alert('Morate popuniti sva polja');
        return;
    }

    var obj = {
        "ime" : ime,
        "prezime" : prezime,
        "email" : email,
        "datum" : datum,
        "vreme" : vreme
    }

    console.log(obj);
    var origin   = window.location.origin;
    console.log(origin);

    $.ajax({
        type:'POST',
        url:  origin + '/api/consult/schedule',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function() {

            alert('Uspešno ste zakazali savetovanje!');
            resetFields();

        },
        error : function(xhr, status, error) {

            if(xhr.responseText == "NOT_FOUND") {
                alert('Ne postoji pacijent sa zadatim mailom!')
            }
            if(xhr.responseText == "BAD_REQUEST") {
                alert('Termin nije dostupan!')
            }

            if(xhr.responseText == "CREATED") {
 
                alert('Uspešno ste zakazali savetovanje!');
                resetFields();
            }
            //alert(xhr.responseText);

        }
    
    })
}

function resetFields() {
    $('#imepac').val('')
    $('#prezimepac').val('')
    $('#mail').val('')
    $('#datumpregleda').val('')
    $('#vremepregleda').val('')
}
