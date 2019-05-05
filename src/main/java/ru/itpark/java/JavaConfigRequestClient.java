package ru.itpark.java;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import ru.itpark.common.cached.Cached;
import ru.itpark.common.model.Model;
import ru.itpark.common.services.RequestClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Cached
public class JavaConfigRequestClient implements RequestClient {
    @Value("${url}")
    private String url;

    @Override
    public Model getModel(Integer id) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ObjectMapper mapper = new ObjectMapper();
        List<Model> responseModel = mapper.readValue(response.toString(), new TypeReference<ArrayList<Model>>() {
        });
        return responseModel.stream().filter(v -> id.equals(v.getId())).findFirst().orElse(null);
    }
}