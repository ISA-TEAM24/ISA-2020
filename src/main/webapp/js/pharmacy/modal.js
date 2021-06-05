const pharmacy_alert_box = $('#pharmacy_alert_modal')
const pharmacy_alert_title = $('#pharmacy_alert_title')
const pharmacy_alert_body = $('#pharmacy_alert_body')

function redirectToUserPage(where) {

    //window.location.href = '../user/me.htm#consult'
    $.ajax({
        type:'GET',
        url: '/api/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(user) {
            redirectCheck(user, where)
        },
        error : function(xhr,status,data) {
            console.log('An Error has occured while trying to reload the profile')
            if (xhr.status == 401) {
                showModal('Not logged in', 'You need to log in to use this funcionality.')
            }
            else {
                showModal('Error message', 'Could not load pharmacy information.')

            }
        }
        
    })

}

function redirectCheck(user,where) {

    if (user != undefined) {
        showModal('Not logged in', 'You need to log in to use this funcionality.')
    }

    var shouldRedirect = false
    user.authorities.forEach(function(a) {
        if(a.authority == 'ROLE_USER') {
            shouldRedirect = true
        }
    })

    if (shouldRedirect) {
        window.location.href = '../user/me.html#' + where
    }

}

function showModal(title, text) {

    pharmacy_alert_title.html(title)
    pharmacy_alert_body.html(text)
    pharmacy_alert_box.modal('show');
}
