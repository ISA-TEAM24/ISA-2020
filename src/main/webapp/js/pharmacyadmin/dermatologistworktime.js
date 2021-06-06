var usernm

$(document).ready(function() {
    getMe();
})

function getMe() {
    $.ajax({
        type:'GET',
        url: '/phadmin/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(phadmin) {
            if(phadmin.prvoLogovanje == true) {
                console.log('Prvi put je logovan.')
                window.location.href = 'index.html';
            }
            const params = new URLSearchParams(window.location.search)
            usernm = params.toString();
            usernm = usernm.slice(0, -1)

            $.ajax({
                type: 'GET',
                url: '/dermatologist/getdermwithradnoinfo/' + usernm,
                contentType: 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(dermatolog) {
                    addPageData(dermatolog)
                    refreshToken();
                }
            })
            refreshToken();
        }, error : function() {
            //alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
        }  
    })
}

function addPageData(dermatolog) {
    $('#name').val(dermatolog.ime)
    $('#lastname').val(dermatolog.prezime)

    var table = "";
    dermatolog.radnaVremena.forEach(function(rv) {
        table +=    `<tr>
                        <td>${rv.odDatum}</td>
                        <td>${rv.doDatum}</td>
                        <td>${rv.odVreme}</td>
                        <td>${rv.doVreme}</td>
                        <td>${rv.apoteka}</td>
                     <tr>`
    });

    $("#myTable").append(table);
}

function register() {
    
    if (!validateFields()) {
        alert('You must fill all fields')
        return;
    }

    if (!endBeforeStart()) {
        alert('End date is before start date')
        return;
    }

    var derm = {
        username : usernm,
        odDatum : $('#odDatum').val(),
        doDatum : $('#doDatum').val(),
        odVreme : $('#odVreme').val(),
        doVreme : $('#doVreme').val(),
        neradniDani : $('#neradniDani').val()
    }

    $.ajax({
        type:'POST',
        url: '/phadmin/hiredermatologist',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(derm),
        success : function() {
            document.location.href = "dermatologistslist.html"
        }, error : function() {
            alert("Your dates overlaps with other work times");
        }
    })
}

function validateFields() {
    if (!$('#odDatum').val()) return false;
    if (!$('#doDatum').val()) return false;
    if (!$('#odVreme').val()) return false;
    if (!$('#doVreme').val()) return false;

    return true;
}

function endBeforeStart() {
    if ($('#odDatum').val() > $('#doDatum').val()) return false;

    return true;
}