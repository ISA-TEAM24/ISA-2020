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
                url: '/phadmin/getunsuccessfulqueries',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(queries) {
                    addQueriesToTable(queries);
                }
            });
        }  
    });
}

function addQueriesToTable(queries) {
    var table = ""
    queries.forEach(function(query) {
        table += `<tr>
                    <td>${query.posiljalac.ime}</td>
                    <td>${query.posiljalac.prezime}</td>
                    <td>${query.lek.naziv}</td>
                    <td>${query.kolicina}</td>
                    <td><button type="button" id="delete-${query.id}" class="btn btn-info-fire">&nbsp&nbspDelete&nbsp&nbsp</button></td>
                  </tr>`
    })

    $("#myTable").append(table);

    queries.forEach(function(query) {
        $(document).on('click', '#delete-'+ query.id, function() {
            $.ajax({
                type: 'DELETE',
                url: '/phadmin/deletequery/' + query.id,
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function() {
                    alert("Succesfully deleted query.");
                    window.location.reload();
                }, error : function() {
                    alert("Error");
                }
            });
        });
    })
}