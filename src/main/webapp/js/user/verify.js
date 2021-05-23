$(document).ready(function() {
    
    verifyUser();
})


function printHeader() {
    var counter = 5;
    var interval = setInterval(function() {
        counter--;
        // Display 'counter' wherever you want to display it.
        if (counter < 1) {
            // Display a login box
            window.location.replace("../index.html");
        }
        else {
            $('#h1id').html("Thank you for confirming your email you will be redirected in " + counter + " seconds");
        }


    }, 1000);
}

function verifyUser() {
    $.ajax({
        type:'GET',
        url: '/auth/verify/' + window.location.href.split("?")[1],
        contentType : 'application/json',
        success : function() {
           printHeader();
        },
        error : function() {
            $('#h1id').html("An error has occurred, unsuccessfull email confirmation.");
        }
        
    })
}