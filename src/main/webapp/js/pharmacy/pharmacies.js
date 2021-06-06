const search_field = $('#generic_filter_input')
const query = window.location.href.split("?")[1]
const grade_select = $('#grade_select')
const derm_select = $('#derm_select')
const farm_select = $('#farm_select')
const pharmacies_body = $('#pharmacies_body')
let pharmacy_list = []



function getAllPharmacies() {
    $.ajax({
        type:'GET',
        url: '/search/open/pharmacies',
        contentType : 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
        },
        success : function(data) {
            pharmacy_list = data
            console.log(pharmacy_list)
            checkQuery()
        },
        error : function() {
            console.log('Could not load pharmacies')
        }
        
    })
}


search_field.keyup(function(){
    filterAll()
})

grade_select.change(function(){ filterAll()})
derm_select.change(function(){ filterAll()})
farm_select.change(function(){ filterAll()})

function displayPharmacies(pharmacies) {

    var html = ""
    
    pharmacies.forEach(function(p){
        //console.log(p)
        var button_td = `<td><button onclick="openPharmacy(this.id)" id="button-${p.id}" class="btn btn-info-reserve">Open</button></td></tr>`
        html += `
            <tr><td>${p.naziv}</td><td>${p.adresa}</td><td>${p.brojDermatologa}</td><td>${p.brojFarmaceuta}</td><td>${p.ocena}</td>`  + button_td
        
    })
    pharmacies_body.html(html)

}



function getGradeFilterValues() {

    var grade_id = grade_select.children(":selected").attr("id");
    
    if (grade_id == "low_grade") {
        return [0, 2]
    }
    else if (grade_id == "medium_grade") {
        return [2, 4]
    }
    else if(grade_id == "high_grade") {
        return [4, 1337]
    }
    else {
        console.log('nothing grade')
    }

    return [0, 1337]

}

function getDermFilterValues() {

    var derm_id = derm_select.children(":selected").attr("id");
    
    if (derm_id == "low_derm") {
        return [-1, 10]
    }
    else if (derm_id == "medium_derm") {
        return [10, 50]
    }
    else if(derm_id == "high_derm") {
        return [50, 1337]
    }
    else {
        console.log('nothing derm')
    }

    return [-1, 1337]
}

function getFarmFilterValues() {

    var farm_id = farm_select.children(":selected").attr("id");
    
    if (farm_id == "low_farm") {
        return [-1, 10]
    }
    else if (farm_id == "medium_farm") {
        return [10, 50]
    }
    else if(farm_id == "high_farm") {
        return [50, 1337]
    }
    else {
        console.log('nothing farm')
    }

    return [-1, 1337]
}

function filterAll() {

    const searchString = search_field.val().toLowerCase();
    var grade_array = getGradeFilterValues()
    var grade_compare_start = grade_array[0]
    var grade_compare_end = grade_array[1]

    var derm_array = getDermFilterValues()
    var derm_compare_start = derm_array[0]
    var derm_compare_end = derm_array[1]

    var farm_array = getFarmFilterValues()
    var farm_compare_start = farm_array[0]
    var farm_compare_end = farm_array[1]
    
    console.log('grade array ' + grade_compare_start + ' se '+ grade_compare_end)
    console.log('derm array ' + derm_array)
    console.log('farm array ' + farm_array)
    //console.log(searchString)
    const filteredPharmacies = pharmacy_list.filter((pharmacy) => {
        var combined = `${pharmacy.naziv.toLowerCase()}, ${pharmacy.adresa.toLowerCase()}`

        return (
            (pharmacy.naziv.toLowerCase().includes(searchString) ||
            pharmacy.adresa.toLowerCase().includes(searchString) || combined.includes(searchString))  &&
            (pharmacy.ocena > grade_compare_start && pharmacy.ocena < grade_compare_end) &&
            (pharmacy.brojDermatologa > derm_compare_start && pharmacy.brojDermatologa < derm_compare_end) &&
            (pharmacy.brojFarmaceuta > farm_compare_start && pharmacy.brojFarmaceuta < farm_compare_end)
            
            );
        });
    console.log(filteredPharmacies)
    displayPharmacies(filteredPharmacies);
}

function checkQuery(){

    if (query != undefined) {
        var text = query.replaceAll("%20"," ")
        search_field.val(text)
        filterAll()
    }
    else {
        console.log('no query')
        filterAll()
    }
}

function resetFilters() {
    //$("#select_id").val("val2").change();
    search_field.val("")
    grade_select.val("grade0").change()
    derm_select.val("derm0").change()
    farm_select.val("farm0").change()
    
}

function openPharmacy(id) {
    var url = "pharmacyprofile.html?id=" + id.split("-")[1]
    window.location.href = url
}

getAllPharmacies();

function sortTableP(n,ttt) {
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
  
  function sortTableNumericP(n, ttt) {
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
