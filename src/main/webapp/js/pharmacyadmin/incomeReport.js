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
                url:'/phadmin/getincomebymonth',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(months) {
                    fillData(months);
                    refreshToken();
                }
            })
            refreshToken();
        }, error : function() {
            //alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
        }  
    });
}

function fillData(months) {
    var datas = [];
    for (const [key, value] of Object.entries(months)) {
        var obj = {
            "country" : key,
            "value" : value
        };
        datas.push(obj);
    }

    drawGraph(datas);
}
