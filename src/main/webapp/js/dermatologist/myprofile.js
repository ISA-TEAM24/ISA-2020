// imeId
// prezimeId
// telId
// adresaId
// drzavaId
// gradId
// mailId

// changepwbtn
// changeDatabtn

// CHANGE PW MODAL:  oldpw, newpw1, newpw2 --> savepwBtn , closeBtn
// CHANGE DATA MODAL: ime1, prezime1, telefon1, adresa1, drzava1, grad1, mail1 
//            --> closedatabBtn, savedataBtn 

$(document).ready(function() {
    test_login();
})


function test_login() {
    var form = {
        "username" : "dermUsername",
        "password" : "test"
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
            getMe();
        },
        error : function() {
            console.log('FAIL LOGIN')
        }
        
    })
}

function getMe() {
    $.ajax({
        type:'GET',
        url: '/dermatologist/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(dermatologist) {
            addProfileData(dermatologist);
            addProfileDataToChangeDataModal(dermatologist);
            if(dermatologist.prvoLogovanje == true) {
                console.log('Prvi put je logovan.')
                requirePwChange(dermatologist.username.toString());
            }
        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
        }    
    })
}

function addProfileData(dermatologist) {
    $('#imeId').attr('readonly','true');
    $('#prezimeId').attr('readonly','true');
    $('#telId').attr('readonly','true');
    $('#adresaId').attr('readonly','true');
    $('#drzavaId').attr('readonly','true');
    $('#gradId').attr('readonly','true');
    $('#mailId').attr('readonly','true');
	
    $( "div.punoIme" ).replaceWith( "<h2>" + dermatologist.ime + ' ' +dermatologist.prezime + "</h2>" );

    $('#imeId').val(dermatologist.ime)
    $('#prezimeId').val(dermatologist.prezime)
    $('#telId').val(dermatologist.telefon)
    $('#adresaId').val(dermatologist.adresa)
    $('#drzavaId').val(dermatologist.drzava)
    $('#gradId').val(dermatologist.grad)
    $('#mailId').val(dermatologist.email)
}

function addProfileDataToChangeDataModal(dermatologist) {
    $('#ime1').val(dermatologist.ime)
    $('#prezime1').val(dermatologist.prezime)
    $('#telefon1').val(dermatologist.telefon)
    $('#adresa1').val(dermatologist.adresa)
    $('#drzava1').val(dermatologist.drzava)
    $('#grad1').val(dermatologist.grad)
    $('#mail1').val(dermatologist.email)

    $('#mail1').attr('readonly','true');
}

function saveNewData(){
    
    var obj = {
     email :  $('#mail1').val().trim(),
     name :  $('#ime1').val().trim(),
     lastName :  $('#prezime1').val().trim(),
     address :  $('#adresa1').val().trim(),
     country :  $('#drzava1').val().trim(),
     city :  $('#grad1').val().trim(),
     phoneNumber :  $('#telefon1').val().trim()    
    }

    $.ajax({
        type:'PUT',
        url: '/dermatologist/editdata',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function(dermatologist) {
            addProfileData(dermatologist);
            addProfileDataToChangeDataModal(dermatologist);

        },
        error : function() {
            console.log('error occured')
        }
        
    })   
}

function changepw() {
    oldpw = document.getElementById("oldpw").value
    newpw1 = document.getElementById("newpw1").value
    newpw2 = document.getElementById("newpw2").value

    if (oldpw == "") {
        return;
    }

    if (newpw1 == "") {
        return;
    }

    if (newpw2 == "") {
        return;
    }

    if (newpw1 != newpw2) {

        console.log(document.getElementById("newpw1").value)
        console.log(document.getElementById("newpw2").value)

        $('#errormsg').text("Ne poklapaju se lozinke!");
        $('#successmsg').text("");
 
        console.log('bad match')
        return;
    }

    var obj = {
        oldPassword : oldpw,
        newPassword : newpw1
    }

    $.ajax({
        type:'POST',
        url: '/dermatologist/changepw',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function() {
            $('#errormsg').text("");
            $('#successmsg').text("Uspešno izmenjena lozinka!");

            var millisecondsToWait = 3000;
            setTimeout(function() {
                $('#changepwModal').modal('toggle');
            }, millisecondsToWait);

            $.ajax({
                type:'POST',
                url: '/dermatologist/firstlogpwchange',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function() {
                console.log('success in firstlogpwchange')
                },
                error : function() {
                    console.log(username)
                    console.log('error in firstlogpwchange')
                }
                
            })

        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
            $('#successmsg').text("");
            $('#errormsg').text("Greška!");

        }
        
    })
}

function clearpwFields() {
    document.getElementById("oldpw").value = '' 
    document.getElementById("newpw1").value = '' 
    document.getElementById("newpw2").value = '' 

    $('#errormsg').text("");
    $('#successmsg').text("");
}

function requirePwChange(username) {
    $('#errormsg').text("Pri prvom logovanju, obavezno je izmeniti lozinku!");
    $('#changepwModal').modal('show'); 

    $('#closepwModal').click( function() {
        location.reload();
    });
    $('#closeBtn').click( function() {
        location.reload();
    });

    console.log(username)
    changepw()

}
