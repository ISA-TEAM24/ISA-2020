var apoteka;
var retData;
var pick_date;
var pick_time;
function findPharmacies() {

    //alert($('#consult-date-picker').val() + "**" + $('#consult-time-picker').val())

    var obj = {
        "date" : $('#consult-date-picker').val().trim(),
        "time" : $('#consult-time-picker').val().trim()
    }
    
    pick_date = obj.date;
    pick_time = obj.time;
    
    $.ajax({
        type:'POST',
        url: '/api/user/consult/check',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function(data) {
            //showError()
            retData = data;
            fillConsultTable(data);
            refreshToken()
        },
        error : function() {
            console.log('An Error has occured while trying to check the consult availability')
            showError('Error message', 'An Error has occured while trying to check the consult availability.')
        }
        
    })
}

function fillConsultTable(data) {

    var table = $('#new_consult_table');
    //table.html(buildInitialTable_1());
    var initial_html = buildInitialTable_1();
    //var body = $('#consult_body');

    var html = initial_html;

    data.forEach(function(apoteka) {
        
        html += "<tr> <td>" + apoteka.naziv + "</td>"
        html += " <td>" + apoteka.adresa + " </td>"
        html += " <td>" + apoteka.ocena + " </td>"
        html += " <td>" + apoteka.cenaSavetovanja + " </td>"
        html +=" <td> <button onclick='findPharmacists(this.id)' id='button-" + apoteka.naziv + "' class='btn btn-info-allergies'>Open</button></td></tr>"
    })
    html += ' </tbody>'
    table.html(html);


}
// NOTE : TBODY IS NOT CLOSED CLOSE IT MANUALLY
function buildInitialTable_1() {
    var html =''
    html += '<thead><tr><th scope="col" onclick="sortTable(0,' + 'new_consult_table' + ')">Pharmacy</th>'
    html +='<th scope="col" onclick="sortTable(1,' + 'new_consult_table'+ ')">Address</th>'
    html +='<th scope="col" onclick="sortTable(2,' + 'new_consult_table' + ')">Grade</th>'
    html +='<th scope="col" onclick="sortTable(3,' + 'new_consult_table' + ')">Price</th>'
    html += '<th scope="col></th>  </tr>'
    html += '</thead><tbody id="consult_body">'
      
    return html;
}

function buildInitialTable_2() {
    var html =''
    html += '<thead><tr><th scope="col" onclick="sortTable(0,' + 'new_consult_table' + ')">Name</th>'
    html +='<th scope="col" onclick="sortTable(1,' + 'new_consult_table'+ ')">Last Name</th>'
    html +='<th scope="col" onclick="sortTable(2,' + 'new_consult_table' + ')">Grade</th>'
    html += '<th scope="col></th> </tr>'
    html += '</thead><tbody id="consult_body">'
      
    return html;
}

function fillPharmacistTable(farmaceuti) {

    var html = buildInitialTable_2();
    farmaceuti.forEach(function(f) {

        html += "<tr> <td>" + f.ime + "</td>"
        html += " <td>" + f.prezime + " </td>"
        html += " <td>" + f.ocena + " </td>"
        html +=" <td> <button onclick='bookConsult(this.id)' id='button-" + f.username + "' class='btn btn-info-allergies'>Book consult</button> </td></tr>"
    })
    html += '</tbody>'
    $('#new_consult_table').html(html)

}

function findPharmacists(btn){
    
    var apoteka_id = btn.split('-')[1]
    var farms;
    retData.forEach(function(apoteka){
        if (apoteka.naziv == apoteka_id) {
            farms = apoteka.farmaceuti
        }
    })
    apoteka = apoteka_id;
    fillPharmacistTable(farms)
    
}

function bookConsult(id) {
    
    //"date" : $('#consult-date-picker').val().trim(),
    //"time" : $('#consult-time-picker').val().trim()
    var obj = {
        "vreme" : pick_time, 
        "datum" : pick_date,
        "trajanje" : "30",
        "zaposleni" : id.split("-")[1],
        "apoteka" : apoteka,
        "poeni" : "10"
    }

    $.ajax({
        type:'POST',
        url: '/api/user/consult/add',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function(data) {
            showError('Success message', 'Successfully scheduled consult!')
            $('#new_consult_table').html(buildInitialTable_1())
            refreshToken()
        },
        error : function() {
            console.log('An Error has occured while trying to schedule the consult')
            showError('Error message', 'An Error has occured while trying to schedule the consult.')
        }
        
    })
}