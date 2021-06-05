datas = [];

$(document).ready(function() {
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

    getUpcomingVisits();
    getPastVisits();
    
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


function getUpcomingVisits() {
  $.ajax({
      type:'GET',
      url: '/api/visit/getupcomings',
      contentType : 'application/json',
      beforeSend: function (xhr) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
      },
      success : function(data) {
          console.log(data)
          addUpcomingVisitsToCalendar(data);
          refreshToken();

      },
      error : function() {
          console.log('Cant get upcoming visits');
          alert("Istekao vam je token. Ulogujte se ponovo.")
          window.location.href = '../index.html';
      }    
  })
}

function addUpcomingVisitsToCalendar(data) {
    data.forEach(function(visit) {

      var startTime = visit.datum + "T" + visit.vreme + ":00";

      var startDate = new Date(startTime);
      console.log(startDate);
      
      console.log(visit.trajanje.toString());
      var endTime = new Date(startDate.setMinutes(startDate.getMinutes() + visit.trajanje ));
      
      console.log(endTime);

      var origin = window.location.origin;
      console.log(origin + "/dermatologist/aktivanPregled.html");
      var visitUrl = origin + "/dermatologist/aktivanPregled.html?" + parseInt(visit.id);
      
      var event = {
        title:  visit.ime + " " + visit.prezime + " - " + visit.apoteka,
        start: startTime,
        end : endTime,
        url : visitUrl,
        color: '#37c5cc'
      } 

      datas.push(event);
    })

}

function getPastVisits() {
  $.ajax({
    type:'GET',
    url: '/api/visit/getfinishedvisits',
    contentType : 'application/json',
    beforeSend: function (xhr) {
        xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('myToken'));
    },
    success : function(data) {
        console.log(data);
        addPastVisitsToCalendar(data);
        refreshToken();
    },
    error : function() {
        console.log('Cant get finished visits');
        alert("Istekao vam je token. Ulogujte se ponovo.")
        window.location.href = '../index.html';
    }    
  })
}

function addPastVisitsToCalendar(data) {
    data.forEach(function(visit) {

      var startTime = visit.datum + "T" + visit.vreme + ":00";

      var startDate = new Date(startTime);
      console.log(startDate);
      
      console.log(visit.trajanje.toString());
      var endTime = new Date(startDate.setMinutes(startDate.getMinutes() + visit.trajanje ));
      
      console.log(endTime);

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
  })
}





