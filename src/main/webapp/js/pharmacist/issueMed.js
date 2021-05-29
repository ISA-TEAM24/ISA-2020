$(document).ready(function() {

})

function getReservationById() {
    var reservationId = $('#idRezervacijeInput').val().toString();

    $.ajax({
        type:'GET',
        url: '/reservation/find/' + reservationId,
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            console.log(data);
            fillTable(data);

        },
        error : function() {
            alert('Broj rezervacije nije ispravan!');
        }    
    })
}

function fillTable(data) {
    var table = document.getElementById('tableBody')
    table.innerHTML = '';

    var row = `<tr>
                <td>${data.pacijent}</td>
                <td>${data.lek}</td>
                <td>${data.brojRez}</td>

                <td><button class="btn btn-warning" onclick='issueMed(this.id)' id="` + data.brojRez + `"> preuzmi </button></td>
                </tr>`

table.innerHTML += row
}

function issueMed(brojRez) {
    console.log(brojRez);

    $.ajax({
        type:'POST',
        url: '/reservation/issue',
        contentType : 'text/plain',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : brojRez,
        success : function() {
            alert('Uspešno izdat lek!');
            refreshPageWithDelay();
            console.log('Success');

        },
        error : function() {
            console.log('Error');
        }
        
    })
}

function refreshPageWithDelay(){
    var millisecondsToWait = 2000;
    setTimeout(function() {
        $('#idRezervacijeInput').val("");
        location.reload();
    }, millisecondsToWait);

}