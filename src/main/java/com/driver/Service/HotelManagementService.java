package com.driver.Service;

import com.driver.Repository.HotelManagementRepostory;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelManagementService {

    @Autowired
    HotelManagementRepostory hotelManagementRepostory;


    public String addHotel(Hotel hotel) {
        return hotelManagementRepostory.addHotel(hotel);
    }


    public Integer addUser(User user) {
        hotelManagementRepostory.addUser(user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        return hotelManagementRepostory.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {
        return hotelManagementRepostory.bookARoom(booking);
    }

    public int getBookings(Integer aadharCard) {
        return hotelManagementRepostory.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelManagementRepostory.updateFacilities(newFacilities, hotelName);
    }
}
