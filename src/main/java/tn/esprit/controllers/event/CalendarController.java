package tn.esprit.controllers.event;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.EntryViewBase;
import com.calendarfx.view.TimeField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import tn.esprit.controllers.motherSideBarController;
import tn.esprit.entities.Event;
import tn.esprit.services.EventParticipationService;
import tn.esprit.services.EventService;
import tn.esprit.services.UserServices;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {
    @FXML
    private CalendarView calendarView;
    @FXML
    private VBox sidebar;

    final EventParticipationService eps = new EventParticipationService();
    final EventService es = new EventService();
    final UserServices us = new UserServices();
    List<Event> events = eps.getAllParticipatedEvents(us.getUser(73));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FXMLLoader fxmlLoader1 = new FXMLLoader();
        fxmlLoader1.setLocation(getClass().getResource("/motherSideBar.fxml"));
        try{
            VBox sideBar = fxmlLoader1.load();
            motherSideBarController eventController=fxmlLoader1.getController();
            sidebar.getChildren().add(sideBar);

        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        /*********************side bar end************************/
// Create a calendar source
        CalendarSource calendarSource = new CalendarSource("My Calendar");

        // Create a calendar
        Calendar calendar = new Calendar("My Calendar");
        //calendar.setStyle(Calendar.Style.STYLE1);

        // Add the calendar to the calendar source
        calendarSource.getCalendars().add(calendar);

        // Add some sample events
        for (Event event : events) {
            Entry<String> entry = createEntry(event);
            calendar.addEntry(entry);
        }


        // Set the calendar source to the calendar view
        calendarView.getCalendarSources().addAll(calendarSource);
        calendarView.setShowAddCalendarButton(false);
        calendarView.showMonthPage();




    }

    private Entry<String> createEntry(Event event) {
        Entry<String> entry = new Entry<>(event.getName()); // Use event name as entry title
        entry.setId(Integer.toString(event.getId())); // Assign event ID as entry ID
        // Convert Date to LocalDate
        Date date = event.getDay();
        LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));

// Convert Time to LocalTime
        LocalTime startTime = event.getHeureDebut().toLocalTime();
        LocalTime endTime = event.getHeureFin().toLocalTime();

        // Concatenate date and start time
        LocalDateTime startDateTime = localDate.atTime(startTime);

        // Concatenate date and end time
        LocalDateTime endDateTime = localDate.atTime(endTime);
        // Set the start and end times of the entry
        entry.changeStartDate(LocalDate.from(startDateTime));
        entry.changeEndDate(LocalDate.from(endDateTime));
        entry.changeStartTime(startDateTime.toLocalTime());
        entry.changeEndTime(endDateTime.toLocalTime());
        entry.setLocation("OnLine");

        // You can set additional properties of the entry here, such as description or location
        return entry;
    }


}
