package artem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class FXMLMainFormController {

    @FXML
    private Text Feels;

    @FXML
    private Text Information;

    @FXML
    private Text Time;

    @FXML
    private Text Humidity;

    @FXML
    private Button OK_button;

    @FXML
    private Text Visibility;

    @FXML
    private Text Temperature;

    @FXML
    private TextField getCity;
    @FXML
    void initialize(){
        OK_button.setOnAction(event -> {
            String cityName = getCity.getText().trim();
            String output = getUrlContent("http://api.weatherapi.com/v1/current.json?key=214231c959c74ffab27165728221808&q="+ cityName +"&aqi=no");
            if(!output.isEmpty()){
                JSONObject obj = new JSONObject(output);

                Temperature.setText("ТЕМПЕРАТУРА: " + obj.getJSONObject("current").getDouble("temp_c"));
                Feels.setText("ОЩУЩАЕТСЯ: " + obj.getJSONObject("current").getDouble("feelslike_c"));
                Humidity.setText("ВЛАЖНОСТЬ: " + obj.getJSONObject("current").getDouble("humidity"));
                Time.setText("ВРЕМЯ: " + obj.getJSONObject("location").getString("localtime"));
                Visibility.setText("ВИДИМОСТЬ: " + obj.getJSONObject("current").getDouble("vis_km"));
            }
            System.out.println(output);
            //Temperature.setText("ТЕМПЕРАТУРА: " + 25);

        });
    }
    private static String getUrlContent(String urlAdress){
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;
            while((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();

        }catch (Exception e){
            System.out.println("Такой город не найден!" );

        }
        return content.toString();
    }

}
