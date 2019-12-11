<!DOCTYPE html>
<html>
<head>
    
    <style>
        
        .searchForm{
            background-color: forestgreen; 
            border-radius: 15px;
            width : 780px;
            height : 300px;
        }
        
        #title
        {
            text-align: center;
            font-style : italic;
            font-size: 40px;
            color:white;
            margin-top:10px;
        }
        
        #Checkbox{
        	float: right;
        	margin-right: 100px;
        	margin-top: -50px;		
        }
        
        
        .leftContent{
            font-size :20px ;       
            color: white;
            top: -10px;
            margin-left: 75px;
        }        
        
        #divider{
            width: 20px;
            height: 400px;
            float: right;
            color: red;
        }
        
        .button{
            border-radius:6px;
            margin-top: 30px;
            display: inline-block;
        }

        #displayCard, #dailyWeather{
            margin-top : 20px;            
            margin-left : 400px;
            width: 400px;
            height: 300px;
            padding : 10px;  
            border-radius : 20px;  
        }  

        #dailyWeather{
            height: 500px;
        }

        table{
            background-color : dodgerblue; 
            border-color : royalblue; 
            color : white; 
            text-align : center; 
            border-style : solid; 
            margin-left : 350px; 
            margin-top : 20px; 
        }

        #subtitle{
            margin-left : 240px; 
        }

        #hourlyChart{
            margin-top : 60px;
            margin-left : 600px; 
        }        

        .images{
            padding : 15px; 
        }

        .number{
            margin-left : 15px;
            margin-top : -5px;
        }

    </style>

    <script type="text/javascript">
        function postvalues()        
        {
                
                var street = document.getElementById("street").value; 
                var city = document.getElementById("city").value;
                var state = document.getElementById("state").value;        		
                
            if(!street || !city || !state)
            {
                var alert = document.createElement("p"); 
                var content = document.createTextNode("Please check the input address");
                alert.appendChild(content);
                var currentElement = document.getElementById("searchForm"); 
                currentElement.parentNode.insertBefore(alert, currentElement.nextSibling); //displaying the error message after the form                
            }//end of if 

        } // checking if the values are valid and not empty  
        
        document.getElementById('Checkbox').onclick = function(){

            var form_element = document.getElementsByClassName("leftContent"); 
            for(var i=0;i<form_element.length;i++){
                form_element[i].disabled = true; 
            }//end of the for loop to disable the text fields
            
            var xhttp = new XMLHttpRequest();
            xhttp.open('GET','http://ip-api.com/json',false); //calling the IP-API to pick the current location and parse the data
            xhttp.send(); 
            currentLocationDetails = JSON.parse(xhttp.responseText); 
            var currentLat = currentLocationDetails.lat; 
            var currentLon = currentLocationDetails.lon;  
            var d = new Date(); //Create an date object
            d.setTime(d.getTime() + (1000*100));
            var expires = "expires=" + d.toGMTString();
            document.cookie = "latitude=" + currentLat +";"+expires; 
            docunment.cookie = "longitude=" + currentLon +";"+expires; 

            <?php
                $geoLat = $_COOKIE['latitude']; 
                $geoLon = $_COOKIE['longitude']; 
                $forecastURL = "https://api.darksky.net/forecast/c40387792d1f698483de9ca1f3be72fb/".$geoLat.",".$geoLon."?exclude=minutely,hourly,alerts,flags";
                $weatherJSON = json_decode(file_get_contents($forecastURL),true);  
            ?> 
        }//checkEnabled function
    </script>

</head>
    
