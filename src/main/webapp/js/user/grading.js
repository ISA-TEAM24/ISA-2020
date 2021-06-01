
//id="select_none"
//id="select_pharmacy"
//id="select_meds">Medicine</option>
//id="select_derm"
//id="select_farm"
const select_group = $('#select_group')
const select_specific = $('#select_specific')
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
    }

    else if(group_id == 'select_derm') {
        console.log('selected derms')
    }

    else if(group_id == 'select_farm') {
        console.log('selected farms')
    }

    else {
        console.log('no group is selected')
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