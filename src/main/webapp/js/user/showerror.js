function showError(title, text) {

    alert_title.html(title)
    alert_body.html(text)
    alert_box.modal('show');
}

function refreshToken() {

    $.ajax({
        type:'POST',
        url: '/auth/refresh',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            localStorage.setItem('myToken', data.accessToken)
            console.log('your token has been refreshed')
        },
        error : function() {
            console.log('Could not reset penalties')
            showError('Invalid token', 'Your token has expired please log in again.')
        }
        
    })
}