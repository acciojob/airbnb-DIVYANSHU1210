package com.driver.Repository;


import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepostory {
    Map<String, Hotel> hotels = new HashMap<>();
    Map<String , User>users = new HashMap<>();

    Map<String, Booking>bookings = new HashMap<>();

    Map<Integer , List<Booking>>usersBooking = new HashMap<>();    //USER'S_ADHAAR : BOOKINGS_LIST

    Hotel hotelWithMostFacilities;


    public String addHotel(Hotel hotel) {
        //You need to add an hotel to the database
        //incase the hotelName is null or the hotel Object is null return an empty a FAILURE
        //Incase somebody is trying to add the duplicate hotelName return FAILURE
        //in all other cases return SUCCESS after successfully adding the hotel to the hotelDb.

        if(hotel == null || hotel.getHotelName() == null || hotels.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }
        hotels.put(hotel.getHotelName(), hotel);

        if(hotelWithMostFacilities == null || hotelWithMostFacilities.getFacilities().size() < hotel.getFacilities().size()){
            hotelWithMostFacilities = hotel;
        }

        return "SUCCESS";
    }


    public void addUser(User user) {
        users.put(user.getName(), user);
    }


    public String getHotelWithMostFacilities() {
        if(hotelWithMostFacilities.getFacilities().isEmpty()){
            return "";
        }
        return hotelWithMostFacilities.getHotelName();
    }

    public int bookARoom(Booking booking) {
        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there arent enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid

        String hotelName = booking.getHotelName();
        int askedRooms = booking.getNoOfRooms();
        Hotel hotel = hotels.get(hotelName);
        if(hotel.getAvailableRooms() < askedRooms){
            return -1;
        }
        int amount = hotel.getPricePerNight() * booking.getNoOfRooms();
        int roomsLeft = hotel.getAvailableRooms() - booking.getNoOfRooms();
        hotel.setAvailableRooms(roomsLeft);
        booking.setBookingId(UUID.randomUUID().toString());
        booking.setAmountToBePaid(amount);

//        add user and his booking to usersbooking database
        int aadharNo = booking.getBookingAadharCard()
        if(usersBooking.containsKey(aadharNo)){
             usersBooking.get(aadharNo).add(booking);
        }
        else{
            List<Booking> newBookingsList = new ArrayList<>();
            newBookingsList.add(booking);
            usersBooking.put(aadharNo, newBookingsList);
        }


        bookings.put(booking.getBookingId(), booking);
        return amount;
    }

    public int getBookings(Integer aadharCard) {
        return usersBooking.get(aadharCard).size();
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        //We are having a new facilites that a hotel is planning to bring.
        //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb
        //return the final updated List of facilities and also update that in your hotelDb
        //Note that newFacilities can also have duplicate facilities possible
        Hotel hotel = hotels.get(hotelName);

        for(Facility facility: newFacilities){
            if(!hotel.getFacilities().contains(facility)){
                hotel.getFacilities().add(facility);
            }
        }

        if(hotel.getFacilities().size() > hotelWithMostFacilities.getFacilities().size()){
            hotelWithMostFacilities = hotel;
        }
        return hotel;

    }
}
