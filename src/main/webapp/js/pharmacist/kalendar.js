var datas = [];

$(document).ready(function() {

})
  
  window.addEventListener('load', addEventRenderCalendar(), false);
  
  function addEventRenderCalendar() {
    //addEventToCalendar(obj);
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
  
  
  
  