$(document).ready(function() {
    visits = [];
    getUpcomingVisits();

    var origin   = window.location.origin;
    console.log(origin);
})

var visits = [];

function getUpcomingVisits() {
    $.ajax({
        type:'GET',
        url: '/api/visit/getupcomings',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            console.log(data)
            fillVisits(data);

        },
        error : function() {
            console.log('Cant get upcoming visits');
        }    
    })
}

function fillVisits(data) {
    for(var i = 0; i<data.length; i++) {
        var obj = {'id' : data[i].id,
                    'ime' : data[i].ime,
                    'prezime' : data[i].prezime,
                    'datum' : data[i].datum,
                    'vreme' : data[i].vreme,
                    'email' : data[i].email,
                    'telefon' : data[i].telefon,
                    'alergije' : data[i].alergije,
                    'dijagnoza' : data[i].dijagnoza,
                    'apoteka' : data[i].apoteka
                    };

        visits.push(obj);
    }
    console.log(visits);
    buildTable(visits);
}

function buildTable(data){

    var table = document.getElementById('tableBody')
    table.innerHTML = ''
    for(var i = 0; i < data.length; i++ ) {

        var pocniId = data[i].id;
        var otkaziId = data[i].email + '-' + data[i].id;

        var row = `<tr>
                        <td>${data[i].datum}</td>
                        <td>${data[i].vreme}</td>
                        <td>${data[i].ime}</td>
                        <td>${data[i].prezime}</td>
                        <td>${data[i].apoteka}</td>
                        <td><button class="btn btn-dark" onclick='redirectToExam(this.id)' id="` + pocniId + `"> Započni pregled </button></td>
                        <td><button class="btn btn-warning" onclick='addPenalToPatient(this.id)' id="` + otkaziId + `"> Nije se pojavio/la </button></td>
                    </tr>`

        table.innerHTML += row
    }
}

function redirectToExam(id) {
    var origin   = window.location.origin;
    console.log(origin + "/dermatologist/aktivanPregled.html");
    var url = origin + "/dermatologist/aktivanPregled.html?" + id;

    console.log(id);
    console.log(url);
    window.location.href = url;

}

function addPenalToPatient(data) {

    var email = data.split('-')[0];
    var visitId = data.split('-')[1];
    console.log(email);
    console.log(visitId);

    var obj = {
        "email" : email,
        "id" : visitId
    }

    $.ajax({
        type:'POST',
        url: '/api/visit/givePenalty',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function() {
            console.log('Success');
            alert('Pacijentu je dodeljen 1 penal!');

            location.reload();
        },
        error : function() {
            console.log('Error');
        }
        
    }) 

}


function searchIme() {

    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("imeId");
    filter = input.value.toUpperCase();
    table = document.getElementById("mydatatable");
    tr = table.getElementsByTagName("tr");
  
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[2];
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }
    }
  }

  function searchPrezime() {

    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("prezimeId");
    filter = input.value.toUpperCase();
    table = document.getElementById("mydatatable");
    tr = table.getElementsByTagName("tr");
  
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[3];
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }
    }
  }

  function searchApoteka() {

    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("apotekaId");
    filter = input.value.toUpperCase();
    table = document.getElementById("mydatatable");
    tr = table.getElementsByTagName("tr");
  
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[4];
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }
    }
  }