const search_field = $('#search_field')
const search_list = $('#search_list') 
let pharmacy_list = []

function searchEvent() {
    if ($("#dropdown_id").children("option").filter(":selected").text().includes('PHARMACY')) {
        console.log("../pharmacy/pharmacies.html")
        window.location.href="../pharmacy/pharmacies.html?" + search_field.val().trim().replace(" ", "%")
    }
}

function getAllPharmacies() {
    $.ajax({
        type:'GET',
        url: '/search/open/pharmacies',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            pharmacy_list = data
        },
        error : function() {
            console.log('Could not load pharmacies')
        }
        
    })
}

$("#dropdown_id").change(function(){

    console.log('change')
    search_list.html("")
})

search_field.keyup(function(){

    if ($("#dropdown_id").children("option").filter(":selected").text().includes('PHARMACY')) {

        
        const searchString = search_field.val().toLowerCase();
        //console.log(searchString)
        const filteredPharmacies = pharmacy_list.filter((pharmacy) => {
            return (
                pharmacy.naziv.toLowerCase().includes(searchString) ||
                pharmacy.adresa.toLowerCase().includes(searchString)
                );
            });
            displayPharmacies(filteredPharmacies);
    }
})



function displayPharmacies(pharmacies) {

    var html = ""
    
    pharmacies.forEach(function(p){
        //console.log(p)
        html += `
            <option id="option-${p.id}" value="${p.naziv}, ${p.adresa}">
        `
    })
    search_list.html(html)

}

getAllPharmacies();
