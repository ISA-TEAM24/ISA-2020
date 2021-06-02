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
    appointments.forEach(function(app) {
        table += `<tr>
                    <td>${app.zaposleni.ime} ${app.zaposleni.prezime}</td>
                    <td>${app.datum}</td>
                    <td>${app.vreme}</td>
                    <td><button type="button" id="reserve-${app.ID}" class="btn btn-info-reserve">Reserve</button></td>
                  </tr>`;
    });

    app_body.append(table);
}