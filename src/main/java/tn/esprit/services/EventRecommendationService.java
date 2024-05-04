package tn.esprit.services;

import tn.esprit.entities.Event;
import tn.esprit.entities.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventRecommendationService {
    final EventService es= new EventService();
    public EventRecommendationService(){

    }
    public List<Event> recommendEvents(String userProfilesJson , User user) {
        //userProfileJson must be with the format of json file
        String userId = String.valueOf(user.getId());
        List<Event> recommendedEvents = new ArrayList<>();
        try {
            // Command to execute the Python script
            String projectDir = System.getProperty("user.dir");
            String pythonScriptPath = projectDir + "/src/main/java/tn/esprit/util/ScriptPython/EventRecommendation.py";

            String[] cmd = {"python ", pythonScriptPath, userProfilesJson , userId};

            // Execute the Python script
            Process process = Runtime.getRuntime().exec(cmd);

            // Read output from the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            List<Integer> eventsIds = new ArrayList<>();
            //String[] elements = o.substring(1,o.length() -1).split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line of output
                System.out.println("line "+ line);
                String[] elements = line.substring(1,line.length() -1).split(",");
                for (String e : elements) {
                    eventsIds.add(Integer.parseInt(e.trim()));
                }

            }
            //System.out.println("Ids "+eventsIds);
            for (int id:eventsIds) {
                Event event = es.getOne(id);

                if (event.getDay().after(new Date())){
                    recommendedEvents.add(event);
                    //System.out.println("event with id"+id);
                    //System.out.println("recommended"+event);
                }
            }
            reader.close();

            // Wait for the process to finish
            process.waitFor();

            // Handle errors or process the output as needed
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
        return recommendedEvents;
    }
}
