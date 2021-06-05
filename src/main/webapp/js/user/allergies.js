//allergies_body
const alergies_box = $('#allergies-input')
function reloadAllergies() {

    $.ajax({
        type:'GET',
        url: '/api/user/allergies',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            fillAllergies(data)
        },
        error : function() {
            console.log('Could not load allergies')
            showError('Error message', 'Could not load allergies.')
        }
        
    })

}

function fillAllergies(data) {

    var body = $('#allergies_body');
    body.html(buildTable(data));

} 

function buildTable(data) {

    var html = "";
    
    data.allergies.forEach(function(a) {

        td_with_id = 'button-' + a;
        html += '<tr> <td>';
        html += a;
        html += '</td> <td>';
        html += '<button onclick="removeAllergy(this.id)" class="btn btn-info-allergies" id="' + td_with_id + '"> Remove </button> </td> </tr>';

    })

    return html;
}

function addAllergy() {
    var name = $('#allergies-input').val().trim();

    var pattern = alergies_box.attr("pattern");
    var re = new RegExp(pattern);
    if (!re.test(alergies_box.val())) {
        showError('Error message', 'Forbidden character in allergy field')
        console.log('allergy does not match pattern')
        return
    }

    var list = [];
    list.push(name);
    var obj = {
        "allergies" : list
    }

    $.ajax({
        type:'POST',
        url: '/api/user/allergy/add',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function() {
            //$('#allergies-add-button').html("Allergy added!");
            //countDownToAllergyButtonEdit(3);

            reloadAllergies();
        },
        error : function() {
            //$('#allergies-add-button').html("Failed to add allergy!");
            //countDownToAllergyButtonEdit(3);
            showError('Error message', 'Could not add allergy, please try again later')
        }
        
    })

}

function countDownToAllergyButtonEdit(x) {
    var counter = x;
    var interval = setInterval(function() {
        counter--;
        // Display 'counter' wherever you want to display it.
        if (counter < 1) {
            // Display a login box
            $('#allergies-add-button').html('Add new allergy')
        }


    }, 1000);
}

function removeAllergy(name) {

    var removeItem = name.split('-')[1];
    var list = [];
    list.push(removeItem);
    var obj = {
        "allergies" : list
    }
    $.ajax({
        type:'POST',
        url: '/api/user/allergy/remove',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function() {
            //$('#allergies-add-button').html("Allergy removed!");
            //countDownToAllergyButtonEdit(3);
            showError('Success', 'Allergy list successfully edited')
            reloadAllergies();
        },
        error : function() {
            //$('#allergies-add-button').html("Failed to remove allergy!");
            //countDownToAllergyButtonEdit(3);
            showError('Error message', 'Could not remove allergy')
        }
        
    })

}