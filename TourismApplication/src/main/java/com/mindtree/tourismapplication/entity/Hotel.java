package com.mindtree.tourismapplication.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int hotelId;
	private String hotelName;
	private double price;
		
	@OneToMany(mappedBy = "hotel")
	private List<Feedback> feedbacks;
	@OneToMany(mappedBy = "hotel")
	private List<Customer> customers;
	@ManyToOne
	private Tourism tourism;
	@OneToMany(mappedBy = "hotel")
	private List<Booking> bookings;
	public Hotel(int hotelId, String hotelName, double price, List<Feedback> feedbacks, List<Customer> customers,
			Tourism tourism, List<Booking> bookings) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.price = price;
		this.feedbacks = feedbacks;
		this.customers = customers;
		this.tourism = tourism;
		this.bookings = bookings;
	}
	public Hotel() {
		super();
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}
	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	public Tourism getTourism() {
		return tourism;
	}
	public void setTourism(Tourism tourism) {
		this.tourism = tourism;
	}
	public List<Booking> getBookings() {
		return bookings;
	}
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	
	
	

}
