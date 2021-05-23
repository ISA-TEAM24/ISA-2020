function reloadInfo() {

    $.ajax({
        type:'GET',
        url: '/api/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(user) {
            fillInfo(user);
        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
        }
        
    })

}

function fillInfo(user) {
    var loyalty = user.loyaltyInfo;
    var off;
    if (loyalty.klasa == 'REGULAR') {
        off = '0%';
    }
    else if (loyalty.klasa == 'SILVER') {
        off = '10%';
    }

    else {
        off = '25%';
    }
    var cat_string = "You currently belong in the " + loyalty.klasa
    cat_string += " category with " + loyalty.poeni + " points -  "
    cat_string +=  off + " off on all products and exams."
 
    var pen_string = "You currently have " + loyalty.penali + " penalty points"

    if (loyalty.penali < 2) {
        pen_string +=  " - no punishment";
    }
    else {
        pen_string += " you're unable to reserve medicine, consults or exams, or use E-Prescriptions"
    }

    var grade_string = "Your current grade : " + user.ocena +  ".0 / 5.0"

    $('#cat_div').html(cat_string)
    $('#pen_div').html(pen_string)
    $('#grade_div').html(grade_string)
}