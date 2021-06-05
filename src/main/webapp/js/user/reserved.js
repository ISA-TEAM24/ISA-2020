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
            refreshToken()
        },
        error : function() {
            console.log('Could not load reserved medicine')
            showError('Error Message', 'Could not load reservations.')
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
            //alert('Succesfully canceled')
            reloadReserved();
            refreshToken()
        },
        error : function() {
            console.log('Could not load reserved medicine')
            showError('Error Message', 'Could not cancel reserved medicine.')
        }
        
    })
}

//new_reserved_input

function searchMedicine() {

    var input = $('#new_reserved_input').val().trim()

    var obj = {
        id : input
    }

    $.ajax({
        type:'POST',
        url: '/reservation/findmeds',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function(data) {
            fillTableWithFoundMeds(data);
            refreshToken()
        },
        error : function() {
            console.log('Could not load reserved medicine')
            showError('Error message', 'Could not find the medicine.')
        }
        
    })
}

function fillTableWithFoundMeds(data) {
    
    var body = $('#new_reserve_body')

    var html = "";

    data.forEach(function(m){
        //name pharmacy adresa pickupby reserve
        var button_id = 'button-' + m.apoteka + '-' + m.idLeka
        html += "<tr>"
        html += "<td>" + m.lek + "</td>" + "<td>" + m.apoteka + "</td>" + "<td>" + m.adresa + "</td>"
        html += "<td>" + '<input type="date" id="date-picker-' + m.idLeka + '"> </td>'
        html += "<td>" + "<button onclick='reserveMedicine(this.id)' class='btn btn-info-allergies' id='" + button_id + "'>Reserve</button>"
    })

    body.html(html)
    data.forEach(function(m){
        limitDatePicker('date-picker-' + m.idLeka)
    })
}

function reserveMedicine(id) {

    var apoteka_naziv = id.split("-")[1]
    var idLeka = id.split("-")[2]
    var date_string = $('#date-picker-' + idLeka).val()
    var obj = {
        lek : idLeka,
        apoteka : apoteka_naziv,
        rokZaPreuzimanje : date_string
    }

    //console.log(obj)
    $.ajax({
        type:'POST',
        url: '/reservation/create',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function(data) {
            //alert('Successfully reserved')
            showError('Success Message', 'Successfully reserved medicine.')
            $('#new_reserve_body').html("")
            refreshToken()
        },
        error : function() {
            console.log('Could not load reserved medicine')
            showError('Error Message', 'Failed to reserve medicine.')
        }
        
    })


}

function limitDatePicker(dp) {
    var today = new Date();
    var dd = today.getDate() + 1;
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();
    if(dd<10){
            dd='0'+dd
        } 
        if(mm<10){
            mm='0'+mm
        } 

    today = yyyy+'-'+mm+'-'+dd;
    document.getElementById(dp).setAttribute("min", today);
}