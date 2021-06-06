//var u;
function penaltyCheck(title, tab) {
 
    $.ajax({
        type:'GET',
        url: '/api/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(user) {
            //return isUserAllowed(user)
            isUserAllowed(user, title, tab)
        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
            //showError('Error message', 'Could not load  information.')
        }
        
    })

}

function isUserAllowed(user, title, tab) {
    console.log(user)
    if(user.loyaltyInfo.penali > 2) {
        console.log('You have been penalized')
        showError(title, 'Because of the amount of penalties you are not allowed to use this functionality, penalties reset every 1st of the month.')
        //$('#' + tab).click()
        countDownToPageReloading(5)
        return
    }    

}