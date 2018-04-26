 
 /* Google Maps
        ------------------------------------------------*/
         


        $(document).ready(function(){
            $("#btnpubmapshow").hide();
            $("#btnfrdmapshow").hide();

            getServerData("ws/index/khawla",initMap);

        $("#btnmymap").click(function(){
            $("#btnpubmapshow").hide();
            $("#btnfrdmapshow").hide();
            $("#btnmymapshow").show();
            
        });
        $("#btnpubmap").click(function(){
            $("#btnmymapshow").hide();
            $("#btnfrdmapshow").hide();
            $("#btnpubmapshow").show();
            
        });
        $("#btnfrdmap").click(function(){
            $("#btnpubmapshow").hide();
            $("#btnmymapshow").hide();
            $("#btnfrdmapshow").show();
            
        });

       
    });

      var map;
      var markers = [];
      var markerImage = 'marker.png';
      
      var infowindows =[];
      var detail =[]; 

      var type=0;

      $(function(){

        $("#btnmymap").click(function(){
            type=0;
           getServerData("ws/index/khawla",initMap); 
        });
        
        $("#setNewmap").click(function(){

           getServerData("/ws/index/khawla/addMapOrPlace",callDone); 
        });
        $("#updateOldMap").click(function(){
           getServerData("ws/index/khawla",initMap);
        });

        $("#btnadd").click(function(){
           getServerData("ws/index/khawla/listname",setNewMap); 
        });

        $("#btnupdate").click(function(){
            type=1;
        });
        $("#btndelete").click(function(){
           deleteMarker();
        });
         


        });


      function callDone(result){

        var sr=JSON.stringify(result);

        console.log(sr);
      }
      
      var dlt=-1;

      var obj= [];

      function initMap(result) {

       var paris={lat: 48.866667, lng: 2.333333};

        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
             paris = {lat: position.coords.latitude,
                       lng: position.coords.longitude};
            map.setCenter(paris);
            addMarker1(paris);
          }, function() {
            handleLocationError(true,map.getCenter());
          });
        } else {
           handleLocationError(false,map.getCenter());
        }    

        map = new google.maps.Map(document.getElementById('google-map'), {
          center: paris,
          zoom: 12,
          panControl: false,
          scrollwheel: false,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        });7

         

            display(result);
           
        
        
        var geocoder = new google.maps.Geocoder();

        document.getElementById('submit').addEventListener('click', function() {
          geocodeAddress(geocoder, map);
        });



      }

      

      function display(result){
         var ji=0;
          for(var j in result){
          var ll=result[j].location;
            for(var i in ll)
            {
              obj[ji] = {
                  name: result[j].name,
                  place: ll[i].place,
                  msg: ll[i].msg,
                  visibilite:ll[i].visibilite,
                  filename:ll[i].filename,
              };

              var d_template = _.template($('#location_window_info').html());
              var d = d_template(obj[ji]);

              /* var d_template = _.template($('#updateMMap').html());
              var detail = d_template(obj[ji]);*/

              var Pos={lat: ll[i].lat, lng: ll[i].lng};
              addMarker(Pos);
              addInfoWindow(markers[ji], d);
              ji++;
              
            }
        }
      }

      function update(){

        console.log("blabla");
          for(var i=0;i<obj.length;i++){
            var d_template = _.template($('#updateMMap').html());
            var detail = d_template(obj[i]);
            addInfoWindow(markers[i], detail);
          }
      }

      function deleteMarker() {
         //test
        markers[0].addListener('click', function () {
            markers[0].setMap(null);
        });
      }


      function addInfoWindow(marker, message) {

            var infoWindow = new google.maps.InfoWindow({
                content: message
            });

            google.maps.event.addListener(marker, 'click', function () {
              infoWindow.open(map, marker);
              
            });
        }
      

      // Adds a marker to the map and push to the array.
      function addMarker(location) {
        var marker = new google.maps.Marker({
                position: location,
                map: map,
                icon: markerImage
               // title: "Vous êtes ici"
            });
     
        markers.push(marker);
      }
      function addInfoW(detail){

        var infowindow = new google.maps.InfoWindow({
            content: detail
        });
        infowindows.push(infowindow);
      }
      // Adds a marker to the map and push to the array.
      function addMarker1(location) {
        var marker = new google.maps.Marker({
                position: location,
                map: map
            });
      }

       function handleLocationError(browserHasGeolocation, pos) {
        addMarker1(pos);
       
      }

     

      function setNewMap(result){

        var det="";

        for (var i = 0; i < result.length-1; i++) {
                    det+='<option value="">'+result[i]+'</option>';
                    }
        map.addListener('click', function(event) {
            var myLatLng = event.latLng;
           
          addMarker(myLatLng);
        
          var obj = {
              d: det,
              lat:event.latLng.lat(),
              lng:event.latLng.lng(),
          };
          var d_template = _.template($('#setMMap').html());
          var detail = d_template(obj); 

                var infowindow = new google.maps.InfoWindow({
                    content: detail
                });
        
            infowindow.open(map, markers[parseInt(result[result.length-1] )] );

         });      
         console.log(parseInt( result[result.length-1] )); 

        
        
      }


    
    function change_valeur() {
        select = document.getElementById("smap");
        choice = select.selectedIndex;
        texte = select.options[choice].text;
        document.getElementById('sname').value = texte;
    }


      

 
      

        function geocodeAddress(geocoder, resultsMap) {
        var address = document.getElementById('address').value;
        geocoder.geocode({'address': address}, function(results, status) {
          if (status === 'OK') {
            resultsMap.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
              map: resultsMap,
              position: results[0].geometry.location
            });
          } else {
            alert('Geocode was not successful for the following reason: ' + status);
          }
        });
      }
      
 