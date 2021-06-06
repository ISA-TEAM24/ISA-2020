$(document).ready(function() {
    getData();
})

function getData() {
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
                url:'/phadmin/getmypharmacists',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(pharmacists) {
                    fillData(pharmacists);
                }
            })
        }, error : function() {
            alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
        }  
    });
}

function fillData(pharmacists) {
    var datas = [];
    pharmacists.forEach(function(ph) {
        var obj = { "country" : ph.ime + " " + ph.prezime , "value" : ph.ocena };
        datas.push(obj);
    });

    drawGraph(datas);
}
