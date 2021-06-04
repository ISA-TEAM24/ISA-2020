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
                url: '/order/getordersfrompharmacy',
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function(orders) {
                    addOrdersToTable(orders);
                }
            });
        }   
    });
}

function addOrdersToTable(orders) {

    var table = "";
    orders.forEach(function(order) {
        table += `<tr>
                    <td>${order.kreirao}</td>
                    <td>${order.status}</td>
                    <td>${order.rok}</td>
                    <td><ul>`;

        order.lekovi.forEach(function(lek) {
            table += `<li>${lek}</li>`
        })

        table +=       `</ul></td>`;
        if (order.status == "Aktivna") {
            table +=  `<td><button type="button" id="delete-${order.id}" class="btn btn-info-fire">Delete</button></td>
                    <td><button type="button" id="offers-${order.id}" class="btn btn-primary-custom">View offers</button></td>
                    </tr>`;
        } else {
            table += `<td></td>
                      <td></td>
                    </tr>`;
        }
    });

    $("#myTable").append(table);

    orders.forEach(function(order) {
        $(document).on('click', '#delete-'+ order.id, function() {
            $.ajax({
                type: 'DELETE',
                url: '/order/delete/' + order.id,
                contentType : 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
                },
                success : function() {
                    alert("Succesfully deleted order.");
                    window.location.reload();
                }, error : function() {
                    alert("Someone already made an offer, so order can't be deleted");
                }
            });
        });
    });

    orders.forEach(function(order) {
        $(document).on('click', '#offers-'+ order.id, function() {
            document.location.href = "orderoffers.html?id=" + order.id;
        });
    })
}