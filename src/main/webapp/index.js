 /* Google Maps
        ------------------------------------------------*/
         var x=0;

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

      

      $(function(){
        $("#btnmymap").click(function(){
           getServerData("ws/index/khawla",initMap); 
        });
        
         $("#setNewmap").click(function(){
           getServerData("/ws/index/khawla/addMapOrPlace",callDone); 
        });

        $("#btnadd").click(function(){
           getServerData("ws/index/khawla/listname",setNewMap); 
        });

        $("#btnupdate").click(function(){
           getServerData("ws/index/khawla",updateMap); 
        });


        });


      function callDone(result){
   
       
        var  sr=200;

        sr=JSON.stringify(result);

        console.log(sr);
    
    //$("#result").append(sr);

        if(sr == "200"){
            window.location = "http://localhost:8088/index.html";
            
        }else{
             markers[markers.length-1].setMap(null);  
        }
    }
      

      function initMap(result) {

        var paris={lat: 48.866667, lng: 2.333333};

        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
             paris = {
              lat: position.coords.latitude,
              lng: position.coords.longitude
            };

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
        });

        var ji=0;
        console.log(result.length);
        for(var j in result){

        console.log(result[j].name);
        console.log(result[j].creator.login);
        console.log(result[j].creator.password);
        console.log(result[j].visibilite);

        var ll=result[j].location;
        console.log(ll.length);
        
        
        for(var i in ll)
        {

          var lt=ll[i].lat;
          var lg=ll[i].lng;
             var d='<div id="detail" class="info-window">' +
                     '<div class="info-content">' +
                    '<h2>'+result[j].name+'</h2>'+
                    '<h3>'+ll[i].place+'</h3>'+
                    '<p>'+ll[i].msg+'</p>'+
                    '<div class="tm-slideshow">'+
                    '<img src="'+ll[i].filename+'" alt="Image">'+ 
                    '</div>'+
                    '</div>'+
                    '</div>';

          
     
     //PB!!! il faut affecte a chaque marker son infowindow     
    
         
           // addInfoW(d);
            var lt=ll[i].lat;
            var lg=ll[i].lng;

            console.log(lt);
            console.log(ll[i].filename);

            var Pos={lat: lt, lng: lg};
            addMarker(Pos);
            addInfoWindow(markers[ji], d);
            /*
            markers[ji].addListener('click', function () {
            infowindows[ji].open(map, markers[ji]);
            });*/
            ji++;
            
        }
    }


        var geocoder = new google.maps.Geocoder();

        document.getElementById('submit').addEventListener('click', function() {
          geocodeAddress(geocoder, map);
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

        var d="";

        for (var i = 0; i < result.length-1; i++) {
                    d+='<option value="">'+result[i]+'</option>';
                    }
        map.addListener('click', function(event) {
            var myLatLng = event.latLng;
           
          addMarker(myLatLng);
        
           var detail1 = '<div class="info-window">' +
                '<div class="info-content">' +
                
                '<form action="ws/index/khawla/addMapOrPlace" method="POST">'+
                '<label>My map </label>'+
                    '<select name="map" id="smap" onchange="change_valeur();">'+
                    '<option value=""></option>'
                    +d+
                    '</select><br/>'+
                    '<label>map\'s name</label>'+
                      '<input type="text" name="namemap" id="sname" ><br>'+
                      '<label>tag</label>'+
                      '<input type="text" name="tag"><br>'+
                      '<label>place name</label>'+
                      '<input type="text" name="place" ><br>'+
                      '<input type="text" name="lat"  value="'+event.latLng.lat()+'">'+
                      '<input type="text" name="lng"  value="'+event.latLng.lng()+'"><br/>'+
                      '<label>description </label>'+
                      '<textarea name="msg" rows="2" cols="30"></textarea></br>'+
                      '<label>upload picture</label>'+
                      '<input type="file" id=""></br>'+
                      '<select name="visib">'+
                    '<option value="public">Public</option>'+
                    '<option value="friend">Friend</option>'+
                    '<option value="private">Private</option>'+
                    '</select><br/>'+
                      '<button type="submit" class="btn btn-primary tm-btn-search " id="setNewmap">Submit</button>'+
                    '</form>'+ 
                '</div>' +
                '</div>';

                var infowindow = new google.maps.InfoWindow({
                    content: detail1,

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

/*
      function updateMap(result){



       
            for( var i in EX )
            {
                a++;
                var LatLng ={ lat : EX[i].lt ,lng : EX[i].lg};
                if(_.isEqual(myLatLng, LatLng)){

                var detail1 = '<div class="info-window">' +
                    '<div class="info-content">' +
                    
                    '<form>'+
                    '<label>My map </label>'+
                        '<select name="map">'+
                        '<option value="paris">'+EX[i].name+'</option>'+
                        '</select><br/>'+
                        '<label>map\'s name</label>'+
                          '<input type="text" name=""  value="'+EX[i].name+'"><br>'+
                          '<label>tag</label>'+
                          '<input type="text" name="" ><br>'+
                          '<label>place name</label>'+
                          '<input type="text" name="" value="'+EX[i].place+'"><br>'+
                          '<label>description </label>'+
                          '<textarea rows="2" cols="30">'+EX[i].msg+'</textarea></br>'+
                          '<label>upload picture</label>'+
                          '<input type="file" id="myFile" vaue="'+EX[i].img+'"></br>'+
                          '<input type="file" id="visibilite" vaue="'+EX[i].visibilite+'"></br>'+
                          '<select name="">'+
                        '<option value="">Public</option>'+
                          '<option value="">Friend</option>'+
                        '<option value="">Private</option>'+
                        '</select><br/>'+
                          '<input type="submit" value="Submit">'+
                         
                        '</form>'+ 
                    '</div>' +
                    '</div>';

                var infowindow = new google.maps.InfoWindow({
                    content: detail1,
                });

                // i et la position exact !!!
                infowindow.open(map, markers[0]);

                }
                
            }
        

        });


      }*/
 
      function deleteMarker() {
        markers[0].addListener('click', function () {
            markers[0].setMap(null);
        });
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
      
 