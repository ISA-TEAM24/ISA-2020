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
            const queryString = window.location.search;
            console.log(queryString);

            if (queryString == "") {
                document.location.href="orderlist.html"
            }

            const urlParams = new URLSearchParams(queryString);
            var id = urlParams.get('id');

            $.ajax({
                type:'GET',
                url: '/order/getorderbyid/' + id,
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(order) {
                    addOrderToTable(order);
                }
            });

            $.ajax({
                type:'GET',
                url: '/offer/getoffersfororder/' + id,
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(offers) {                   
                    addOffersToTable(offers);
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

function addOrderToTable(order) {
    var table1 = "";
    table1 += `<tr>
                    <td>${order.username}</td>
                    <td>${order.rok}</td>
                    <td><ul>`;

    order.lekovi.forEach(function(lek) {
        table1 +=  `<li>${lek.naziv}: ${lek.kolicina}</li>`;
    })
        
    table1 += `</ul></td></tr>`;

    $("#myTable1").append(table1);

}

function addOffersToTable(offers) {
    var table2 = "";
    offers.forEach(function(o) {
        table2 += `<tr>
                    <td>${o.posiljalac}</td>
                    <td><ul>`;

    o.lekovi.forEach(function(lek) {
        table2 +=  `<li>${lek.naziv}: ${lek.kolicina}</li>`;
    })
        
    table2 +=       `</ul></td>
                    <td>${o.ukupnaCena}</td>
                    <td><button type="button" id="accept-${o.id}" class="btn btn-primary-custom">Accept offer</button></td>
                </tr>`;
    })

    $("#myTable2").append(table2);

    offers.forEach(function(o) {

        $(document).on('click', '#accept-'+ o.id, function() {
            if (!o.admin) {
                alert("Only pharmacy admin who created order can pick an offer");
                return;
            }

            if (!o.datumprosao) {
                alert("You can pick an offer after order deadline has passed")
                return;
            }

            if (o.admin && o.datumprosao) {
                $.ajax({
                    type: 'PUT',
                    url: '/offer/accept/' + o.id,
                    contentType : 'application/json',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                    },
                    success : function() {
                        alert("Offer successfuly accepted");
                        document.location.href = "orderslist.html";
                        refreshToken();
                    }
                });
            }
        });
    });

}