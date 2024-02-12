package photo;

import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Chatting.ChatClientObject;
import static photo.image_control.saveImage;

public class PhotoChange {
    //public static void main(String[] args) {
    public void requestApi() {
        ExecutorService executor = Executors.newSingleThreadExecutor(); // 단일 스레드 풀 생성

        executor.submit(() -> {


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("task_type","async")
                .addFormDataPart("image","file",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("QR-Code-em-java/src/input/Photo.jpg")))
                .addFormDataPart("type","boy-7")
                .build();
        Request request = new Request.Builder()
                .url("https://www.ailabapi.com/api/image/effects/ai-anime-generator")
                .method("POST", body)
                .addHeader("ailabapi-api-key", "xUsapX8vJ4D6uLjIRK5Ja9ZwcTPAbkdWECromRymilbP7Bo1LwGSDezYnXMugABv")
                .build();
        //Response response = client.newCall(request).execute();





        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONParser parser = new JSONParser();

                try {
                    JSONObject jsonObj = (JSONObject) parser.parse(responseBody);

                    // 이제 jsonObj를 사용하여 필요한 데이터에 접근할 수 있습니다.
                    //result id
                    String resultId = (String) jsonObj.get("request_id");
                    System.out.println("request_id :"+ resultId);
                    String errorMsg = (String) jsonObj.get("error_msg");
                    System.out.println("error_msg :"+ errorMsg);
                    String taskId = (String) jsonObj.get("task_id");
                    System.out.println("task_id :"+ taskId);
                    Thread.sleep(10000);
                    System.out.println("Waiting for a response from the server......");

                    while(true) {
                        Request resultRequest = new Request.Builder()
                                .url("https://www.ailabapi.com/api/common/query-async-task-result?task_id="+taskId)
                                .method("GET", null)
                                .addHeader("ailabapi-api-key", "xUsapX8vJ4D6uLjIRK5Ja9ZwcTPAbkdWECromRymilbP7Bo1LwGSDezYnXMugABv")
                                .build();

                        Response resultResponse = client.newCall(resultRequest).execute();

                        if (resultResponse.isSuccessful()) {
                            String resultResponseBody = resultResponse.body().string();
                            JSONParser resultParser = new JSONParser();

                            try {
                                JSONObject resultJsonObj = (JSONObject) resultParser.parse(resultResponseBody);
                                String resultJsonString = resultJsonObj.toString();
                                System.out.println("JSON String: " + resultJsonString);


                                //if문을 위한 status 추출
                                Long status = (Long) resultJsonObj.get("task_status");

                                //response받기 성공했다면
                                if (status==2) {
                                    JSONObject dataObj = (JSONObject) resultJsonObj.get("data");
                                    String resultUrl = (String) dataObj.get("result_url");
                                    //"https:\/\/ai-result-rapidapi.ailabtools.com\/image\/generateCartoonizedImage\/2023-12-16\/183251-7a631d55-0de7-1b2b-ef95-78958c0f4ae9-1702751571.jpg"
                                    resultUrl=resultUrl.replaceAll("[\\\\]","");
                                    System.out.println("result_url : "+resultUrl);

                                    //ChatClientObject.appointed_url(resultUrl);
                                    ChatClientObject.url=resultUrl;
                                    saveImage(resultUrl);
                                    break;
                                }else{
                                    System.out.println("reponse를 재요청합니다.");
                                    Thread.sleep(5000);
                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                                System.err.println("JSON 파싱 중 에러가 발생했습니다.");
                            }
                        } else {
                            // 에러 응답 처리
                            System.err.println("Error: " + resultResponse.code() + " " + resultResponse.message());
                        }
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                    System.err.println("JSON 파싱 중 에러가 발생했습니다.");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // 에러 응답 처리
                System.err.println("Error: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        });

        executor.shutdown(); // 모든 작업이 완료되면 스레드 풀을 종료합니다.
    }
}

