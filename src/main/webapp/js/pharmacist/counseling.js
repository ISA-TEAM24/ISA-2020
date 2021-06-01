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

    buildTable(visits);
}

function buildTable(data){

    var table = document.getElementById('tableBody')
    table.innerHTML = ''
    for(var i = 0; i < data.length; i++ ) {

        var pocniId = data[i].id;
        var otkaziId = data[i].id;

        var row = `<tr>
                        <td>${data[i].datum}</td>
                        <td>${data[i].vreme}</td>
                        <td>${data[i].ime}</td>
                        <td>${data[i].prezime}</td>
                        <td><button class="btn btn-dark" onclick='redirectToCounseling(this.id)' id="` + pocniId + `"> Započni savetovanje </button></td>
                        <td><button class="btn btn-warning" onclick='addPenalToPatient(this.id)' id="` + otkaziId + `"> Nije se pojavio/la </button></td>
                    </tr>`

        table.innerHTML += row
    }
}

function redirectToCounseling(id) {
    var origin   = window.location.origin;
    console.log(origin + "/pharmacist/aktivnoSavetovanje.html");
    var url = origin + "/pharmacist/aktivnoSavetovanje.html?" + id;

    console.log(id);
    console.log(url);
    window.location.href = url;

}

function addPenalToPatient(id) {



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