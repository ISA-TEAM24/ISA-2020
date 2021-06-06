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
                url:'/phadmin/getmydermatologists',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(dermatologists) {
                    addDermatologistsToTable(dermatologists);
                }
            })
        }, error : function() {
            //alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
        }  
    })
}

function addDermatologistsToTable(dermatologists) {
    var table = "";
    dermatologists.forEach(function(derm) {
        table +=    '<tr>' +
                    '<td>' + derm.ime + '</td>' +
                    '<td>' + derm.prezime + '</td>' +
                    '<td>' + derm.ocena + '</td>' +
                    '<td><button type="button" id="AddApp-' + derm.username + '" class="btn btn-primary-appointment">Add appointments</button></td>' +
                    '<td><button type="button" id="Fire-' + derm.username + '" class="btn btn-info-fire">&nbsp&nbspFire&nbsp&nbsp</button></td>' +
                    '</tr>';
    });

    $("#myTable").append(table);

    dermatologists.forEach(function(derm) {
        $(document).on('click', '#AddApp-'+ derm.username, function() {
            document.location.href = 'appointments.html?' + derm.username ;
        });
    })

    dermatologists.forEach(function(derm) {
        $(document).on('click', '#Fire-'+ derm.username, function() {
            $.ajax({
                type:'PUT',
                url:'/phadmin/firedermatologist/' + derm.username,
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function() {
                    alert("Succesfully removed dermatologist from pharmacy.");
                    window.location.reload()
                }, error : function() {
                    alert("Dermatologist have schedulled appointments. Cant be fired.");
                }
            })
        });
    });
}


function hire() {
    document.location.href = 'hiredermatologist.html';
}

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
