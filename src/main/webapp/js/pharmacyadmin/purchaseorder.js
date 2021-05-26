var counter = 0;
var openItems = false;
var shouldRender = true;
var name = "";
var amount = "";
var reqs = new Array();
map = {}

$(document).ready(function() {

    addClickListener($('#addItem'));
    $('#table').hide();

})

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
        //var addItemId = "addItem" + counter;
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
            ('Invalid field value');
            shouldRender = false;
            openItems = false;
            return;
        }
        
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
        
        //map[name] = amount;
        console.log('herere')
        $('#table').show();
        $('#' + divId).hide();
        $('#' + addItemButtonId).hide();
        openItems = false;
        shouldRender = true;
    });
};


function validateFields() {

    if (name == "") return false;
    if (amount == "") return false;
    if (amount < 1) return false;
    return true;
}