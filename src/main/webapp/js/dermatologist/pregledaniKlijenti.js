// SEARCH
// $("imeId").val();
// $("#prezimeId").val();

$(document).ready(function () {

    $("#searchbtn").click(function() {
        // alert('clicked search btn');
        findClient();
    });

    sortTable();

})

var myArray = [

    {'ime': 'Ana', 'prezime': 'Anic', 'datum':'13/05/2021'},
    {'ime': 'Pero', 'prezime': 'Peric', 'datum':'09/05/2021'},
    {'ime': 'Laki', 'prezime': 'Luk', 'datum':'15/05/2021'},
    {'ime': 'Imre', 'prezime': 'Imic', 'datum':'13/05/2021'},
    {'ime': 'Rudi', 'prezime': 'Rudolf', 'datum':'12/05/2021'},
    {'ime': 'Jovo', 'prezime': 'Jovic', 'datum':'01/05/2021'},
]

function findClient() {

}

function sortTable() {
    $('th').on('click' , function(){

        var column = $(this).data('column')
        var order = $(this).data('order')

        var text = $(this).html()

        console.log('=====================' + text + 'len: ' + text.length)

        text = text.substring(0, text.length - 1)

        console.log('======after substr ===============' + text)

        console.log('kolona clicked', column, order)

        if(order == 'desc'){
            $(this).data('order', "asc")
            myArray = myArray.sort((a,b) => a[column] > b[column] ? 1 : -1)

            text += '&#9660'


        } else{
            $(this).data('order', "desc")
            myArray = myArray.sort((a,b) => a[column] < b[column] ? 1 : -1)

            text += '&#9650'

        }

        $(this).html(text)
        buildTable(myArray);

    })

    buildTable(myArray);
}


function buildTable(data){
    var table = document.getElementById('tableBody')
    table.innerHTML = ''
    for(var i = 0; i < data.length; i++ ) {
        var row = `<tr>
                        <td>${data[i].ime}</td>
                        <td>${data[i].prezime}</td>
                        <td>${data[i].datum}</td>
                    </tr>`

        table.innerHTML += row
    }
}
