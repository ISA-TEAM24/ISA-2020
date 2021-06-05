var items = new Array();
var list = new Array();
map = {}

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
                url: '/phadmin/getpricelist',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(priceList) {
                    list = priceList;
                    addPriceList(priceList);
                }
            });
        }   
    });
}

function addPriceList(priceList) {
    var html = ""
    priceList.forEach(function(item) {
        html += `<div class="row">
                    <div class="col-3"></div>
                    <div class="col-3 text-center" id="inputfield">
                        <div class="form-group">
                            <input type="text" disabled id="name-${item.naziv}" class="form-control" value="${item.naziv}">
                        </div>
                    </div>
                    <div class="col-3 text-center" id="inputfield">
                        <div class="form-group">
                            <input type="number" id="price-${item.naziv}" class="form-control" value="${item.cena}">
                        </div>
                    </div>
                    <div class="col-3"></div>
                </div>
                <br>`;
    });
    $("#addHere").append(html);

}


function updatepricelist() {

    var medName = "";
    var medAmount = "";
        
    list.forEach(function(item) {
        medName = $("#name-" + item.naziv).val();
        medAmount = $("#price-" + item.naziv).val();
        if (medAmount < 0) {
            alert("Cena ne sme biti manja od 0");
            return;
        }
        map[medName] = medAmount;
     
    });

    console.log(map);
    items = [];

    for (const [key, value] of Object.entries(map)) {
        var obj = {
            naziv : key,
            cena : parseInt(value)
        };
        items.push(obj);
    }

    var pricelist = {
        stavke : items
    }

    $.ajax({
        type : 'PUT',
        url : '/phadmin/updatepricelist',  
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : JSON.stringify(pricelist),                
        success : function() {   
            location.reload();
        },
        error : function() {
            alert("ERRROR");
        }
    });

    
}
