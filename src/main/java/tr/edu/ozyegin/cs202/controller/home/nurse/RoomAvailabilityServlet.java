package tr.edu.ozyegin.cs202.controller.home.nurse;

import tr.edu.ozyegin.cs202.model.Appointment;
import tr.edu.ozyegin.cs202.model.Room;
import tr.edu.ozyegin.cs202.service.room.RoomService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RoomAvailabilityServlet", urlPatterns = {"/doctor/room_availability", "/nurse/room_availability", "/nurse/home_nurse.jsp"})
public class RoomAvailabilityServlet extends HttpServlet {

    private RoomService roomService = new RoomService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String roomName = request.getParameter("roomName");

        List<Room> rooms = roomService.getRooms();
        List<Appointment> reservedTimes = roomService.getReservedTimes(roomName);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("room_availability.jsp");
        request.setAttribute("selectedRoom", roomName);
        request.setAttribute("rooms", rooms);
        request.setAttribute("reservedTimes", reservedTimes);

        requestDispatcher.include(request, response);
    }
}
