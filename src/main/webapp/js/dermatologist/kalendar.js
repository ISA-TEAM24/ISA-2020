datas = [
  {
    title: 'All Day Event',
    start: '2021-05-17',
  },
  {
    title: 'Long Event',
    start: '2021-05-17T10:00:00',
    end: '2021-05-17T16:00:00',
  },
  {
    title: 'Long Event',
    start: '2021-05-17T10:00:00',
    end: '2021-05-17T16:00:00',
    url : 'zapocniPregled.html?Date=1337&Pacijent=1'
  },
  {
    title: 'Pregled - Osoba X - mail@gmail.com',
    start: '2021-05-17T10:00:00',
    end: '2021-05-17T16:00:00',
  
  },
  {
    title: 'Long Event',
    start: '2021-05-17T10:00:00',
    end: '2021-05-17T16:00:00',
  }
]

var obj =   {
  title: 'Conference',
  start: '2021-05-18',
  end: '2021-05-18'
}

var listOfEvents = [];

function fillTheListWithEvents(event) {

}


$(document).ready(function() {
  
  //await sleep(2000);
  //initCalendar(datas);
  //addEventToCalendar(obj);
})

window.addEventListener('load', addEventRenderCalendar(), false);

function addEventRenderCalendar() {
  addEventToCalendar(obj);
  initCalendar(datas)
}

function initCalendar(datas) {
  document.addEventListener('DOMContentLoaded', function() {

    
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
      plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'list' ],
      height: 'parent',
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
      },
      defaultView: 'dayGridMonth',
      navLinks: true, // can click day/week names to navigate views
      editable: true,
      eventLimit: true, // allow "more" link when too many events
      events: datas
    });
 
    calendar.render();
  });


}

function addEventToCalendar(obj) {
  datas.push(obj);
}

// initCalendar(datas);



