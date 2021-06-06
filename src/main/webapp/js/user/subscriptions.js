const subs_body = $('#subs_body')

function reloadSubs(){

    $.ajax({
        type:'GET',
        url: '/api/user/subs',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            console.log(data)
            fillSubs(data)
            refreshToken()
        },
        error : function() {
            console.log('Could not load allergies')
            showError('Error message', 'Could not load subscriptions.')
        }
        
    })
}

function fillSubs(data) {

    subs_body.html('')
    var html = ''
    for (let key of Object.keys(data)) {
        var follows = data[key];
        console.log(key, follows);
        var helper
        if (follows) {
            helper = 'checked'
        }
        else{
            helper = ''
        }
        html+= `
            <tr>
            <td> ${key} </td>
            <td> <label class="switch"> <input type="checkbox" onchange=changeSubStatus(this.id) id="check-${key}" ${helper}> <span class="slider round"></span> </label> </td>
            </tr>
        `

    }

    subs_body.html(html)
}

function changeSubStatus(id) {

    var follows;

    if($('#' + id).is(":checked")) {
        console.log('WE CHECKED')
        follows = true

    }
    else {
        console.log('WE UNCHECKED')
        follows = false
    }

    var obj = {
        naziv : id.split("-")[1],
        prati : follows
    }

    $.ajax({
        type:'PUT',
        url: '/api/user/subs/update',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function() {
            console.log('Sub success')
        },
        error : function() {
            console.log('Could not save subs')
        }
        
    })


}