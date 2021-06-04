const past_presc_meds_body = $('#past_presc_meds_body')

function reloadPastPrescMeds() {

    $.ajax({
        type:'GET',
        url: '/api/user/getpastpresc/meds',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            fillPastPrescMedsTable(data)
          
        },
        error : function() {
            showError('Error message', 'Could not load past prescriptions')
        }
        
    })

}

function fillPastPrescMedsTable(data) {

    past_presc_meds_body.html('')
    var html = ''

    data.forEach(function(m) {

        var date = new Date(m.datumPrepisivanja)

        html += `
                <tr> <td> ${m.nazivLeka} </td> <td> ${date.toLocaleDateString('fr-CA')} </td> <td> ${m.nazivApoteke} </td> </tr>`
    })

    past_presc_meds_body.html(html)
}