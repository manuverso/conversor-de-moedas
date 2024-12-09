import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class Conversor {
    private Scanner leitura =  new Scanner(System.in);
    private Gson gson = new Gson();
    private static final String apiKey = "070094870508fa1e99aad8b5";
    private static final String link = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/";

    // Faz a solicitação à API para obter a taxa de câmbio
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(link + "USD/ARS"))
            .GET()
            .build();

    HttpResponse<String> response;

    {
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    String responseBody = response.body();


    public void converterUsdParaArs() {
        double taxaCambio = pegaTaxa(responseBody);
        System.out.println("Digite o valor em dólares: ");
        double dolares = leitura.nextDouble();
        double pesoArgentino = dolares * taxaCambio;
        System.out.println("O valor em peso argentino é: " + pesoArgentino);
    }

    public void converterArsParaUsd (){
        double taxaCambio = pegaTaxa(responseBody);
        System.out.println("Digite o valor em pesos argetino: ");
        double pesoArgetino = leitura.nextDouble();
        double dolares = pesoArgetino / taxaCambio;
        System.out.println("O valor em dólares é: " + dolares);

    }

    public void converterUsdParaBrl(){
        double taxaCambio = pegaTaxa(responseBody);
        System.out.println("Digite o valor em dólares");
    }

    public void converterBrlParaUsd(){

    }

    public void converterUsdParaCny(){

    }

    public void converterCnyParaUsd(){

    }




    private double pegaTaxa(String responseBody){
        JsonElement jsonElement = gson.fromJson(responseBody, JsonElement.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        double taxaDeConversao = jsonObject.get("conversion_rate").getAsDouble();
        return taxaDeConversao;
    }

}
