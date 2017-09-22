var markOnMapPossible = false;
var currentMarker = null;

function handlePointClick(event) {
    if(markOnMapPossible === true) {
        if (currentMarker != null) {
            currentMarker.setMap(null);
        }
        currentMarker = new google.maps.Marker({
             position:new google.maps.LatLng(event.latLng.lat(), event.latLng.lng())
               });
        currentMarker.setTitle(event.latLng.lat().toString().concat(", ").concat(event.latLng.lng().toString()));
        PF('map').addOverlay(currentMarker);
    }
}

function clickOnMapPossible() {
    markOnMapPossible = true;
}

function clickOnMapNoPossible() {
    markOnMapPossible = false;
    if (currentMarker != null) {
        currentMarker.setMap(null);
    }
    currentMarker = null;
}
