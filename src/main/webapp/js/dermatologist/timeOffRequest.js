// odDatuma
// doDatuma
// vrstaZahteva
// razlogZahteva
// podnesiZahtev 
$(document).ready(function() {
    //test_login();   // just for testing

    var millisecondsToWait = 1000;
    setTimeout(function() {
        getRequests();
    }, millisecondsToWait);

})

/*
function test_login() {
    var pageURL = $(location).attr("href");
    console.log(pageURL);
    var part = pageURL.split("/");
    console.log(part[3]);

    if(part[3] == 'dermatologist') {
        var form = {
            "username" : "dermUsername",
            "password" : "test"
        }; 
    } else {
        var form = {
            "username" : "farmaceut1",
            "password" : "test"
        };
    }

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
        success : function(user) {
            //addProfileData(dermatologist);
            //addProfileDataToChangeDataModal(dermatologist);
            //if(dermatologist.prvoLogovanje == true) {
            //    console.log('Prvi put je logovan.')
            //    requirePwChange(dermatologist.username.toString());
            //}
        },
        error : function() {
            console.log('An Error has occured while trying to reload the profile')
        }    
    })
}
*/

function sendRequest() {
    
    var odDatuma = new Date($('#odDatuma').val());
    console.log(odDatuma);
    var doDatuma = new Date($('#doDatuma').val());
    console.log(doDatuma);
    var vrsta = $('#vrstaZahteva').val();
    console.log(vrsta);
    var razlog = $('#razlogZahteva').val();
    console.log(razlog);

    if(odDatuma > doDatuma) {
        $("#errormsg").text("Od datum mora biti pre do datuma!");
        $("#successmsg").text("");
        refreshPageWithDelay();
        return;
    }

    if(isNaN(odDatuma)){
        $("#errormsg").text("Izaberite datum!");
        $("#successmsg").text("");
        refreshPageWithDelay();
        return;
    }

    if(isNaN(doDatuma)){
        $("#errormsg").text("Izaberite datum!");
        $("#successmsg").text("");
        refreshPageWithDelay();
        return;
    }

    var obj = {
        "odDatuma" : odDatuma,
        "doDatuma" : doDatuma,
        "vrsta" : vrsta,
        "razlog" : razlog
    }

    $.ajax({
        type:'POST',
        url: '/timeoff/addnew',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(obj),
        success : function() {
            console.log('SUCCESS')
            $("#successmsg").text("Uspešno poslat zahtev!");
            $("#errormsg").text("");
            alert("Uspešno poslat zahtev!");
            refreshPageWithDelay();
        },
        error : function() {
            console.log('FAIL')
            $("#errormsg").text("Error!");
            $("#successmsg").text("");
        }
        
    })
}

function refreshPageWithDelay(){
    var millisecondsToWait = 2000;
    setTimeout(function() {
        $("#razlogZahteva").val('');
        $('#doDatuma').val('');
        $('#odDatuma').val('');
        location.reload();
    }, millisecondsToWait);

}

function getRequests() {
    $.ajax ({
        type : 'GET',
        url : '/timeoff/requests/all',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            console.log('Loaded requests')
            fillRequestTable(data)
        },
        error : function() {
            console.log('Could not load Requests!')
        }
    })
}

function fillRequestTable(data) {
    
    var table = document.getElementById('requestBody')
    console.log(data)
    table.innerHTML = ''
    for(var i = 0; i < data.length; i++ ) {
        var vrsta = '';
        if(data[i].vrsta == "GODISNJI") {
            vrsta = "Godišnji odmor"
        } else {
            vrsta = "Odsustvo"
        }

        var status = '';
        if(data[i].stanjeZahteva == 'PRIHVACEN') {
            status = "PRIHVAĆEN"
        } else {
            status = data[i].stanjeZahteva;
        }

        var row = `<tr>
                        <td>${data[i].odDatuma.split('T')[0]}</td>
                        <td>${data[i].doDatuma.split('T')[0]}</td>
                        <td>${vrsta}</td>
                        <td>${data[i].razlog}</td>
                        <td>${status}</td>
                    </tr>`

        table.innerHTML += row
    }    
}