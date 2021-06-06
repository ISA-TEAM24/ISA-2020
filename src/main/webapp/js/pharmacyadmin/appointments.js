var usernm

var today = new Date().toISOString().split('T')[0];
document.getElementsByName("setTodaysDate")[0].setAttribute('min', today);

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
            usernm = params.toString()
            usernm = usernm.slice(0, -1)

            $.ajax({
                type: 'GET',
                url: '/dermatologist/getdermwithappointments/' + usernm,
                contentType: 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(dermatolog) {
                    addPageData(dermatolog)
                }
            })
        }, error : function() {
            alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
        }   
    })
}

function addPageData(dermatolog) {

    var table = "";
    dermatolog.radnaVremena.forEach(function(rv) {
        table +=    `<tr>
                        <td>${rv.odDatum}</td>
                        <td>${rv.doDatum}</td>
                        <td>${rv.odVreme}</td>
                        <td>${rv.doVreme}</td>
                        <td>${rv.apoteka}</td>
                        <td><ul>`;

        rv.neradnidani.forEach(function(nd){
            if (nd == 1) {
                table +=        `<li>Monday</li>`;
            } else if (nd == 2) {
                table +=        `<li>Tuesday</li>`;
            } else if (nd == 3) {
                table +=        `<li>Wednesday</li>`;
            } else if (nd == 4) {
                table +=        `<li>Thursday</li>`;
            } else if (nd == 5) {
                table +=        `<li>Friday</li>`;
            } else if (nd == 6) {
                table +=        `<li>Saturday</li>`;
            } else if (nd == 7) {
                table +=        `<li>Sunday</li>`;
            }
        })

        table +=       `</ul></td></tr>`;
                        
    });

    $("#myTable").append(table);

    var table2 = "";
    dermatolog.zakazaniPregledi.forEach(function(zp) {
        table2 +=    `<tr>
                        <td>${zp.zaposleni}</td>
                        <td>${zp.datum}</td>
                        <td>${zp.vreme}</td>
                        <td>${zp.trajanje}</td>
                        <td>${zp.apoteka}</td>
                     </tr>`;
    })

    $("#myTable2").append(table2);

    $("#name").val(dermatolog.username);

}

function addAppointment() {
    
    if (!validateFields()) {
        alert('You must fill all fields')
        return;
    }

    var appointment = {
        dermatologist : $('#name').val(),
        date : $('#datefield').val(),
        time : $('#timefield').val(),
        duration : $('#durationfield').val(),
    }

    $.ajax({
        type:'POST',
        url: '/api/addnewappointment',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(appointment),
        success : function() {
            alert("SUCCESS");
        }, error : function() {
            alert("Dermatologist isn't in the pharmacy or already has schedulled appointment at that time. Check tables above.");
        }
    })
}

function validateFields() {
    if (!$('#datefield').val()) return false;
    if (!$('#timefield').val()) return false;
    if ($('#durationfield').val() == 0) return false;
    if ($('#pricefield').val() == 0) return false;

    return true;
}
