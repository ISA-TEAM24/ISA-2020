
//id="select_none"
//id="select_pharmacy"
//id="select_meds">Medicine</option>
//id="select_derm"
//id="select_farm"
const select_group = $('#select_group')
const select_specific = $('#select_specific')
const grade_note = $('#grade_note')
const grade = $('#grade_number')
var pharmacy_list = []
var med_list = []
var derm_list = []
var farm_list = [] 

select_group.change(function(){

    var group_id = select_group.children(":selected").attr("id");

    if(group_id == 'select_pharmacy') {
        console.log('selected pharmacies')
        getPharmaciesForGrading()
    }

    else if(group_id == 'select_meds') {
        console.log('selected meds')
        getMedsForGrading()
    }

    else if(group_id == 'select_derm') {
        console.log('selected derms')
        getDermsForGrading()
    }

    else if(group_id == 'select_farm') {
        console.log('selected farms')
        getFarmsForGrading()
    }

    else {
        console.log('no group is selected')
        select_specific.prop('selectedIndex', 0)
    }

}) 

function getPharmaciesForGrading() {

    $.ajax({
        type:'GET',
        url: '/api/user/grading/pharmacies',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            pharmacy_list = data 
            editSelectionWithPharmacies()
        },
        error : function() {
            console.log('Could not load pharmacies')
        }
        
    })

}

function getMedsForGrading() {

    $.ajax({
        type:'GET',
        url: '/api/user/grading/meds',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            med_list = data 
            editSelectionWithMeds()
        },
        error : function() {
            console.log('Could not load meds')
        }
        
    })

}

function getFarmsForGrading() {

    $.ajax({
        type:'GET',
        url: '/api/user/grading/farms',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            farm_list = data 
            editSelectionWithFarms()
        },
        error : function() {
            console.log('Could not load farms')
        }
        
    })

}

function getDermsForGrading() {

    $.ajax({
        type:'GET',
        url: '/api/user/grading/derms',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            derm_list = data 
            editSelectionWithDerms()
        },
        error : function() {
            console.log('Could not load derms')
        }
        
    })

}

function editSelectionWithPharmacies() {

    var html = ""
    
    pharmacy_list.forEach(function(p){
        console.log(p)
        html += `
            <option id="option-${p.id}">${p.naziv}, ${p.adresa}</option>
        `
    })

    select_specific.html(html)
    console.log(pharmacy_list)
}

function editSelectionWithMeds() {

    var html = ""
    
    med_list.forEach(function(m){
        console.log(m)
        html += `
            <option id="option-${m.id}">${m.naziv}</option>
        `
    })

    select_specific.html(html)
    console.log(med_list)

}

function editSelectionWithFarms() {

    var html = ""
    
    farm_list.forEach(function(f){
        console.log(f)
        html += `
            <option id="option-${f.id}">dr ${f.ime} ${f.prezime}</option>
        `
    })

    select_specific.html(html)
    console.log(farm_list)
}

function editSelectionWithDerms() {

    var html = ""
    
    derm_list.forEach(function(d){
        console.log(d)
        html += `
            <option id="option-${d.id}">dr ${d.ime} ${d.prezime}</option>
        `
    })

    select_specific.html(html)
    console.log(derm_list)
}

function sendGrade() {

    var primaoc;
    
    var group_id = select_group.children(":selected").attr("id");

    if(group_id == 'select_pharmacy') {
        primaoc = 'apoteka'
    }

    else if(group_id == 'select_meds') {
        primaoc = 'lek'
    }

    else if(group_id == 'select_derm') {
        primaoc = 'osoba'
    }

    else if(group_id == 'select_farm') {
        primaoc = 'osoba'
    }

    else {
        return
    }

    var obj = {
        "recipientID" : select_specific.children(":selected").attr("id").split("-")[1],
        "vrstaPrimaoca" : primaoc,
        "ocena" : grade.val().trim(),
        "note" : grade_note.val().trim()
    }

    console.log(obj)

    if (obj.ocena > 5) {
        console.log('>5')
        return
    }

    if (obj.ocena < 0) {
        console.log('<0')
        return
    }

    $.ajax({
        type:'POST',
        url: '/api/user/grading/add',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function(data) {
            alert('Successfully left grade')
            resetDropDowns()
        },
        error : function() {
            console.log('Could not leave grade')
        }
        
    })

    
}

function resetDropDowns() {

    select_group.prop('selectedIndex', 0).change()
    select_specific.html("")
    grade.val("")
    grade_note.val("")
}