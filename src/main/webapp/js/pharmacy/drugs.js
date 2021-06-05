// the table contains the following headers : name , date-picker for picking up date, button for reserving
var drugs_body =  $('#drugs_body') // drugs table body

$(document).ready(function() {

    getMyDrugs();
});

function getMyDrugs() {

    const queryString = window.location.search;
    console.log(queryString);

    if (queryString == "") {
        document.location.href="pharmacies.html"
    }

    const urlParams = new URLSearchParams(queryString);
    var id = urlParams.get('id')

    $.ajax({
        type: 'GET',
        url: '/apoteka/findmedicines/' + id,
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(drugs) {
            reloadDrugs(drugs);
        }, error : function() {
            console.log('error occured');
        }
    });
}

function reloadDrugs(drugs) {
    table = "";
    drugs.forEach(function(drug) {
        table += `<tr>
                    <td>${drug.naziv}</td>
                    <td><button type="button" id="reserve-${drug.naziv}" onclick="reserveReroute(this.id)" class="btn btn-info-reserve">Reserve</button></td>
                  </tr>`;
    });

    drugs_body.append(table);
}
// in modal.js
function reserveReroute(id) {
    console.log('Clicked on reserve button')
    var med = id.split("-")[1]
    localStorage.setItem('looking_for_med', med)
    redirectToUserPage('reservenew')
}