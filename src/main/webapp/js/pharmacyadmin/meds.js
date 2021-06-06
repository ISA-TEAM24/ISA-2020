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
                type: 'GET',
                url: '/phadmin/findmedicineswithprice',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(medicines) {
                    addMedicinesToTable(medicines);
                }
            });
        }, error : function() {
            //alert("Your token has expired. You will be redirected to index page")
            window.location.href = '../index.html';
        }  
    });
}

function addMedicinesToTable(medicines) {

    var table = "";
    medicines.forEach(function(med) {
        table += `<tr>
                    <td>${med.lek}</td>
                    <td>${med.kolicina}</td>
                    <td>${med.cena}</td>
                    <td><button type="button" id="remove-${med.lek}" class="btn btn-info-fire">Remove</button></td>
                  </tr>`;
    })

    $("#myTable").append(table);

    medicines.forEach(function(med) {
        $(document).on('click', '#remove-'+ med.lek, function() {
            $.ajax ({
                type: 'PUT',
                url: '/phadmin/removemedicine/' + med.lek,
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function() {
                    alert("Succesfully removed medicine from pharmacy.");
                    window.location.reload()
                }, error : function() {
                    alert("Users have reserved this medicine. Can't be removed.");
                }
            });
        });
    })
}

function search() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("search");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        } 
    }
}

function addMeds() {
    document.location.href = "allmeds.html"
}

function purcahseOrder() {
    document.location.href = "purchaseorder.html"
}

function priceList() {
    console.log('CAOOO DANILO ')
    document.location.href = "pricelist.html"
}
