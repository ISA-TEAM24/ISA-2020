// the table contains the following headers : name , last name, grade
var derm_body =  $('#derm_body'); // dermatologist table body

$(document).ready(function() {

    getMyDermatologists();
});

function getMyDermatologists() {

    const queryString = window.location.search;
    console.log(queryString);

    if (queryString == "") {
        document.location.href="pharmacies.html"
    }

    const urlParams = new URLSearchParams(queryString);
    var id = urlParams.get('id')

    $.ajax({
        type: 'GET',
        url: '/apoteka/finddermatologists/' + id,
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(dermatologists) {
            reloadDermatologists(dermatologists);
        }, error : function() {
            console.log('error occured');
        }
    });
}

function reloadDermatologists(dermatologists) {

    table = "";
    dermatologists.forEach(function(derm) {
        table += `<tr>
                    <td>${derm.ime}</td>
                    <td>${derm.prezime}</td>
                    <td>${derm.ocena}</td>
                  </tr>`;
    });

    derm_body.append(table);
}