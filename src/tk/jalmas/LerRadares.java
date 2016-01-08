package tk.jalmas;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import tk.jalmas.*;

public class LerRadares {
	private static final String URL = "http://www.saocarlosoficial.com.br/_fonte/transitoradar.asp";

	public static void main(String[] args) {		
		Calendar today = Calendar.getInstance();
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);

        System.out.println("starting...");

        ArrayList<Radar> radares = ParserRadares.parse(downloadHttp(URL), today);
        System.out.println(radares);
		//ParserRadares.parse(downloadHttp(URL), tomorrow);
		
		//JSONObject a = new JSONObject();
	}
	
	/**
     * Baixa um arquivo JSON.
     * @param url : url do arquivo
     * @return : arquivo JSON em formato String, null em caso de erro.
     */	
    private static String downloadHttp(String url) {
        String json = null, line;
        url = url.replaceAll(" ", "%20");

        //System.out.println("downloadJSON url: " + url);

        InputStream stream = getHttpConnection(url);
        if (stream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder out = new StringBuilder();
            try {
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
                json = out.toString();
            } catch (IOException ex) {
                System.out.println("IOException in downloadJSON()");
                ex.printStackTrace();
            }
        }
        return json;
    }
    
    /**
     * Estabelece uma conexão HTTP.
     * @param urlString : url para a conexã a ser estabelecida.
     * @return : InputStream da conexão, null em caso de erro.
     */
    private static InputStream getHttpConnection(String urlString) {
        InputStream stream = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (UnknownHostException e1) {
            System.out.println("UnknownHostException in getHttpConnection()");
            e1.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Exception in getHttpConnection()");
            //ex.printStackTrace();
        }
        return stream;
    }

}
