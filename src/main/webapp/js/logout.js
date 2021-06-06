function logOutPharmDerm() {
    $.ajax({
        type:'POST',
        url: '/auth/logout',
        contentType : 'application/json',
        success : function() {

            console.log('I have been logged out')
            localStorage.removeItem('myToken')
            //location.reload()
        },
        error : function() {
            console.log('FAIL LOGOUT')
            //alert('Bad credentials')
        }
        
    })
}