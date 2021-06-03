$(document).ready(function() {
    getMe();
})

function getMe() {
    $.ajax({
        type:'GET',
        url: '/phadmin/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(phadmin) {
            if(phadmin.prvoLogovanje == true) {
                console.log('Prvi put je logovan.')
                window.location.href = 'index.html';
            }

            $.ajax({
                type: 'GET',
                url: '/phadmin/getallmeds',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(medicines) {
                    addMedsToTable(medicines);
                }
            })
        } 
    })
}

function addMedsToTable(medicines) {
    var table = "";
    medicines.forEach(function(med) {
        table += `<tr>
                    <td>${med.naziv}</td>
                    <td>${med.sastav}</td>
                    <td>${med.proizvodjac}</td>
                    <td>${med.ocena}</td>`;

        if (!med.uMojojApoteci) {
            table += `<td><button type="button" id="add-${med.naziv}" class="btn btn-info-fire">Add to pharmacy</button></td>`;
        } else {
            table += `<td></td>`
        }
                 
        table += `</tr>`;
    })

    $("#myTable").append(table);

    medicines.forEach(function(med) {
        $(document).on('click', '#add-'+ med.naziv, function() {
            $.ajax ({
                type: 'PUT',
                url: '/phadmin/addmedicine/' + med.naziv,
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function() {
                    alert("Succesfully added medicine to pharmacy.(You have to manually change price and amount)");
                    document.location.href="meds.html";
                }, error : function() {
                    alert("Error.");
                }
            });
        });
    })
}

function search() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("search");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
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

function sortTable(n,ttt) {

    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById(ttt);
    switching = true;
    dir = "asc";
    while (switching) {
        switching = false;
        rows = table.rows;
        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            if (dir == "asc") {
            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                shouldSwitch = true;
                break;
            }
            } else if (dir == "desc") {
            if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                shouldSwitch = true;
                break;
            }
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            switchcount ++;
        } else {
            if (switchcount == 0 && dir == "asc") {
            dir = "desc";
            switching = true;
            }
        }
    }
}