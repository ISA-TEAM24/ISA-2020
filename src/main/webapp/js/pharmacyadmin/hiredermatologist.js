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

            $.ajax({
                type:'GET',
                url:'/phadmin/getalldermatologists',
                contentType:'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(dermatologists) {
                    addDermatologistsToTable(dermatologists);
                }
            })
        }, error : function() {
            //alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
        }    
    })
}

function addDermatologistsToTable(dermatologists) {


    var table = "";
    dermatologists.forEach(function(derm) {

        table +=    `<tr>
                        <td>${derm.ime}</td>
                        <td>${derm.prezime}</td>
                        <td>${derm.ocena}</td>
                        <td>
                            <ul>`
        derm.radiUApotekama.forEach(function(apoteka) {
            table +=            `<li>${apoteka.naziv}</li>`
        });
        
        table +=           `</ul></td>`
  
        if (!derm.radiUMojoj) {
            table +=    `<td><button type="button" id="Hiredermatolgist-${derm.username}" class="btn btn-primary-custom">&nbsp&nbspHire&nbsp&nbsp</button></td>`;
        } else {
            table +=    `<td></td>`;
        }

        table +=    '</tr>';
    });

    dermatologists.forEach(function(derm) {
        $(document).on('click', '#Hiredermatolgist-'+ derm.username, function() {
            document.location.href = 'dermatologworktime.html?' + derm.username ;
        });

    })

    $("#myTable").append(table);

}
