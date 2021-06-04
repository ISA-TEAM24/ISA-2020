$(document).ready(function() {

    reloadDermatologists();
});

function reloadDermatologists() {

    $.ajax({
        type:'GET',
        url: '/dermatologist/getalldermatologists',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(dermatologists) {
            fillDermatologists(dermatologists)
        },
        error : function() {
            console.log('Could not load dermatologists')
        }
        
    })
}

function fillDermatologists(dermatologists) {
    var table = "";
    dermatologists.forEach(function(derm) {

        table +=    `<tr>
                        <td>${derm.ime}</td>
                        <td>${derm.prezime}</td>
                        <td>${derm.ocena}</td>
                        <td>
                            <ul>`
        derm.radiUApotekama.forEach(function(apoteka) {
            table +=            `<li>${apoteka.naziv}</li>`
        });
        table +=           `</ul></td></tr>`
    });

    $("#dermatologistsTable").append(table);
}

function searchDermatologistsByName() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchdermname");
    filter = input.value.toUpperCase();
    table = document.getElementById("dermatologistsTable");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
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

function searchDermatologistsByLastName() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchdermlastname");
    filter = input.value.toUpperCase();
    table = document.getElementById("dermatologistsTable");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
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