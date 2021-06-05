$(document).ready(function() {

    resetFields();
    disablePastDates();
    refreshToken();
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

    if(validateInputs() == false) {
        return;
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
            refreshToken();
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

function disablePastDates() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0 so need to add 1 to make it 1!
    var yyyy = today.getFullYear();
    if(dd<10){
      dd='0'+dd
    } 
    if(mm<10){
      mm='0'+mm
    } 
    
    today = yyyy+'-'+mm+'-'+dd;
    document.getElementById("datumpregleda").setAttribute("min", today);
}

function validateInputs() {
    var pattern = $("#imepac").attr("pattern");
    var re = new RegExp(pattern);

    if(!re.test($("#imepac").val())) {
        alert('Zabranjen karakter u ime polje');
        return false;
    }

    if(!re.test($("#prezimepac").val())) {
        alert('Zabranjen karakter u prezime polje');
        return false;
    }

    return true;
}
