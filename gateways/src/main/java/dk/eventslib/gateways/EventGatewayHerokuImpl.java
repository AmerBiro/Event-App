package dk.eventslib.gateways;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import dk.eventslib.entities.Event;
import dk.eventslib.usecases.createevent.EventGateway;

public class EventGatewayHerokuImpl extends BaseGatewayInMemory<Event> implements EventGateway {
    @Override
    public List<Event> findAllEvents() {
        return null;
    }

    @Override
    public void delete(Event event) {

    }

    @Override
    public Event findEventByTitle(String title) {
        return null;
    }

    @Override
    public Event createEvent(Event event) {
        sendData(event);
        return event;
    }

    private void sendData(Event event) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("id", event.getId());
        jsonObject.accumulate("title", event.getTitle());
        jsonObject.accumulate("description", event.getDescription());

        boolean success = sendPost("https://kpv-events.herokuapp.com/events", jsonObject.toString());
    }

    private boolean sendPost(String url, String jsonStr) {
        boolean requestResult = false;
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(url);

            StringEntity se = new StringEntity(jsonStr);
            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null) {
                result = extractResponse(inputStream);
                requestResult = true;
            } else {
                result = "Did not work!";
                requestResult = false;
                throw new EventCreationException(result + requestResult);
            }
            System.out.println(result);
        } catch (Exception e) {
            requestResult = false;
            throw new EventCreationException(e.getMessage() + requestResult);
        }
        return requestResult;
    }

    private String extractResponse(InputStream inputStream) {
        String resultStr = "";
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream))){
            String line = null;
            while((line = bf.readLine()) != null){
                resultStr += " "+line;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new EventCreationException(e.getMessage());
        }
        return resultStr;
    }


    @Override
    public Event getEvent(String id) {
        return null;
    }
}
