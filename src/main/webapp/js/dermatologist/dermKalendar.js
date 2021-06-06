var deo;
var datas = [];
var myPharmacies = [];
var apoteka;

$(document).ready(function() {

    myPharmacies = [];
    getMyPharmacies();

    if(deo == undefined) {
        console.log('deo je undefined')
    }

    refreshToken();

})

// on window load popuni kalendar
window.addEventListener('load', addEventRenderCalendar(), false);

function addEventRenderCalendar() {
  datas = [];
  console.log('PRE UPISA STANJE DATA: ');
  console.log(datas);

  initCalendar(datas);

}

function initCalendar(datas) {

  console.log('INIT CALENDAR');

  document.addEventListener('DOMContentLoaded', function() {

    url = window.location.href;
    deo = url.split("?")[1];

    if(deo != undefined) {
       getAllVisits(deo); 
    }
    
    var millisecondsToWait = 200;
    setTimeout(function() {

      var calendarEl = document.getElementById('calendar');
      var calendar = new FullCalendar.Calendar(calendarEl, {
        plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'list' ],
        height: 'parent',
        header: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
        },
        defaultView: 'listMonth',
        navLinks: true, // can click day/week names to navigate views
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        events: datas
        });

      calendar.render();
  
    }, millisecondsToWait);

    console.log('POSLE RENDEROVANJE: ');
    console.log(datas);
  });

}


function getMyPharmacies() {
    $.ajax({
      type:'GET',
      url: '/dermatologist/getmypharmacies',
      beforeSend: function (xhr) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
      },
      success : function(data) {
          console.log(data);
          console.log(data[0].naziv);
          fillMyPharmacies(data);
      },
      error : function() {
          console.log('Cant get my pharmacies');
          window.location.href = '../index.html';
      }    
  })

}

function fillMyPharmacies(data) {

    $('#apotekeId').html('');
    $("#apotekeId").html(`<option id="option-none"> </option>`);
    data.forEach(function(f) {
      myPharmacies.push(f.id);
      $('#apotekeId').append('<option value="' + f.naziv + '" id="option-' + f.id + '">' + f.naziv + '</option>');
    })

    console.log(myPharmacies);
    var counter = 1;
    var index;
    myPharmacies.forEach(function(pharmacyId) {
        if(pharmacyId == deo) {
          index = counter;
        }
        else {
          counter = counter + 1;
        }
    })
    
     $("#apotekeId").prop('selectedIndex', index);

    console.log(myPharmacies);
}

$("#apotekeId").change(function() {

  if($("#apotekeId").children(":selected").attr("id") == 'option-none') {
    return;
  }

  var xurl = window.location.href;
  var neededpart = xurl.split("?")[0];
  console.log(neededpart);

  var selectedId = $("#apotekeId").children(":selected").attr("id");
  var mojId = selectedId.split("-")[1];

  console.log('MOJ IDDD JE : ' + mojId);

  window.location.href = neededpart + "?" + mojId;

})

function getAllVisits(id) {
  $.ajax({
    type:'GET',
    url: '/api/dermatologist/visits/all/' + id,
    contentType : 'application/json',
    beforeSend: function (xhr) {
        xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
    },
    success : function(data) {
        addVisitsToCal(data);
        console.log(data);
    },
    error : function() {
        console.log('Cant get upcoming visits');
        //alert("Istekao vam je token. Ulogujte se ponovo.")
        window.location.href = '../index.html';
    }    
})
}

function addVisitsToCal(data) {
  data.forEach(function(visit) {

    if(visit.aktivan == true) {
        var startTime = visit.datum + "T" + visit.vreme + ":00";

        var startDate = new Date(startTime);

        var endTime = new Date(startDate.setMinutes(startDate.getMinutes() + visit.trajanje ));
        
        var origin = window.location.origin;
        var visitUrl = origin + "/dermatologist/aktivanPregled.html?" + visit.posetaId;
        
        var event = {
          title:  visit.pacijentIme + " " + visit.pacijentPrezime + " - " + visit.apoteka,
          start: startTime,
          end : endTime,
          url : visitUrl,
          color: '#37c5cc'
        } 
    
        datas.push(event);

    } else {
        var startTime = visit.datum + "T" + visit.vreme + ":00";

        var startDate = new Date(startTime);
        
        var endTime = new Date(startDate.setMinutes(startDate.getMinutes() + visit.trajanje ));
        
        var origin = window.location.origin;
        var visitUrl = origin + "/dermatologist/pregledaniKlijenti.html";
        
        var event = {
          title:  visit.pacijentIme + " " + visit.pacijentPrezime + " - " + visit.apoteka,
          start: startTime,
          end : endTime,
          url : visitUrl,
          color: '#eb8c21'
        } 
    
        datas.push(event);
    }

})
}





