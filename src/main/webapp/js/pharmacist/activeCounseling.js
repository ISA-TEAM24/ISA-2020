$(document).ready(function() {
    getData();
    drugList = [];

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

    
    $('#pac2').val(data.ime + " " + data.prezime);
    $('#mail2').val(data.email);

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

var drugList = [];
function preparePrescription() {

    var dto = {
        "apotekaId" : obj.apotekaId,
        "pacijentEmail" : obj.email
    }
    
    $.ajax({
        type: 'POST',
        url: '/api/findRecommendedMedicines',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(dto),
        success : function(drugs) {
            console.log(drugs);
            fillSelectBoxWithDrugs(drugs);
            drugList = drugs;
        }, error : function() {
            console.log('error occured');
        }
    });


}

function fillSelectBoxWithDrugs(drugs) {
    $('#drugsInput').html('');

    $("#alternativeDrugs").html('');
    $("#alternativeDrugs").html(`<option id="option-none"> </option>`);
    
    drugs.forEach(function(item) {
        console.log(item.naziv);
        $('#drugsInput').append('<option value="' + item.naziv + '" id=option-' + item.id  + '>' + item.naziv + '</option>');
    })

}

function printDrugSpecification() {
    console.log($( "#drugsInput" ).val())
    var myDrug = $( "#drugsInput" ).val();

    drugList.forEach(function(drug) {
        if(drug.naziv == myDrug) {


            $('#nazivLeka').val(drug.naziv);
            $('#napomeneLeka').val(drug.napomene);
            $('#oblikLeka').val(drug.oblik);
            $('#sastavLeka').val(drug.sastav);
            $('#proizvLeka').val(drug.proizvodjac);

            console.log(drug.proizvodjac);
        }
    })

}

function checkAvailability() {
    $("#alternativeDrugs").html('');
    $("#alternativeDrugs").html(`<option id="option-none"> </option>`);


    var lek_id = $("#drugsInput").children(":selected").attr("id");
    var idleka = lek_id.split("-")[1];

    console.log(idleka);

    var dto = {
        "medId" : idleka,
        "apotekaId" : obj.apotekaId,
        "patientEmail" : obj.email
    }
    
    $.ajax({
        type: 'POST',
        url: '/api/checkDrugAvailabity',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(dto),
        success : function(data, textStatus, xhr) {
            if(xhr.status == 200) {
                console.log('STATUS JE: '  + xhr.status)
                alert('Nema tog leka u magacinu, administrator obavešten!');
                loadAlternatives(dto);
            } else {
                alert('Lek je dostupan');

                $("#saveReceptBtn").attr("disabled", false);
            }

 
        }, error : function() {
            console.log('error occured');
        }
    });

}

function loadAlternatives(dto) {
    $.ajax({
        type: 'POST',
        url: '/api/getalternativedrugs',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(dto),
        success : function(data) {
            console.log(data);
            fillAlternatives(data);
        }, error : function() {
            console.log('error occured');
        }
    });

}

function fillAlternatives(data) {
    var html = '';
    $("#alternativeDrugs").html('');
    html += `<option id="option-none"> </option>`;

    data.forEach(function(drug) {
        html += `<option id="option-${drug.id}"> ${drug.naziv} </option>`;
        
    })

    $("#alternativeDrugs").html(html);


}

$("#drugsInput").change(function() {
    $("#alternativeDrugs").html('');
    $("#alternativeDrugs").html(`<option id="option-none"> </option>`);

    $("#saveReceptBtn").attr("disabled", true);
})

$("#alternativeDrugs").change(function() {
    $("#saveReceptBtn").attr("disabled", false);
})

function prescribe() {
    var selectedDrugId;

    var selectId = $("#alternativeDrugs").children(":selected").attr("id");
    var idAlternative = selectId.split("-")[1];

    if(selectId == "option-none") {
        console.log('KORISTITI PRAVI LEK, ne alternativu');

        var idReal = $("#drugsInput").children(":selected").attr("id");
        var idDrug = idReal.split("-")[1];

        selectedDrugId = idDrug;

    } else {
        console.log('ALTERNATIVA JE IZABRAN' + idAlternative);

        selectedDrugId = idAlternative;
    }

// marknak van funkcio ahol leszedi a magacinbol lekot

    if($("#trajanje").val() == 0) {
        alert('Definišite trajanje terapije!');
        return;
    }

    if($("#trajanje").val() < 1 && $("#trajanje").val() != 0) {
        alert('Trajanje terapije mora da bude pozitivan broj!');
        return;
    }
    // PrescriptionDTO
    var retObj = {
        'email' : obj.email,
        'trajanje' : parseInt($("#trajanje").val()),
        'lekId' : parseInt(selectedDrugId),
        'apotekaId' : parseInt(obj.apotekaId)
    }

    console.log(retObj);

    $.ajax({
        type: 'POST',
        url: '/api/writeprescription',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(retObj),
        success : function() {
            alert('Uspešno sačuvan prepis!');
            $('#prepisiLekModal').modal('toggle');
 
        }, error : function() {
            console.log('error occured');
        }
    });

}
