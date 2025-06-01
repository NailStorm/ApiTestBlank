import com.fasterxml.jackson.databind.ObjectMapper;
import jsonHelpers.Pet;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static enumCollection.petStatus.*;

public class ApiTest {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void petRecordLifeCycle() throws IOException, InterruptedException {

        SimpleDateFormat formatter = new SimpleDateFormat("ddHHmmss");
        int pet_id = Integer.parseInt(formatter.format(new Date()));
        String pet_name = "cat " + pet_id;

        System.out.println("Pet ID: " + pet_id);

        Pet post = new Pet();
        post.setId(pet_id);
        post.setName(pet_name);

        //Создаем питомца (проверка POST)
        Request petCreate = new Request.Builder()
                .url(BASE_URL + "/pet")
                .post(RequestBody
                        .create(objectMapper.writeValueAsString(post),
                                MediaType.parse("application/json")))
                .build();
        try (Response response = client.newCall(petCreate).execute()) {
            assertEquals(200, response.code());
            System.out.println("POST create pet \n" + response);
        }

        //Костыль, ожидаем пока данные запишутся, пока гонял то моментально создавалось, то очень долго
        //И даже при запуске через веб интерфейс свагера через раз может отдавать 404 на существующую запись
        sleep(10000);

        //Проверяем что данные записаны (проверка GET)
        Request petCheck = new Request.Builder()
                .url(BASE_URL + "/pet/" + pet_id)
                .build();
        try (Response response = client.newCall(petCheck).execute()) {
            assertEquals(200, response.code());
            String responseBody = response.body().string();
            Pet pet = objectMapper.readValue(responseBody, Pet.class);
            assertEquals(pet_name, pet.getName());
            System.out.println("GET check pet \n" + response);
        }

        Pet put = new Pet();
        put.setId(pet_id);
        put.setName(pet_name);
        put.setStatus(SOLD.getTitle());

        //Обновляем запись питомца (проверка PUT)
        Request petUpdate = new Request.Builder()
                .url(BASE_URL + "/pet")
                .put(RequestBody
                        .create(objectMapper.writeValueAsString(put),
                                MediaType.parse("application/json")))
                .build();
        try (Response response = client.newCall(petUpdate).execute()) {
            assertEquals(200, response.code());
            System.out.println("PUT update pet \n" + response);
        }

        sleep(10000);

        //Проверяем что данные обновлены (проверка GET)
        Request petCheckUpdate = new Request.Builder()
                .url(BASE_URL + "/pet/" + pet_id)
                .build();
        try (Response response = client.newCall(petCheckUpdate).execute()) {
            assertEquals(200, response.code());
            String responseBody = response.body().string();
            Pet pet = objectMapper.readValue(responseBody, Pet.class);
            assertEquals(pet_name, pet.getName());
            assertEquals(SOLD.getTitle(),pet.getStatus());
            System.out.println("GET check pet update \n" + response);
        }

        //Удаляем запись (проверка DELETE)
        Request petDelete = new Request.Builder()
                .url(BASE_URL + "/pet/" + pet_id)
                .delete()
                .build();
        try (Response response = client.newCall(petDelete).execute()) {
            assertEquals(200, response.code());
            System.out.println("DELETE pet \n" + response);
        }

        sleep(12000);

        //Проверяем что данные удалены (проверка GET)
        Request petCheckDelete = new Request.Builder()
                .url(BASE_URL + "/pet/" + pet_id)
                .build();
        try (Response response = client.newCall(petCheckDelete).execute()) {
            assertEquals(404, response.code());
            System.out.println("GET check pet update \n" + response);
        }

    }
}
