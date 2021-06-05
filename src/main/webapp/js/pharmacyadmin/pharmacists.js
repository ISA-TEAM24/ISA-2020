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
                type:'GET',
                url:'/phadmin/getmypharmacists',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(pharmacists) {
                    addPharmacistsToTable(pharmacists);
                }

            })
        }, error : function() {
            alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
        }      
    })
}

function addPharmacistsToTable(pharmacists) {
    var table = "";
    pharmacists.forEach(function(ph) {
        table +=    '<tr>' +
                    '<td>' + ph.ime + '</td>' +
                    '<td>' + ph.prezime + '</td>' +
                    '<td>' + ph.ocena + '</td>' +
                    '<td><button type="button" id="Fire-' + ph.username + '" class="btn btn-info-fire">&nbsp&nbspFire&nbsp&nbsp</button></td>' +
                    '</tr>';
    });

    $("#myTable").append(table);

    pharmacists.forEach(function(ph) {
        $(document).on('click', '#Fire-'+ ph.username, function() {
            $.ajax({
                type:'PUT',
                url:'/phadmin/firepharmacist/' + ph.username,
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function() {
                    alert("Succesfully removed pharmacist from pharmacy");
                    window.location.reload()
                }, error : function() {
                    alert("Pharmacist have schedulled appointments. Cant be deleted");
                }
            })
        });
    });
}

$("#Hire").click(function() {
    document.location.href = 'registerpharmacist.html';
});

function searchByName() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("search1");
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

function searchByLastName() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("search2");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
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
