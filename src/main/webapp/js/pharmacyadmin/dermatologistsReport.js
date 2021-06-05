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
            if(phadmin.prvoLogovanje == true) {
                console.log('Prvi put je logovan.')
                window.location.href = 'index.html';
            }

            $.ajax({
                type:'GET',
                url:'/phadmin/getmydermatologists',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(dermatologists) {
                    fillData(dermatologists);
                }
            })
        }, error : function() {
            alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
        }  
    });
}

function fillData(dermatologists) {
    var datas = [];
    dermatologists.forEach(function(derm) {
        var obj = { "country" : derm.ime + " " + derm.prezime , "value" : derm.ocena };
        datas.push(obj);
    });

    drawGraph(datas)
}