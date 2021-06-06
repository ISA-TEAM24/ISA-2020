var counter = 0;
var openItems = false;
var shouldRender = true;
var name = "";
var amount = "";
var reqs = new Array();
map = {}

$(document).ready(function() {
    getMe();

    addClickListener($('#addItem'));
    $('#table').hide();

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
        }, error : function() {
            //alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
        }  
    })
}

function createPO() {

    var a = $('#closingDate').val();
    var table = $('#table');
    var medName = "";
    var medAmount = "";
    table.find('tr').each(function (i, el) {
        var $tds = $(this).find('td'),
            medName = $tds.eq(0).text(),
            medAmount = $tds.eq(1).text();               
        if (medName != "") 
            map[medName] = medAmount;    
    });      
    for (const [key, value] of Object.entries(map)) {
        var obj = {
            naziv : key,
            kolicina : parseInt(value)
        };
        reqs.push(obj);
    }
    console.log(reqs);

    var order = {
        rok : a,
        lekovi : reqs
    };

    console.log(order);
    var o = JSON.stringify(order);
    console.log(o);

    $.ajax({
        type : 'POST',
        url : '/order/create',  
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        data : o,                 
        success : function() {   
            alert("Successfuly created purchase order.");
            document.location.href = "orderslist.html";
        },
        error : function() {
            alert("You can't create empty purchase order.");
        }
    });
}


function addClickListener(addItemButton) {

    addItemButton.click(function() {

        renderItemHTML();
        counter += 1;
    });

}

function renderItemHTML() {

    if (openItems)
        return;
    
    openItems = true;

    if (shouldRender) {

        var divvy = $('#dynamicDiv');
        divvy.empty();
        var amountId = "amount" + counter;
        var medicineNameId = "medicineName" + counter;
        var addItemButtonId = "addItemButton" + counter;
        var divId = "div" + counter;    
        var myhtml = '';
        myhtml = '<div id="' + divId + '">'
        myhtml += '<br><br><div class="row"><div class="col-2"></div><div class="col-3" id="labels">Medicine name</div><div class="col-5" id="inputfield"><div class="form-group">';
        myhtml += '<input type="text" class="form-control" id="' + medicineNameId + '"></div></div></div><br><div class="row"><div class="col-2"></div><div class="col-3" id="labels">Amount';
        myhtml += '</div><div class="col-5" id="inputfield"><div class="form-group"><input type="number" class="form-control" id="' + amountId + '"></div></div></div><br><br></div><div class="text-center"><button id="' + addItemButtonId + '" class="btn-primary-custom btn"> Add Item </button></div></div></div>';
        myhtml += '</div> <br>';

        divvy.append(myhtml);
    }

    
    $('#' + addItemButtonId).click(function(){

        name = $('#' + medicineNameId).val();
        amount = $('#' + amountId).val();

        if (!validateFields()) {
            shouldRender = false;
            openItems = false;
            return;
        }

        $.ajax({
            type: 'PUT',
            url: '/phadmin/checkmedicine/' + name,
            contentType : 'application/json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
            },
            success : function() {
                alert("Succesfully added medicine.");
                var buttonId = 'button-' + counter;
                var rowId = 'row' + counter;
                var rowwy = $('#tbodyId');
                rowwyHTML = "";
                rowwyHTML +='<tr id="'+ rowId +'">';
                rowwyHTML += "<td>";
                rowwyHTML += name;
                rowwyHTML += "</td>";
                rowwyHTML += "<td>";
                rowwyHTML += amount;
                rowwyHTML += "</td>";
                rowwyHTML += "<td>";
                rowwyHTML += '<button class="btn btn-primary-custom" id="' + buttonId + '">Remove</button>';
                rowwyHTML += "</td>";
                rowwyHTML +="</tr>";
                rowwy.append(rowwyHTML);

                $('#' + buttonId).click(function(){

                    var splitty = this.id.split('-');
                    $('#' + 'row' + splitty[1]).remove();

                });
                
                map[name] = amount;
                $('#table').show();
                $('#' + divId).hide();
                $('#' + addItemButtonId).hide();
                openItems = false;
                shouldRender = true;

            }, error : function() {
                alert("That medicine doesn't exist in system.")
                shouldRender = false;
                openItems = false;
                return;
            }
        })        
    });
};


function validateFields() {

    if (name == "") return false;
    if (amount == "") return false;
    if (amount < 1) return false;
    return true;
}