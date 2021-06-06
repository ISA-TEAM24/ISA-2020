$(document).ready(function() {
    getData();
})

function getData() {
    console.log("USAO OVDE");
    $.ajax({
        type:'GET',
        url: '/phadmin/whoami',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(phadmin) {
            console.log(phadmin);
            if(phadmin.prvoLogovanje == true) {
                console.log('Prvi put je logovan.')
                window.location.href = 'index.html';
            }
            $.ajax({
                type: 'GET',
                url: '/phadmin/getpharmacy',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(pharmacy) {
                    console.log(phadmin);
                    fillData(pharmacy);
                }, error : function() {
                    console.log('error occured')
                }
            })
        }, error : function() {
            alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
        }  
    });
}

function fillData(pharmacy) {
    var datas = [];
    console.log("USAO I OVDE");
    var obj = { "country" : pharmacy.naziv , "value" : pharmacy.ocena };

    datas.push(obj);
    drawGraph(datas)
}
