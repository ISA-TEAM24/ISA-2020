
//id="select_none"
//id="select_pharmacy"
//id="select_meds">Medicine</option>
//id="select_derm"
//id="select_farm"
const select_group_c = $('#select_group_complaint')
const select_specific_c = $('#select_specific_complaint')
const complaint_text = $('#complaint_text')

var pharmacy_list = []
var med_list = []
var derm_list = []
var farm_list = [] 

select_group_c.change(function(){

    var group_id = select_group_c.children(":selected").attr("id");

    if(group_id == 'select_pharmacy_c') {
        console.log('selected pharmacies')
        getPharmaciesForComplaint()
    }

    else if(group_id == 'select_meds_c') {
        console.log('selected meds')
        getMedsForComplaint()
    }

    else if(group_id == 'select_derm_c') {
        console.log('selected derms')
        getDermsForComplaint()
    }

    else if(group_id == 'select_farm_c') {
        console.log('selected farms')
        getFarmsForComplaint()
    }

    else {
        console.log('no group is selected')
        select_specific.prop('selectedIndex', 0)
    }

}) 

function getPharmaciesForComplaint() {

    $.ajax({
        type:'GET',
        url: '/api/user/grading/pharmacies',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            pharmacy_list = data 
            editSelectionWithPharmaciesC()
        },
        error : function() {
            console.log('Could not load pharmacies')
        }
        
    })

}

function getMedsForComplaint() {

    $.ajax({
        type:'GET',
        url: '/api/user/grading/meds',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            med_list = data 
            editSelectionWithMedsC()
        },
        error : function() {
            console.log('Could not load meds')
        }
        
    })

}

function getFarmsForComplaint() {

    $.ajax({
        type:'GET',
        url: '/api/user/grading/farms',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            farm_list = data 
            editSelectionWithFarmsC()
        },
        error : function() {
            console.log('Could not load farms')
        }
        
    })

}

function getDermsForComplaint() {

    $.ajax({
        type:'GET',
        url: '/api/user/grading/derms',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            derm_list = data 
            editSelectionWithDermsC()
        },
        error : function() {
            console.log('Could not load derms')
        }
        
    })

}

function editSelectionWithPharmaciesC() {

    var html = ""
    
    pharmacy_list.forEach(function(p){
        console.log(p)
        html += `
            <option id="option-${p.id}">${p.naziv}, ${p.adresa}</option>
        `
    })

    select_specific_c.html(html)
    console.log(pharmacy_list)
}

function editSelectionWithMedsC() {

    var html = ""
    
    med_list.forEach(function(m){
        console.log(m)
        html += `
            <option id="option-${m.id}">${m.naziv}</option>
        `
    })

    select_specific_c.html(html)
    console.log(med_list)

}

function editSelectionWithFarmsC() {

    var html = ""
    
    farm_list.forEach(function(f){
        console.log(f)
        html += `
            <option id="option-${f.id}">dr ${f.ime} ${f.prezime}</option>
        `
    })

    select_specific_c.html(html)
    console.log(farm_list)
}

function editSelectionWithDermsC() {

    var html = ""
    
    derm_list.forEach(function(d){
        console.log(d)
        html += `
            <option id="option-${d.id}">dr ${d.ime} ${d.prezime}</option>
        `
    })

    select_specific_c.html(html)
    console.log(derm_list)
}

function sendComplaint() {

    var primaoc;
    
    var group_id = select_group_c.children(":selected").attr("id");

    if(group_id == 'select_pharmacy_c') {
        primaoc = 'apoteka'
    }

    else if(group_id == 'select_meds_c') {
        primaoc = 'lek'
    }

    else if(group_id == 'select_derm_c') {
        primaoc = 'osoba'
    }

    else if(group_id == 'select_farm_c') {
        primaoc = 'osoba'
    }

    else {
        console.log('I returned')
        return
    }

    var obj = {
        "idPrimaoca" : select_specific_c.children(":selected").attr("id").split("-")[1],
        "vrstaPrimaoca" : primaoc,
        "text" : complaint_text.val().trim()
    }

    console.log(obj)
    

    $.ajax({
        type:'POST',
        url: '/api/user/complaint/add',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function(data) {
            showError('Success message', 'Successfully left complaint')
            resetDropDowns()
        },
        error : function() {
            console.log('Could not leave complaint')
            showError('Error message', 'Failed to leave complaint, please try again later!')
        }
        
    })

    
}

function resetDropDowns() {

    select_group_c.prop('selectedIndex', 0).change()
    select_specific_c.html("")
    complaint_text.val('')
}