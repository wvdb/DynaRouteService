# Mobiscan

## History

This project can be found at https://github.com/wvdb/DynaRouteService.
Desciption to be added.

## Running the project

### Running application locally
TODO:
mvn spring-boot:run
program arguments : --spring.profiles.active=local

### Running on a docker-based RestClient
TODO: add  a docker file hosting the restClient
docker-compose up

### Swagger
http://localhost:8088/dyna-route-service/swagger-ui.html

### Google Distance Matrix API

// https://developers.google.com/maps/documentation/distance-matrix/start
// https://developers.google.com/maps/documentation/distance-matrix/intro

// example of a request:

//        https://maps.googleapis.com/maps/api/distancematrix/json?
//                              origins=Tweebunder%204,+Edegem,+Belgium&
//                              destinations=Oostende,+Belgium&
//                              departure_time=1492675220&
//                              key=AIzaSyDrQxf6ftnF-2xihZBUQkTL6ZEIlgee5WA

// example of a response:

//        {
//            "destination_addresses" : [ "8400 Ostend, Belgium" ],
//            "origin_addresses" : [ "Tweebunder 4, 2650 Edegem, Belgium" ],
//            "rows" : [
//            {
//                "elements" : [
//                {
//                    "distance" : {
//                    "text" : "129 km",
//                            "value" : 128582
//                },
//                    "duration" : {
//                    "text" : "1 hour 17 mins",
//                            "value" : 4629
//                },
//                    "duration_in_traffic" : {
//                    "text" : "1 hour 19 mins",
//                            "value" : 4725
//                },
//                    "status" : "OK"
//                }
//                ]
//            }
//            ],
//            "status" : "OK"
//        }

// epoch : https://www.epochconverter.com/

### Websites of interest (others)
* http://www.objgen.com/json
* https://jsonlint.com/
* https://www.docker.com/products/docker-toolbox
* https://www.websequencediagrams.com/
* https://app.scrumdo.com
* http://www.latlong.net/ : to get latitude and longitude
* https://github.com/OpenFeign/feign : REST client framework
* http://openweathermap.org/forecast5
* http://api.openweathermap.org/data/2.5/forecast?lat=51.1500242&lon=4.4584652&APPID=97fdf5ad61c66373bf9e7c0134e256de
* https://www.latlong.net/
* https://wegenenverkeer.be/carpoolparkings
