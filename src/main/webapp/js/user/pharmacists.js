$(document).ready(function() {

    reloadPharmacists();
});

function reloadPharmacists() {

    $.ajax({
        type:'GET',
        url: '/pharmacist/getallpharmacists',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(pharmacists) {
            fillPharmacists(pharmacists)
        },
        error : function() {
            console.log('Could not load dermatologists')
        }
        
    })
}

function fillPharmacists(pharmacists) {
    var table = "";
    pharmacists.forEach(function(ph) {

        table +=    `<tr>
                        <td>${ph.ime}</td>
                        <td>${ph.prezime}</td>
                        <td>${ph.ocena}</td>
                        <td>${ph.apoteka}</td>
                     </tr>`
    });

    $("#pharmacistsTable").append(table);
}

function searchPharmacistsByName() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchphname");
    filter = input.value.toUpperCase();
    table = document.getElementById("pharmacistsTable");
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

function searchPharmacistsByLastName() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchphlastname");
    filter = input.value.toUpperCase();
    table = document.getElementById("pharmacistsTable");
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