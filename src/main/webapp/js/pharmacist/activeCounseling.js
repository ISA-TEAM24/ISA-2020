$(document).ready(function() {
    getData();
})

function getData() {

    var url = window.location.href;
    console.log(url);

    var id = url.split("?")[1];
    console.log("MOJ ID POSETE JE: " + id);

    
    $.ajax({
        type:'POST',
        url: '/api/visit/findVisit',
        contentType : 'text/plain',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : id,
        success : function(data) {
            console.log('Success');
            fillFields(data);
            
        },
        error : function() {
            console.log('Error');
        }
        
    }) 
}

var obj = {};

function fillFields(data) {

    obj = data;
    console.log(obj.dijagnoza + "--------------------");

    $("#imeLabel").text(data.ime);
    $("#prezimeLabel").text(data.prezime);
    $("#emailLabel").text(data.email);
    $("#telLabel").text(data.telefon);
    $("#alergijeLabel").text(data.alergije.allergies);

    console.log(data.alergije.allergies.length);

    if(data.alergije.allergies.length == 0) {
        $("#alergijeLabel").text("nema");
    }

    console.log(data.alergije);
}

function saveReport() {
    obj.dijagnoza = $('#dijagnozaId').val().trim();
    console.log(obj.dijagnoza);

    if(obj.dijagnoza != "") {

        $.ajax({
            type:'PUT',
            url: '/api/visit/savereport',
            contentType : 'application/json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
            },
            data : JSON.stringify(obj),
            success : function() {

                alert("Uspešno ste sačuvali izveštaj!");
                var origin   = window.location.origin;
                var url = origin + "/pharmacist/savetovanje.html"
            
                window.location.href = url;
    
            },
            error : function() {
                console.log('error occured')
            }
           
        })   

    } else {

        alert("Morate popuniti dijagnozu!");
    }

}

function fillAppointmentFields() {

    $('#pacijentId1').val(obj.ime + " " + obj.prezime);
    $('#mailId1').val(obj.email);
    $('#apotekaId1').val(obj.apoteka);

    $('#pac2').val(obj.ime + " " + obj.prezime);
    $('#mail2').val(obj.email);

}

function createAddtitionalVisit() {

    var datum = $('#datumId1').val().trim();
    var vreme = $('#vremeId1').val().trim();

    if(datum == "" || vreme == "") {
        alert('Morate izabrati datum i vreme pregleda!');
        return;
    }

    var dto = {
        "ime" : obj.ime,
        "prezime" : obj.prezime,
        "email" : obj.email,
        "datum" : datum,
        "trajanje" : 30,
        "vreme" : vreme
    }

    $.ajax({
        type:'POST',
        url:  origin + '/api/consult/schedule',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(dto),
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
 
                alert('Uspešno ste zakazali dodatno savetovanje!');
                
                $('#dodatniPregledModal').modal('toggle');

            }

        }
    })
}

function writePrescription() {
    




}
