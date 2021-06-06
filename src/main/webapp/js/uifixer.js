$(document).ready(function(){

    fixButtons()
})

function fixButtons() {

    $.ajax({
        type:'GET',
        url: '/api/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(user) {
            console.log('user found')
            createLoggedInButtons()
        },
        error : function() {
            console.log('user not found')
            setupLoginButton()
            setupRegistrationButton()
        }
        
    })
}

function createLoggedInButtons() {
    $('#loginNavBtn').html('LOGOUT')
    $('#loginNavBtn').click(function(){
        console.log('I should logout')

        $.ajax({
            type:'POST',
            url: '/auth/logout',
            contentType : 'application/json',
            success : function() {
                console.log('I have been logged out')
                localStorage.removeItem('myToken')
                location.reload()
            },
            error : function() {
                console.log('FAIL LOGOUT')
                //alert('Bad credentials')
            }
            
        })
    });

    $("#signUpNavBtn").html('PROFILE')
    $("#signUpNavBtn").click(function(){
        console.log('redirect me')
        redirectMe()
    });

}