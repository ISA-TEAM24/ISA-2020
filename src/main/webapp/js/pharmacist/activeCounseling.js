$(document).ready(function() {
    getData();
})

function getData() {

    var url = window.location.href;
    console.log(url);

    var id = url.split("?")[1];
    console.log("MOJ ID POSETE JE: " + id);

    
    $.ajax({
        type:'POST',
        url: '/api/visit/findVisit',
        contentType : 'text/plain',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : id,
        success : function(data) {
            console.log('Success');
            fillFields(data);

        },
        error : function() {
            console.log('Error');
        }
        
    }) 
}

function fillFields(data) {
    $("#imeLabel").text(data.ime);
    $("#prezimeLabel").text(data.prezime);
    $("#emailLabel").text(data.email);
    $("#telLabel").text(data.telefon);
    $("#alergijeLabel").text(data.alergije.allergies);

    console.log(data.alergije.allergies.length);

    if(data.alergije.allergies.length == 0) {
        $("#alergijeLabel").text("nema");
    }

    console.log(data.alergije);
}