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
                    refreshToken();
                }
            })
            refreshToken();
        },error : function() {
            //alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
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
                    refreshToken();
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
