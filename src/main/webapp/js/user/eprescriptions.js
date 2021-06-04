const past_epresc_body = $('#past_epresc_body')
const past_presc_filter = $('#past_presc_filter')
var past_presc = []

function reloadPastPresc() {

    $.ajax({
        type:'GET',
        url: '/api/user/getpastpresc',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            console.log(data)
            past_presc = data
            fillPrescTable(past_presc)
        },
        error : function() {
            showError('Error message', 'Could not load past prescriptions')
        }
        
    })

}

past_presc_filter.change(function(){

    var id = past_presc_filter.children("option").filter(":selected").attr("id")

    if(id == 'presc_none') {
        console.log('presc none')
        fillPrescTable(past_presc)
    }
    else if(id == 'presc_new') {
        console.log('presc active')
        filterByStatus('AKTIVAN')
    }
    else if(id == 'presc_processed') {
        console.log('presc processed')
        filterByStatus('OBRADJEN')
    }
    else {
        console.log('presc rejected')
        filterByStatus('ODBIJEN')
    }
})

function fillPrescTable(data) {

    // num date dur status
    past_epresc_body.html('')
    var html = ''
    
    data.forEach(function(p){
        var date = new Date(p.datumIzdavanja)//.toString()

        html += `
                <tr> <td> ${p.lekovi.length} </td> <td> ${date.toLocaleDateString()} </td> <td> ${p.trajanjeTerapije} </td> <td> ${p.status} </td> </tr>`

    }) 

    past_epresc_body.html(html)


}

function filterByStatus(status) {

    const filteredPrescs = past_presc.filter((presc) => {
        return (
            presc.status.toLowerCase().includes(status.toLowerCase())
            );
        });
        fillPrescTable(filteredPrescs);
}