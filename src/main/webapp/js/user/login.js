const username = $('#username_input')
const pw = $('#pw_input')

$(document).ready(function() {
    console.log("asdsasad");
});

$('#loginNavBtn').click(function(){
        console.log('hello');
        $("#registrationFormModal").hide();
        disableScroll();
        $('#loginFormModal').show();
        hideIndex1()
        
        
    }); 


function disableScroll(){
        var scrollPosition = [
        self.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft,
        self.pageYOffset || document.documentElement.scrollTop  || document.body.scrollTop
                            ];
        var html = jQuery('html'); // it would make more sense to apply this to body, but IE7 won't have that
        html.data('scroll-position', scrollPosition);
        html.data('previous-overflow', html.css('overflow'));
        html.css('overflow', 'hidden');
        window.scrollTo(scrollPosition[0], scrollPosition[1]);
    }

function enableScroll(){
    var html = jQuery('html');
    var scrollPosition = html.data('scroll-position');
    html.css('overflow', html.data('previous-overflow'));
    window.scrollTo(scrollPosition[0], scrollPosition[1])
}


$('#cancelBtn').click(function(){
        enableScroll();
        $('#loginFormModal').hide();
        showIndex1()
    }); 

function showIndex1() {

    $('#search').show()
    $('#banner_section').show()

}

function hideIndex1() {

    $('#search').hide()
    $('#banner_section').hide()

}

function logoListener() {

    $("#registrationFormModal").hide();
    $('#loginFormModal').hide();
    showIndex1()
    enableScroll()
}

function logMeIn() {

    var form = {
        "username" : username.val(),
        "password" : pw.val()
    };

    $.ajax({
        type:'POST',
        url: '/auth/login',
        contentType : 'application/json',
        data : JSON.stringify(form),
        success : function(retToken) {
            console.log('LOGGED IN')
            console.log('your token is' + retToken.accessToken)
            localStorage.setItem('myToken', retToken.accessToken);
            redirectMe()
            //reloadProfile();
        },
        error : function() {
            console.log('FAIL LOGIN')
            alert('Bad credentials')
        }
        
    })

}

function redirectMe() {

    $.ajax({
        type:'GET',
        url: '/api/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(user) {
            redirectToDashboard(user)
        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
        }
        
    })
}

function redirectToDashboard(user){

    if (user.activated == false) {
        alert('Please activate your account')
    }

    var auth = user.authorities[0]
    console.log(auth)
    if(auth.authority == "ROLE_USER") {
        console.log('You are a user redirecting to me.html')
        window.location.href = 'user/me.html'
    }

    if(auth.authority == "ROLE_DERMATOLOGIST") {
        console.log('You are a dermatologist redirecting to indexDermatolog.html')
        window.location.href = 'dermatologist/indexDermatolog.html'
    }

    if(auth.authority == "ROLE_PHARMACIST") {
        console.log('You are a user redirecting to indexFarmaceut.html')
        window.location.href = 'pharmacist/indexFarmaceut.html'
    }


        
}

