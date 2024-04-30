<html>
<head>
    <title>Weather App!</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="row">
    <div class="col-lg-4"></div>
    <div class="col-lg-4">
        <div class="card" style="">
            <img src="https://cdn-icons-png.freepik.com/512/4804/4804190.png" class="card-img-top" alt="..." >
            <div class="card-body">
                <h5 class="card-title">Hellos ${user}</h5>
                <p class="card-text">

                    <ul>
                        <li>Temperature now : <span id="temp_now"></span></li>
                        <li>Feel Like : <span id="feel_like"></span></li>
                </ul>
                </p>
                <button id="bt_refresh" class="btn btn-primary">Refresh</button>
            </div>
        </div>
    </div>
    <div class="col-lg-4"></div>
</div>

<script src="js/jquery-3.7.1.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script>
    $(function (){
        get_weather();

        $('#bt_refresh').click(function (){
            get_weather();
        });
    });

    function get_weather(){
        $('#temp_now, #feel_like').html('');
        $.ajax({
            type : 'GET',
            url: '/api/weather_now_mapping',
            data: '',
            cache: false,
            dataType : 'json',
            success: function(data) {
                var tempCelc = Math.round(data.data.temp - 273);
                var feelLikeCelc = Math.round(data.data.feels_like - 273);
                $('#temp_now').html(tempCelc);
                $('#feel_like').html(feelLikeCelc);

            },
            error: function(e){
                alert(e);
            }
        });
    }
</script>
</body>
</html>