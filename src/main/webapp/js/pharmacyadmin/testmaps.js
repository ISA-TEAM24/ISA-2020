$(document).ready(function() {
    geocode();
})

function geocode() {
    var location = window.localStorage.getItem('address');
    var adresa = { 
        address : location,
        key : 'AIzaSyBkulZXxb-ShHcTXIqWZqnTmvWDxNBWWx8' 
    }

    $.ajax({
        type : 'GET',
        url : 'https://maps.googleapis.com/maps/api/geocode/json',
        data : adresa,
        success : function(response) {
            createCordinates(response);
        }, error : function() {
            console.log("error");
        }
        
    })
}

function createCordinates(response) {
    if (response.results[0] == undefined) {
      alert("Location doesn't exists.")
      window.location.href = history.go(-1);
      return;
    } 
    var latValue = response.results[0].geometry.location.lat;
    var lngValue = response.results[0].geometry.location.lng;

    var obj = {
        lat : latValue,
        lng : lngValue,
    }

    initMap(obj);

}

let map;

function initMap(obj) {
    const myLatLng = obj;
    map = new google.maps.Map(document.getElementById("map"), {
    center: myLatLng,
    zoom: 18,
  });
  marker = new google.maps.Marker({
    position: myLatLng, map,
    title: "Location",
    animation: google.maps.Animation.DROP,
  });
  marker.addListener("click", toggleBounce);
}

function toggleBounce() {
  if (marker.getAnimation() !== null) {
    marker.setAnimation(null);
  } else {
    marker.setAnimation(google.maps.Animation.BOUNCE);
  }
}

