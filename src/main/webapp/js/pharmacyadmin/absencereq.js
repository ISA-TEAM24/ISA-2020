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
                url: '/timeoff/getrequestsbypharmacy',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(requests) {
                    addRequestsToTable(requests);
                },
                error : function() {
                    console.log("error");
                }
            });
        } 
    });
}

function addRequestsToTable(requests) {

    var table = "";
    requests.forEach(function(req) {
        table += `<tr>
                    <td>${req.ime}</td>
                    <td>${req.prezime}</td>
                    <td>${req.uloga}</td>
                    <td>${req.odDatuma}</td>
                    <td>${req.doDatuma}</td>
                    <td>${req.vrsta}</td>
                    <td>${req.razlog}</td>
                    <td><button type="button" id="Accept-${req.id}" class="btn btn-success">Accept</button></td>
                    <td><button type="button" id="Reject-${req.id}" class="btn btn-danger">Reject</button></td>
                  </tr>`;
    });

    $("#myTable").append(table);

    requests.forEach(function(req) {
        $(document).on('click', '#Accept-'+ req.id, function() {

            $.ajax({
                type:'PUT',
                url: '/timeoff/accept/' + req.id,
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(requests) {
                    alert("TimeOff request accepted");
                    location.reload();
                },
                error : function() {
                    console.log("error");
                }
            })
        });
    });

    requests.forEach(function(req) {
        $(document).on('click', '#Reject-'+ req.id, function() {
            $("#insertreject").empty();
            var textarea = "";
            textarea += `<div class="form-group">
                            <textarea class="form-control" rows="5" id="field-${req.id}" placeholder="Enter reason"></textarea>
                         </div>
                         <button type="button" id="SendAnsw-${req.id}" class="btn btn-primary">Send answer</button>`;

            $("#insertreject").append(textarea);
        });
    });

    requests.forEach(function(req) {
        $(document).on('click', '#SendAnsw-'+ req.id, function() {
            
            var obj = {
                id : req.id,
                razlogOdbijanja : $("#field-" + req.id).val()
            }


            $.ajax({
                type:'PUT',
                url: '/timeoff/reject',
                contentType : 'application/json',
                data: JSON.stringify(obj),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(requests) {
                    alert("TimeOff request rejected");
                    location.reload();
                },
                error : function() {
                    console.log("error");
                }
            })
        });
    });
}
