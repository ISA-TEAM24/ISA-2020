// the table contains the following headers : doctor full name / date / time / price / Reserve button
const app_body =  $('#app_body') // app table body

$(document).ready(function() {

    getFreeAppointments();
});

function getFreeAppointments() {
    const queryString = window.location.search;
    console.log(queryString);

    if (queryString == "") {
        document.location.href="pharmacies.html"
    }

    const urlParams = new URLSearchParams(queryString);
    var id = urlParams.get('id')

    $.ajax({
        type: 'GET',
        url: '/apoteka/findavailableappointments/' + id,
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(appointments) {
            reloadAppointments(appointments);
        }, error : function() {
            console.log('error occured');
        }
    });
}

function reloadAppointments(appointments) {
    
    table = "";
    app_body.html("")
    appointments.forEach(function(app) {
        
        table += `<tr>
                    <td>${app.zaposleni.ime} ${app.zaposleni.prezime}</td>
                    <td>${app.datum}</td>
                    <td>${app.vreme}</td>
                    <td>${app.zaposleni.ocena}</td>
                    <td>${app.cena}</td>
                    <td><button onclick="reserveApp(this.id)" id="reserve-${app.id}" class="btn btn-info-reserve">Reserve</button></td>
                  </tr>`;
    });

    app_body.html(table);
}

function reserveApp(id) {

    var id_pregleda = id.split("-")[1]
    var obj = {
        id : id_pregleda
    }
 
    

    $.ajax({
        type: 'POST',
        url: '/api/user/exams/add',
        data : JSON.stringify(obj),
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function() {
            getFreeAppointments()
        }, 
        error : function() {
            console.log('error occured');
        }
    });

}