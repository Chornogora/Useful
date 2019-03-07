import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

//A class to send GET/POST requests to the server and get responses

public class HttpConnector {

    private URL address;

    public HttpConnector(URL address){
        this.address = address;
    }

    public void setAddress(URL address) {
        this.address = address;
    }

    public URL getAddress() {
        return address;
    }

    public String sendPostRequest(String[] params) {

        HttpURLConnection connection;

        //creating the connection
        try {
            connection = (HttpURLConnection) address.openConnection();
        } catch (IOException e) {
            System.out.println("IOException during connection creating");
            return "";
        }

        //setting method
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            System.out.println("IOException during method setting");
            return "";
        }

        //setting parameters
        if (params != null) {
            connection.setDoOutput(true);
            try (OutputStream stream = connection.getOutputStream()) {
                StringBuilder builder = new StringBuilder();

                for (String str : params){
                    str = convertParam(str);
                    builder.append(str + "&");
                }

                byte[] bytes = builder.toString().getBytes();

                stream.write(bytes);
            } catch (IOException e) {
                System.out.println("IOException during setting parameters");
                return "";
            } catch (Exception e2) {
                System.out.println("Unknown Exception");
            }
        }

        return  getResponse(connection);
    }

    public String sendGetRequest(String[] params) {

        HttpURLConnection connection;
        URL GETURL = null;
        //setting parameters
        try {
            if (params != null) {
                String StringAddress = Arrays.asList(params).stream().reduce(address.toString() + "?", (acc, param) -> {
                    param = convertParam(param);
                    return acc + param + "&";
                });
                StringAddress = StringAddress.substring(0, StringAddress.length() - 1);
                GETURL = new URL(StringAddress);
            } else {
                GETURL = address;
            }
        }catch(MalformedURLException e){
            System.out.println("Failed to create URL");
        }

        //creating the connection
        try {
            connection = (HttpURLConnection) GETURL.openConnection();
            connection.setRequestMethod("GET");
        }catch (ProtocolException e) {
            System.out.println("IOException during method setting");
            return "";
        }catch (IOException e) {
            System.out.println("IOException during connection creating");
            return "";
        }

        return  getResponse(connection);
    }

    private String getResponse(HttpURLConnection connection){
        //Getting Response Message
        try {
            InputStreamReader stream = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
            Scanner sc = new Scanner(stream);
            StringBuilder builder = new StringBuilder();

            while (sc.hasNext()) {
                builder.append(sc.next());
                builder.append(" ");
            }

            return builder.toString();
        } catch (IOException e) {
            System.out.println("IOException during response getting");
            return "";
        }
    }

    private static String convertParam(String param){
        String[] arr = param.split("=");
        try {
            return arr[0] + "=" + URLEncoder.encode(arr[1], StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
