import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.io.FileReader;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;


import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class App {
    public static HashMap<String, Double> currencyData;
    public static HashMap<String, String> countryCode;
    public static HashMap<String, String> currencyCode;
    public static String[] countryNames;
    JFrame f;

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    // C:\Users\SRachamalla\Desktop\Currency_Convertor\src
    public static HashMap<String, Double> getCurrencyData() throws Exception {
        JSONObject json = readJsonFromUrl("https://floatrates.com/daily/usd.json"); // Get data using a call to this
                                                                                    // website
        HashMap<String, Double> currencyData = new HashMap<String, Double>();
        for (String currencyKey : json.keySet()) { // for each availble currency get the rate and its code
            double val = json.getJSONObject(currencyKey).getDouble("rate"); // get currency rate
            val = Math.round(val * 100.0) / 100.0; // rounding to only two decimal places
            currencyData.put(currencyKey.toUpperCase(), val);
        }
        return currencyData;
    }

    public static String getJsonString(String path) {

        StringBuilder jsonStringBuilder = new StringBuilder();
        String jsonString = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            // Read the file line by line
            while ((line = bufferedReader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }

            // Convert StringBuilder to String
            jsonString = jsonStringBuilder.toString();

            // Print the JSON data
            // System.out.println("JSON data read from file:");
            // System.out.println(jsonString);
            return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static HashMap<String, String> getCountryCode() throws Exception {
        // JSONObject json1 = readJsonFromUrl("https://country.io/names.json"); // Get
        // data using a call to this website
        String path = "C:\\\\Users\\\\SRachamalla\\\\Desktop\\\\Currency_Convertor\\\\src\\\\dataCode1.json";
        String data = getJsonString(path);
        // JSONObject json = readJsonFromUrl("https://api.jsonserve.com/tzZG75"); // Get
        // data using a call to this
        // website
        JSONObject json = new JSONObject(data);
        HashMap<String, String> countryCode = new HashMap<String, String>();
        for (String countryKey : json.keySet()) {
            countryCode.put(json.getString(countryKey), countryKey);
        }
        return countryCode;
    }

    public static HashMap<String, String> getCurrencyCode() throws Exception {
        String path = "C:\\Users\\SRachamalla\\Desktop\\Currency_Convertor\\src\\currencyCode.json";
        String data = getJsonString(path);

        JSONObject json = new JSONObject(data);
        HashMap<String, String> countryCode = new HashMap<String, String>();
        for (String countryKey : json.keySet()) {
            countryCode.put(countryKey, json.getString(countryKey));
        }
        return countryCode;
    }

    public static void getData() throws Exception {
        currencyData = getCurrencyData();
        countryCode = getCountryCode();
        currencyCode = getCurrencyCode();
        int size = countryCode.size();
        countryNames= new String[size];
        int j = 0;
        for(String i: countryCode.keySet()){
            String val = countryCode.get(i);
            if(currencyCode.containsKey(val)){
                String val2 = currencyCode.get(val);
                if(currencyData.containsKey(val2)){

                }
            }
        }
       
    }

    public App() throws Exception {
        f = new JFrame("Currency Convertor");
        getData();
        
         
        
        JComboBox cb = new JComboBox(countryNames);
        cb.setBounds(70, 70, 90, 20);
        AutoCompleteDecorator.decorate(cb);
        f.add(cb);
        f.setLayout(null);
        f.setSize(400, 500);
        f.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        //new App();
        // System.out.println("Hello, World!");
        // JSONObject json = readJsonFromUrl("https://floatrates.com/daily/usd.json");

        // double rate= json.getJSONObject("gbp").getDouble("rate");
        // System.out.println("Rate: "+ rate);
        // HashMap<String, Double> currencyData = getCurrencyData();
        // HashMap<String, String> countryCode = getCountryCode();
        // HashMap<String, String> currencyCode = getCurrencyCode();
        // System.out.println(currencyData.get(currencyCode.get(countryCode.get("Bangladesh"))));
        // System.out.println(currencyData.get("CAD"));
        // System.out.println(currencyCode.get(countryCode.get("Bangladesh")));
        // test();
    }
}
