// the table contains the following headers : name , last name, grade
var farm_body =  $('#farm_body') // pharmacists table body

$(document).ready(function() {

    getMyPharmacists();
});



function getMyPharmacists() {

    const queryString = window.location.search;
    console.log(queryString);

    if (queryString == "") {
        document.location.href="pharmacies.html"
    }

    const urlParams = new URLSearchParams(queryString);
    var id = urlParams.get('id')

    $.ajax({
        type: 'GET',
        url: '/apoteka/findpharmacists/' + id,
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(pharmacists) {
            reloadPharmacists(pharmacists);
        }, error : function() {
            console.log('error occured');
        }
    });
}

function reloadPharmacists(pharmacists) {

    table = "";
    pharmacists.forEach(function(ph) {
        table += `<tr>
                    <td>${ph.ime}</td>
                    <td>${ph.prezime}</td>
                    <td>${ph.ocena}</td>
                  </tr>`;
    });

    farm_body.append(table);
}

