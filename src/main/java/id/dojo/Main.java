package id.dojo;

import com.google.gson.Gson;
import id.dojo.models.MyResponse;
import id.dojo.models.WeatherStatus;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinFreemarker;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main {
    static Gson gson;

    public static void main(String[] args) {
        gson = new Gson();

        Javalin app = Javalin.create(config -> {
                    config.fileRenderer(new JavalinFreemarker());
                    config.staticFiles.add(staticFiles -> {
                        staticFiles.hostedPath = "/";                   // change to host files on a subpath, like '/assets'
                        staticFiles.directory = "/public";              // the directory where your files are located
                        staticFiles.location = Location.CLASSPATH;      // Location.CLASSPATH (jar) or Location.EXTERNAL (file system)
                    });
                })
                .get("/", ctx -> {
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("user", "Masjo");
                    ctx.render("/views/main.ftl", attributes);
                })
                .get("/api/weather_now", ctx ->
                        ctx.json(getTemperatureJSON().toString())
                )
                .get("/api/weather_now_mapping", ctx ->{
                        WeatherStatus temperature = getTemperature();
                        MyResponse myResponse = new MyResponse(200, "Ok", temperature);
                        ctx.json(myResponse);
                })
                .start(1234);
    }

    public static WeatherStatus getTemperature(){

        double latitude = -7.795580;
        double longitude = 110.369492;
        String apikey = "e13dcc90e92e2b45611efd34626f1651";
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid="+apikey;
        HttpResponse<JsonNode> response = Unirest.get(url)
                .asJson();

        JSONObject data = response.getBody().getObject();
        String mainData = data.getJSONObject("main").toString();
        return gson.fromJson(mainData, WeatherStatus.class);
    }

    public static JSONObject getTemperatureJSON(){

        double latitude = -7.795580;
        double longitude = 110.369492;
        String apikey = "e13dcc90e92e2b45611efd34626f1651";
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid="+apikey;
        HttpResponse<JsonNode> response = Unirest.get(url)
                .asJson();

        JSONObject data = response.getBody().getObject();

        return data.getJSONObject("main");
    }
}