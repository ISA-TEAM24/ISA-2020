$(document).ready(function() {
    obj = {};
    getData();
    drugList = [];
    retVal = [];
    addIdToPenalButton();
    disablePastDates();
    refreshToken();
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
            refreshToken();
        },
        error : function() {
            console.log('Error');
            //alert("Istekao vam je token. Ulogujte se ponovo.")
            window.location.href = '../index.html';
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
    $('#apoteka2').val(data.apoteka);

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
                refreshToken();
            },
            error : function() {
                console.log('error occured')
                //alert("Istekao vam je token. Ulogujte se ponovo.")
                window.location.href = '../index.html';
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
 
                alert('Uspešno ste zakazali dodatno savetovanje!');
                
                $('#dodatniPregledModal').modal('toggle');

            }

        }
    })
}


// DERM START
function createAdditionalExam() {

    console.log('ADDITIONAL EXAM')

    console.log('ID POSETE JE: ' + obj.id);

    
    var datum = $('#datumId1').val().trim();
    var vreme = $('#vremeId1').val().trim();

    if(datum == "" || vreme == "") {
        alert('Morate izabrati datum i vreme pregleda!');
        return;
    }

    var mydto = {
        "ime" : obj.ime,
        "prezime" : obj.prezime,
        "email" : obj.email,
        "datum" : datum,
        "trajanje" : 30,
        "vreme" : vreme,
        "apoteka" : $("#apotekaId1").val().trim()
    }
    console.log('ITTVOK----------------------')
    console.log(mydto);

    if($("#predefTerminsId").children(":selected").attr("id") == "option-none") {
        createNewExam(mydto);
    } else {
        rewritePredefExam(mydto);
    }
    
}
function rewritePredefExam(dto) {

    var selectedId = $("#predefTerminsId").children(":selected").attr("id");
    var mojId = selectedId.split("-")[1];

    var objekat= {
        'email' : obj.email,
        'idpregled' : mojId
    }

    $.ajax({
        type:'POST',
        url:  origin + '/api/exam/rewritepredef',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(objekat),
        success : function() {

            alert('Uspešno ste zakazali pregled!');
            $('#dodatniPregledModal').modal('toggle');
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
 
                alert('Uspešno ste zakazali dodatno savetovanje!');
                
                $('#dodatniPregledModal').modal('toggle');

            }

        }
    })

}

function createNewExam(dto) {
    $.ajax({
        type:'POST',
        url:  origin + '/api/exam/schedule',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(dto),
        success : function() {

            alert('Uspešno ste zakazali pregled!');
            $('#dodatniPregledModal').modal('toggle');
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
 
                alert('Uspešno ste zakazali dodatno savetovanje!');
                
                $('#dodatniPregledModal').modal('toggle');

            }

        }
    })
}

var retVal = [];
function getPredefinedExamsForDerm() {
    
    var idDto = {
        "id" : obj.id
    }

    $.ajax({
        type:'POST',
        url:  origin + '/api/visit/predefinedtermins',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(idDto),
        success : function(data) {
            retVal = data;
            console.log('SUCCES');
            //console.log(retVal);
            console.log(data);
            fillDermAppointmentFields();
            refreshToken();
        },
        error : function(xhr, status, error) {
            console.log('ERROR')
            //alert("Istekao vam je token. Ulogujte se ponovo.")
            window.location.href = '../index.html';
        }
    })
}

function fillDermAppointmentFields() {
    $('#pacijentId1').val(obj.ime + " " + obj.prezime);
    $('#mailId1').val(obj.email);
    $('#apotekaId1').val(obj.apoteka);

    $('#predefTerminsId').html('');
    $("#predefTerminsId").html(`<option id="option-none"> </option>`);
    
    retVal.forEach(function(item) {
        console.log(item.naziv);

        var date = item.date.split(" ")[0];
        var time = item.time;
        $('#predefTerminsId').append('<option value="' + date + time + '" id="option-' + item.idexam + '">' + date + "//" + time + '</option>');
    })


}

$("#predefTerminsId").change(function() {

    $('#datumId1').val('');
    $('#vremeId1').val('');

    var selectedId = $("#predefTerminsId").children(":selected").attr("id");
    var mojId = selectedId.split("-")[1];

    console.log(mojId);

    var kompletDatum = $("#" + selectedId).html();
    console.log(kompletDatum);
    var datum = kompletDatum.split("//")[0];
    var vreme = kompletDatum.split("//")[1];

    
    console.log('CHANGEPROPERTY TEST: ')
    console.log(datum);
    console.log(vreme);

    $('#datumId1').val(datum);
    $('#vremeId1').val(vreme);

    if($("#predefTerminsId").children(":selected").attr("id") == "option-none") {
        
        $('#datumId1').val('');
        $('#vremeId1').val('');
    }

})

// ako menjam datepicker ili timepicker --> predef isprazni
$("#datumId1").change(function() {
    $("#predefTerminsId").prop('selectedIndex', 0);
})

$("#vremeId1").change(function() {
    $("#predefTerminsId").prop('selectedIndex', 0);
})



// DERM END

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
            refreshToken();
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
                alert('Nema tog leka u magacinu, administrator obavešten! Pogledajte alternativne lekove.');
                loadAlternatives(dto);
                refreshToken();
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
            refreshToken();
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

    if($("#alternativeDrugs").children(":selected").attr("id") == "option-none") {
        $("#saveReceptBtn").attr("disabled", true);
    }


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
            refreshToken();
 
        }, error : function() {
            console.log('error occured');
        }
    });

}

function addIdToPenalButton(){


    var val = $("#penal").attr("onclick", "addPenalToPatient()");
    console.log(val);
}

function addPenalToPatient(visitId) {

    var url = window.location.href;
    var visitId = url.split("?")[1];

    console.log(visitId);
    console.log(obj.email);

    var object = {
        "email" : obj.email,
        "id" : parseInt(visitId)
    }

    $.ajax({
        type:'POST',
        url: '/api/visit/givePenalty',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(object),
        success : function() {
            console.log('Success');
            alert('Pacijentu je dodeljen 1 penal!');
            history.go(-1);
        },
        error : function() {
            console.log('Error');
        }
        
    }) 

}

function disablePastDates() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1;
    var yyyy = today.getFullYear();
    if(dd<10){
      dd='0'+dd
    } 
    if(mm<10){
      mm='0'+mm
    } 
    
    today = yyyy+'-'+mm+'-'+dd;
    document.getElementById("datumId1").setAttribute("min", today);

}