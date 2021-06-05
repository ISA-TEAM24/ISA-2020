$(document).ready(function(){
    addListenerToSaveButton();
});

function addListenerToSaveButton() {
  
    var x = $('#history_dermatologist_table');
    var y = $('#history_pharmacist_table');
    x.hide();
    $('#history_save').click(function(){
        //history_dermatologist_table
        console.log('hello');
        
        
        if (x.is(":visible")) {
            x.hide();
            y.show();
            //console.log('if');
            $('#history_save').html("Show exams");
        }
        else {
            //console.log('else');
            y.hide();
            x.show();
            $('#history_save').html("Show consultations");
        }
    });   
}

function loadPastVisits() {

    $.ajax({
        type:'GET',
        url: '/api/user/visits',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            fillVisitTables(data)
        },
        error : function() {
            console.log('An Error has occured while trying to reload the history')
            showError('Error message', 'Could not load history of visits.')
        }
        
    })
}

function fillVisitTables(data){

    var fb = $('#farm_body_id')
    var db = $('#derm_body_id')

    fb.html('')
    db.html('')

    var fb_html = ''
    var db_html = ''
    //zap, cena, trajanje, datum
    data.forEach(function(v){
      var date = new Date(v.datum)
      if (v.vrsta == 'SAVETOVANJE'){
        fb_html += `<tr><td>dr ${v.zaposleni.ime} ${v.zaposleni.prezime} </td><td>${v.apoteka.cenovnik['SAVETOVANJE']}</td><td>${v.trajanje}</td><td>${date.toLocaleDateString('fr-CA')}</td></tr>`
      }
      else {
        db_html += `<tr><td>dr ${v.zaposleni.ime} ${v.zaposleni.prezime} </td><td>${v.apoteka.cenovnik['PREGLED']}</td><td>${v.trajanje}</td><td>${date.toLocaleDateString('fr-CA')}</td></tr>`
      }
      
    })

    fb.html(fb_html)
    db.html(db_html)

}

function sortTable(n,ttt) {
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
  
  function sortTableNumeric(n, ttt) {
    console.log('sorting numerically')
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    
    table = document.getElementById(ttt);
    switching = true;
    dir = "asc";
    while (switching) {
        switching = false;
        rows = table.getElementsByTagName("TR");
        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
                    var cmpX=isNaN(parseInt(x.innerHTML))?x.innerHTML.toLowerCase():parseInt(x.innerHTML);
                    var cmpY=isNaN(parseInt(y.innerHTML))?y.innerHTML.toLowerCase():parseInt(y.innerHTML);
    cmpX=(cmpX=='-')?0:cmpX;
    cmpY=(cmpY=='-')?0:cmpY;
            if (dir == "asc") {
                if (cmpX > cmpY) {
                    shouldSwitch= true;
                    break;
                }
            } else if (dir == "desc") {
                if (cmpX < cmpY) {
                    shouldSwitch= true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            switchcount ++;      
        } else {
            if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
    }  
