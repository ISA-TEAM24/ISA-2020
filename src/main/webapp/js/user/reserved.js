function reloadReserved() {


    $.ajax({
        type:'GET',
        url: '/reservation/all',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            fillReserved(data)
        },
        error : function() {
            console.log('Could not load reserved medicine')
        }
        
    })
}

function fillReserved(data) {

    var html = "";
    var body = $('#reserved_body')

    data.forEach(function(dto) {
        var r = dto.rezervacija;
        var btn_class = " class='btn btn-info-allergies' "

        if (dto.canCancel != true)
            btn_class =" class='btn btn-info-allergies' disabled "


        var button_id = 'button-' + r.id;
        var row_string = 'id="' + button_id + '"';
        html += "<tr>"
        html += "<td>" + r.lek.naziv + "</td> <td>"+ r.apoteka.naziv + "</td> <td>" + r.rokZaPreuzimanje.split("T")[0] + "</td> <td>";
        html += "<button onclick='cancelReservation(this.id)' " + btn_class + row_string + '>Cancel</button>' + '</td> </tr>'
    })

    body.html(html)

}

function cancelReservation(id) {

    var r_id = id.split("-")[1]

    var obj = {
        id : r_id
    }

    $.ajax({
        type:'POST',
        url: '/reservation/cancel',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function() {
            alert('Succesfully canceled')
            reloadReserved();
        },
        error : function() {
            console.log('Could not load reserved medicine')
        }
        
    })
}