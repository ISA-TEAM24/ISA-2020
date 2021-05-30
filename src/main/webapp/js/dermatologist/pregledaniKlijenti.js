$(document).ready(function () {
    allVisits = [];
    myArray = [];
    getMyFinishedVisits();

})

var myArray = [];
var allVisits = []

function getMyFinishedVisits() {
    $.ajax({
        type:'GET',
        url: '/api/visit/getfinishedvisits',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            console.log(data);
            fillArray(data);
            //buildTable(data);
            fillAllVisits(data);

        },
        error : function() {
            console.log('Cant get finished visits');
        }    
    })
}



function sortTable(data) {
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
                        <td>${data[i].pacijentIme}</td>
                        <td>${data[i].pacijentPrezime}</td>
                        <td>${data[i].datum}</td>
                        <td><button class="btn btn-primary" onclick='openIzvestaj(this.id)' id="` + data[i].pacijentIme + data[i].pacijentPrezime + data[i].datum + `"> Pogledaj izveštaj </button></td>
                    </tr>`

        table.innerHTML += row
    }
}


function fillArray(data) {
    for(var i = 0; i<data.length; i++) {
        var obj = {'pacijentIme' : data[i].pacijentIme,
                    'pacijentPrezime' : data[i].pacijentPrezime,
                    'datum' : data[i].datum};
        myArray.push(obj);
    }

    console.log(myArray);
    buildTable(myArray);
}


function fillAllVisits(data) {
    for(var i = 0; i<data.length; i++) {
        var obj = {'pacijentIme' : data[i].pacijentIme,
                    'pacijentPrezime' : data[i].pacijentPrezime,
                    'datum' : data[i].datum,
                    'vreme' : data[i].vreme,
                    'trajanje' : data[i].trajanje.toString(),
                    'dijagnoza': data[i].dijagnoza};
        allVisits.push(obj);
    }

    //console.log(allVisits);
}

function sortTable2(n,ttt) {

    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    //console.log(ttt);
    table = document.getElementById(ttt);
    switching = true;
    // Set the sorting direction to ascending:
    dir = "asc";
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
      // Start by saying: no switching is done:
      switching = false;
      rows = table.rows;
      /* Loop through all table rows (except the
      first, which contains table headers): */
      for (i = 1; i < (rows.length - 1); i++) {
        // Start by saying there should be no switching:
        shouldSwitch = false;
        /* Get the two elements you want to compare,
        one from current row and one from the next: */
        x = rows[i].getElementsByTagName("TD")[n];
        y = rows[i + 1].getElementsByTagName("TD")[n];
        /* Check if the two rows should switch place,
        based on the direction, asc or desc: */
        if (dir == "asc") {
          if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
            // If so, mark as a switch and break the loop:
            shouldSwitch = true;
            break;
          }
        } else if (dir == "desc") {
          if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
            // If so, mark as a switch and break the loop:
            shouldSwitch = true;
            break;
          }
        }
      }
      if (shouldSwitch) {
        /* If a switch has been marked, make the switch
        and mark that a switch has been done: */
        rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
        switching = true;
        // Each time a switch is done, increase this count by 1:
        switchcount ++;
      } else {
        /* If no switching has been done AND the direction is "asc",
        set the direction to "desc" and run the while loop again. */
        if (switchcount == 0 && dir == "asc") {
          dir = "desc";
          switching = true;
        }
      }
    }
}

function  openIzvestaj(id) {
    console.log(allVisits);
    for(var i = 0; i < allVisits.length; i++ ) {
        var idvisit = allVisits[i].pacijentIme+allVisits[i].pacijentPrezime + allVisits[i].datum;
        //console.log(idvisit);
        //console.log('id is: ' + id);

        if(idvisit == id) {
            var ime = allVisits[i].pacijentIme;
            var prezime = allVisits[i].pacijentPrezime;
            var datum = allVisits[i].datum;
            var vreme = allVisits[i].vreme;
            var trajanje = allVisits[i].trajanje.toString();
            var dijagnoza = allVisits[i].dijagnoza;

            console.log('POKLAPA SE : ' + ime + prezime + datum + vreme + trajanje + dijagnoza);

            fillIzvestaj(ime, prezime, datum, vreme, trajanje, dijagnoza);
        }
    }

}

function fillIzvestaj(ime, prezime, datum, vreme, trajanje, dijagnoza){
    console.log(ime, prezime, dijagnoza);
    document.getElementById("pacijentid").value = '' ;
    document.getElementById("datumid").value = ''; 
    document.getElementById("vremeid").value = '' ;
    document.getElementById("trajanjeid").value = '' ;
    document.getElementById("dijagnozaid").value = '' ;

    $('#pacijentid').attr('readonly','true');
    $('#datumid').attr('readonly','true');
    $('#vremeid').attr('readonly','true');
    $('#trajanjeid').attr('readonly','true');
    $('#dijagnozaid').attr('readonly','true');

    document.getElementById("pacijentid").value = ime + ' ' +  prezime;
    document.getElementById("datumid").value = datum;
    document.getElementById("vremeid").value = vreme;
    document.getElementById("trajanjeid").value = trajanje;
    document.getElementById("dijagnozaid").value = dijagnoza;

    $('#izvestajModal').modal('show'); 
}

