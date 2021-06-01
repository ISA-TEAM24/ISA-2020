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
                url:'/phadmin/getalldermatologists',
                contentType:'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(dermatologists) {
                    addDermatologistsToTable(dermatologists);
                }
            })
        }  
    })
}

function addDermatologistsToTable(dermatologists) {
    var table = "";
    var pharmacies = [];
    let works;

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
        
        table +=           `</ul></td>`
  
        if (!derm.radiUMojoj) {
            table +=    `<td><button type="button" id="Hiredermatolgist-${derm.username}" class="btn btn-primary-custom">&nbsp&nbspHire&nbsp&nbsp</button></td>`;
        } else {
            table +=    `<td></td>`;
        }

        table +=    '</tr>';
    });

    dermatologists.forEach(function(derm) {
        $(document).on('click', '#Hiredermatolgist-'+ derm.username, function() {
            document.location.href = 'dermatologworktime.html?' + derm.username ;
        });

    })

    $("#myTable").append(table);

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