<body>
    
    
    <div class="searchForm" style="margin-left:250px;margin-top:40px";>
        <form name="locationSearch" method="POST" action="<?=$_SERVER['PHP_SELF'];?>" onsubmit="postvalues()"> 
        
        	    <p id="title"> <i> Weather Search </i> </p>  

            <div class="leftContent">
                <!-- tags for collecting the user location details -->                              
                <p> <strong>Street<input id="street" type = "text" name="Street" style="margin-left: 5px;width:150px;height:20px;border-radius:0px;"> </strong></p> <p id="divider"></p> <p id = "Checkbox" style="margin-left: 60px;"><strong><input type = "checkbox" name="Location">Current Location</strong></p> 
                <p> <strong>City<input id="city" type = "text" name="City" style="margin-left: 19px;width:150px;height:27px;border-radius:0px;"></strong></p>
                <p> <strong>State
                    <select id="state" name="State" required style="margin-left:2px;width:200px"> 
                        <option selected value = "States">States</option>
                        <option value="AL">Alabama</option>
                        <option value="AK">Alaska</option>
                        <option value="AZ">Arizona</option>
                        <option value="AR">Arkansas</option>
                        <option value="CA">California</option>
                        <option value="CO">Colorado</option>
                        <option value="DE">Delaware</option>
                        <option value="DC">District Of Columbia</option>
                        <option value="FL">Florida</option>
                        <option value="GA">Georgia</option>
                        <option value="HI">Hawaii</option>
                        <option value="ID">Idaho</option>
                        <option value="IL">Illinois</option>
                        <option value="IN">Indiana</option>
                        <option value="IA">Iowa</option>
                        <option value="KS">Kansas</option>
                        <option value="KY">Kentucky</option>
                        <option value="LA">Louisiana</option>
                        <option value="ME">Maine</option>
                        <option value="MD">Maryland</option>
                        <option value="MA">Massachusetts</option>
                        <option value="MI">Michigan</option>
                        <option value="MN">Minnesota</option>
                        <option value="MS">Mississippi</option>
                        <option value="MO">Missouri</option>
                        <option value="MT">Montana</option>
                        <option value="NE">Nebraska</option>
                        <option value="NV">Nevada</option>
                        <option value="NH">New Hampshire</option>
                        <option value="NJ">New Jersey</option>
                        <option value="NM">New Mexico</option>
                        <option value="NY">New York</option>
                        <option value="NC">North Carolina</option>
                        <option value="ND">North Dakota</option>
                        <option value="OH">Ohio</option>
                        <option value="OK">Oklahoma</option>
                        <option value="OR">Oregon</option>
                        <option value="PA">Pennsylvania</option>
                        <option value="RI">Rhode Island</option>
                        <option value="SC">South Carolina</option>
                        <option value="SD">South Dakota</option>
                        <option value="TN">Tennessee</option>
                        <option value="TX">Texas</option>
                        <option value="UT">Utah</option>
                        <option value="VT">Vermont</option>
                        <option value="VA">Virginia</option>
                        <option value="WA">Washington</option>
                        <option value="WV">West Virginia</option>
                        <option value="WI">Wisconsin</option>	
                        <option value="WY">Wyoming</option>
                    </select>
                </strong> </p>
                	

                 <!-- search and clear buttons.. onclick of the search button pass the street, city and the state values-->
                <input class = "button" name="search" type="submit" value="search"  style="margin-left:190px";>
                <input class = "button" name="clear" type="reset" value="clear">
            </div>
        </form>
    </div>

    <div id="displayCard"></div>
    <div id="displayTable"></div>
    <div id="subtitle"></div>
    <div id="dailyWeather"></div>
    <div id="hourlyChart"></div>

    <script type="text/javascript">    

        <?php 
            
            //declaring the latitude and longitude variables to be globally available across all the functions    
            if($_SERVER["REQUEST_METHOD"] == "POST") 
            { 
            $street = $_POST['Street'];
            $city = $_POST['City'];
            $state = $_POST['State'];

            $geocodeURL = "https://maps.googleapis.com/maps/api/geocode/xml?address=".$street.",".$city.",".$state."&key=AIzaSyCH2oPI34OIf0_qlzyL9zTIMfBUxEgZur4";
            $geocode = simplexml_load_file($geocodeURL); 
            
            //parse the geocode xml output and get the latitude and longitude values
            $geoLat = $geocode->result->geometry->location->lat;
            $geoLon = $geocode->result->geometry->location->lng;  

            }//end of if 

            //calling the forecast API and extracting the card and table details     
            $forecastURL = "https://api.darksky.net/forecast/c40387792d1f698483de9ca1f3be72fb/".$geoLat.",".$geoLon."?exclude=minutely,hourly,alerts,flags";
            $weatherJSON = json_decode(file_get_contents($forecastURL),true);  

            //converting the JSON content and storing the same in an array 
            $cardWeather = array(
                'latitude' => $geoLat,
                'longitude' => $geoLon, 
                'timezone' => $weatherJSON["timezone"],
                'temperature' => $weatherJSON["currently"]["temperature"], 
                'summary' => $weatherJSON["currently"]["summary"],
                'humidity' => $weatherJSON["currently"]["humidity"],
                'pressure' => $weatherJSON["currently"]["pressure"],
                'windSpeed' => $weatherJSON["currently"]["windSpeed"], 
                'visibility' => $weatherJSON["currently"]["visibility"],
                'cloudCover' => $weatherJSON["currently"]["cloudCover"],
                'ozone' => $weatherJSON["currently"]["ozone"], 
            ); 

            
            //converting the JSON into array object for storing the table details 
            for($i=0;$i<count($weatherJSON["daily"]["data"]);$i++){

                $tableSummary[$i] = array( 
                    'date' => $weatherJSON["daily"]["data"][$i]["time"],
                    'status' => $weatherJSON["daily"]["data"][$i]["icon"],
                    'summary' => $weatherJSON["daily"]["data"][$i]["summary"],
                    'temperatureHigh' => $weatherJSON["daily"]["data"][$i]["temperatureHigh"],
                    'temperatureLow' => $weatherJSON["daily"]["data"][$i]["temperatureLow"],
                    'windSpeed' => $weatherJSON["daily"]["data"][$i]["windSpeed"], 
                ); //has one tableSummary array content

            }//end of for loop 

        ?>   

        var cardContent=""; 
        var x = document.getElementById("city").value; 
        console.log(x); 
        jsonDoc = JSON.parse(JSON.stringify(<?php echo json_encode($cardWeather); ?>)); 

        if (jsonDoc.timezone != null){

            //storing the value in cardContent to be displayed in the view card             
            cardContent += "<p><font color='white' size='7'><strong>" + x + "</strong></font></p>" +
                           "<p><font size='4' color='white'>" + jsonDoc.timezone + "</font></p>" +
                            "<p><font size ='7' color='white'><strong>" + jsonDoc.temperature  + "</strong></font>" + "<img class='degree' src='https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png' width= '7' height='10' align='top'/><font color='white' size='6'><strong>"+"F"+ "</strong></font><br></p>" +  
                            "<p><font size='6' color='white'><strong>" + jsonDoc.summary + "</strong></font><br></p>" + 
                            "<img class=images src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-16-512.png' alt='alternative text' title='Humidity' width='25px' height='30px'/>" + 
                            "<img class=images src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-25-512.png' alt='alternative text' title='Pressure' width='25px' height='30px'/>" + 
                            "<img class=images src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png' alt='alternative text' title='Wind Speed' width='25px' height='30px'/>" + 
                            "<img class=images src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-30-512.png' alt='alternative text' title='Visibility' width='25px' height='30px'/>" + 
                            "<img class=images src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png' alt='alternative text' title='CloudCover' width='25px' height='30px'/>" + 
                            "<img class=images src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-24-512.png' alt='alternative text' title='Ozone' width='25px' height='30px'/>" + 
                            "<p class=number><font color='white' size='4'><strong>" + jsonDoc.humidity + "&nbsp;&nbsp;&nbsp;&nbsp;" + jsonDoc.pressure + "&nbsp;&nbsp;&nbsp;&nbsp;" + jsonDoc.windSpeed + "&nbsp;&nbsp;&nbsp;&nbsp;" + jsonDoc.visibility + "&nbsp;&nbsp;&nbsp;&nbsp;" + jsonDoc.cloudCover + "&nbsp;&nbsp;&nbsp;&nbsp;" + jsonDoc.ozone + "</strong></p>";
            
            document.getElementById("displayCard").innerHTML = cardContent;
            document.getElementById("displayCard").style.backgroundColor = 'deepskyblue'; 

        }//end of if      
            

        var tableContent=""; 
        tableJSON = JSON.parse(JSON.stringify(<?php echo json_encode($tableSummary); ?>)); 
        tableContent+= "<table border='1px';width = 'auto'; height = 'auto'; text-align = 'center'>"
        tableContent+="<tr><th>" + "Date" + "</th>" + 
                "<th>" + "Status" + "</th>" +
                "<th>" + "Summary" + "</th>" + 
                "<th>" + "TemparatureHigh" + "</th>" +
                "<th>" + "TemparatureLow" + "</th>" +
                "<th>" + "WindSpeed" + "</th></tr>";

        for(i=0;i<tableJSON.length;i++)
        {   

            var date = new Date(tableJSON[i].date); 
            var year = date.getFullYear();
            var month = ("0" + (date.getMonth() + 1)).slice(-2);
            var day = ("0" + date.getDate()).slice(-2);

            tableContent += "<tr><td>" + year+"-"+month+"-"+day + "</td>";
            
            if (tableJSON[i].status == "clear-day" || tableJSON[i].status == "clear-night"){
                tableContent += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-12-512.png' width='30' height='40'></td>"
            }
            
            else if (tableJSON[i].status == "rain"){
                tableContent += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-04-512.png' width='30' height='40'></td>"
            }
            
            else if (tableJSON[i].status == "snow"){
                tableContent += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-19-512.png' width='30' height='40'></td>"
            }
            
            else if (tableJSON[i].status == "sleet"){
                tableContent += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-07-512.png' width='30' height='40'></td>"
            }
                
            else if (tableJSON[i].status == "wind"){
                tableContent += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png' width='30' height='40'></td>"
            }
                
            else if (tableJSON[i].status == "fog"){
                tableContent += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png' width='30' height='40'></td>"
            }
                
            else if (tableJSON[i].status == "cloudy"){
                tableContent += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-01-512.png' width='30' height='40'></td>"
            }
                
            else if (tableJSON[i].status == "partly-cloudy-day") {
                tableContent += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-02-512.png' width='30' height='40'></td>"
            }

            else{
                tableContent += ""
            }
            tableContent += "<td onclick='dailyCard("+ tableJSON[i].date +")'>" + tableJSON[i].summary + "</td>" + 
                            "<td>" + tableJSON[i].temperatureHigh + "</td>" + 
                            "<td>" + tableJSON[i].temperatureLow + "</td>" + 
                            "<td>" + tableJSON[i].windSpeed + "</td>" + "</tr>";
        }//end of for  

        document.getElementById("displayTable").innerHTML = tableContent; 
     
        function dailyCard(time)
        {
            var d = new Date(); //Create an date object
            d.setTime(d.getTime() + (1000*100));
            var expires = "expires=" + d.toGMTString();
            document.cookie = "time=" +time +";"+expires; 

            <?php 
            
            //calling the darksky API for a specific date 
            $time = $_COOKIE['time'];  
            $dailyforecast = json_decode(file_get_contents("https://api.darksky.net/forecast/c40387792d1f698483de9ca1f3be72fb/37.8267,-122.4233,1572552480?exclude=minutely"),true);
            $daily_weather = array(
                'summary' => $dailyforecast["currently"]["summary"],
                'temperature' => $dailyforecast["currently"]["temperature"],
                'icon' => $dailyforecast["currently"]["icon"],
                'precipitation' => $dailyforecast["currently"]["precipIntensity"],
                'rainChance' => $dailyforecast["currently"]["precipProbability"],
                'windspeed' => $dailyforecast["currently"]["windSpeed"],
                'humidity' => $dailyforecast["currently"]["humidity"],
                'visibility'=> $dailyforecast["currently"]["visibility"],
                'sunrise' => $dailyforecast["daily"]["data"][0]["sunriseTime"],
                'sunset' => $dailyforecast["daily"]["data"][0]["sunsetTime"],
            );

            for($i=1;$i<count($dailyforecast["hourly"]["data"]);$i++){
                $hourlyTemp[$i] = $daily_weather["hourly"]["data"][$i]["temperature"];  
            }//end of for
 
            if($hourlyTemp[1]){echo $hourlyTemp[1];} else {echo $hourlyTemp[0];}

            ?>

            parsedDailyReport = JSON.parse(JSON.stringify(<?php echo json_encode($daily_weather); ?>)); 

            if (parsedDailyReport){
                var subtitle = ""; 
                subtitle += "<p align = 'center'><font size='7'><strong> Daily Weather </strong><font></p>"; 
                document.getElementById('subtitle').innerHTML = subtitle; 

                var dailyContent="<br>";
                dailyContent += "<br>"; 

                //reading the parsed output and storing the values from the JSON output                 
                dailyContent += "<p align='center'><font size='6' color='white'>" + parsedDailyReport.summary + "</font>"                            

                if(parsedDailyReport.icon == "clear-day" || parsedDailyReport.icon == "clear-night"){
                    dailyContent += "<img src='https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png' width='200' height='200' align='right'></p>";
                }

                else if(parsedDailyReport.icon == "rain"){
                    dailyContent += "<img src='https://cdn3.iconfinder.com/data/icons/weather-344/142/rain-512.png' width='200' height='200' align='right'></p>";
                }

                else if(parsedDailyReport.icon == "snow"){
                    dailyContent += "<img src=https://cdn3.iconfinder.com/data/icons/weather-344/142/snow-512.png width='200' height='200' align='right'></p>";
                }

                else if(parsedDailyReport.icon == "sleet"){
                    dailyContent += "<img src=https://cdn3.iconfinder.com/data/icons/weather-344/142/lightning-512.png width='200' height='200' align='right'></p>";
                }

                else if(parsedDailyReport.icon == "wind"){
                    dailyContent += "<img src=https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_10-512.png width='200' height='200' align='right'></p>";
                }

                else if(parsedDailyReport.icon == "fog"){
                    dailyContent += "<img src=https://cdn3.iconfinder.com/data/icons/weather-344/142/cloudy-512.png width='200' height='200' align='right'></p>";
                }

                else if(parsedDailyReport.icon == "cloudy"){
                    dailyContent += "<img src=https://cdn3.iconfinder.com/data/icons/weather-344/142/cloud-512.png width='200' height='200' align='right'></p>";
                }

                else if(parsedDailyReport.icon == "partly-cloudy-day" || parsedDailyReport.icon == "partly-cloudy-night"){
                    dailyContent += "<img src=https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png width='200' height='200' align='right'></p>";
                }

                else{
                    //do nothing 
                }
                
                dailyContent += "<p align='center'><font color='white' size='7'><strong>" + parsedDailyReport.temperature + "<strong></font><img src='https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png' width= '7' height='10' align='top'/><font color='white' size='6'><strong>"+"F"+ "</strong></font></p>";
                dailyContent += "<br>"; 
                dailyContent += "<br>"; 
                dailyContent += "<br>"; 
                dailyContent += "<br>"; 

                if(parsedDailyReport.precipitation<=0.001){
                    dailyContent += "<p align='right'><font size='3' color='white'><strong> Precipitation: </strong></font><font size='5' color='white'><strong> None </strong></font></p>"; 
                }


                else if(parsedDailyReport.precipitation<=0.015){
                    dailyContent += "<p align='right'><font size='3' color='white'><strong> Precipitation: </strong></font><font size='5' color='white'><strong> Very Light </strong></font></p>"; 
                }


                else if(parsedDailyReport.precipitation<=0.05){
                    dailyContent += "<p align='right'><font size='3' color='white'><strong> Precipitation: </strong></font><font size='5' color='white'><strong> Light </strong></font></p>"; 
                }


                else if(parsedDailyReport.precipitation<=0.1){
                    dailyContent += "<p align='right'><font size='3' color='white'><strong> Precipitation: </strong></font><font size='5' color='white'><strong> Moderate </strong></font></p>"; 
                }


                else if(parsedDailyReport.precipitation>01){
                    dailyContent += "<p align='right'><font size='3' color='white'><strong> Precipitation: </strong></font><font size='5' color='white'><strong> Heavy </strong></font></p>"; 
                }

                else{
                    dailyContent += "<p align='right'><font size='3' color='white'><strong> Precipitation: </strong></font><font size='5' color='white'><strong> N/A </strong></font></p>"; 
                }

                dailyContent += "<p align='right'><font size='3' color='white'><strong> Chance of Rain: </strong></font><font size='5' color='white'><strong>" +  parsedDailyReport.rainChance*100 + "</strong></font><font color='white' size='4'> % </font></p>"; 

                dailyContent += "<p align='right'><font size='3' color='white'><strong> Wind Speed: </strong></font><font size='5' color='white'><strong>" +  parsedDailyReport.windspeed + "</strong></font><font color='white' size='4'><strong> mph </strong></font></p>";

                dailyContent += "<p align='right'><font size='3' color='white'><strong> Humidity: </strong></font><font size='5' color='white'><strong>" +  parsedDailyReport.humidity*100 + "</strong></font><font color='white' size='4'><strong> % </strong></font></p>";

                dailyContent += "<p align='right'><font size='3' color='white'><strong> Visibility: </strong></font><font size='5' color='white'><strong>" +  parsedDailyReport.visibility + "</strong></font><font color='white' size='4'><strong> mi </strong></font></p>";

                var sunrise = Math.floor((parsedDailyReport.sunriseTime/(1000*60*60)) % 24);
                console.log(sunrise);

                var sunset = Math.floor((parsedDailyReport.sunsetTime/(1000*60*60)) % 24);
                console.log(sunset);                  
                
                dailyContent += "<p align='right'><font size='3' color='white'><strong> Sunrise / Sunset: </strong></font><font size='5' color='white'><strong>" +  sunrise +"</strong></font><font size='4' color='white'><strong> AM / </strong></font><font size='5' color='white'><strong>" +  sunset +"</strong></font><font size='4' color='white'><strong> PM </strong></font></p>";  

                    
                dailyContent += "<p align='center'> <font size='6'><strong> Day's Hourly Weather </strong></font></p>"                
                    
                document.getElementById('dailyWeather').innerHTML = dailyContent;
                document.getElementById('displayCard').innerHTML = ""; 
                document.getElementById('displayCard').style.width = "0px"; 
                document.getElementById('displayCard').style.height = "0px"; 
                document.getElementById('displayCard').style.margin = "0px 0px 0px 0px"; 
                document.getElementById('displayCard').style.backgroundColor = 'white'; 
                document.getElementById('displayTable').innerHTML = ""; 
                document.getElementById('dailyWeather').style.backgroundColor = 'cadetblue'; 


                var up = ""; 
                up += "<img src='https://cdn4.iconfinder.com/data/icons/geosm-e-commerce/18/point-down-512.png' width='60' height='40' align='center' onclick='hourlyChart()'>"
                document.getElementById("hourlyChart").innerHTML = up; 

            }//end of if  

        }//end of function dailyCard    

        function hourlyChart()
        {
            var down ="";
            down += "<img src='https://cdn0.iconfinder.com/data/icons/navigation-set-arrows-part-one/32/ExpandLess-512.png' width='60' height='40' align='center'>"
            document.getElementById("hourlyChart").innerHTML = down; 

            var hourlyTemperature = JSON.parse(JSON.stringify(<?php echo json_encode($hourlyTemp); ?>));     

            //converting JSON object into array 
            var hourArray = new Array(hourlyTemperature.length); 
            for(i=0;i<hourArray.length;i++){
                hourArray[i] = hourlyTemperature.i; 
            }
            
            google.charts.load('current', {'packages':['corechart']});
            google.charts.setOnLoadCallback(drawChart);

            function drawChart() {
                var data = google.visualization.arrayToDataTable(hourArray);

                var options = {
                curveType: 'function',
                legend: { position: 'bottom' }
                };

                var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

                chart.draw(data, options);
            }
             
            
        }//end of function hourlyChart 

    </script>
    
</body>
</html>