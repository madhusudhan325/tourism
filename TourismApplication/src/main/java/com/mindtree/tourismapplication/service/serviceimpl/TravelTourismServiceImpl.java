package com.mindtree.tourismapplication.service.serviceimpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.tourismapplication.dto.TourismDto;
import com.mindtree.tourismapplication.entity.Booking;
import com.mindtree.tourismapplication.entity.Customer;
import com.mindtree.tourismapplication.entity.Feedback;
import com.mindtree.tourismapplication.entity.Hotel;
import com.mindtree.tourismapplication.entity.State;
import com.mindtree.tourismapplication.entity.Tourism;
import com.mindtree.tourismapplication.repository.BookingRepository;
import com.mindtree.tourismapplication.repository.CustomerRepository;
import com.mindtree.tourismapplication.repository.FeedbackRepository;
import com.mindtree.tourismapplication.repository.HotelRepository;
import com.mindtree.tourismapplication.repository.StateRepository;
import com.mindtree.tourismapplication.repository.ToursimRepository;
import com.mindtree.tourismapplication.service.MailService;
import com.mindtree.tourismapplication.service.TravelTourismService;

@Service
public class TravelTourismServiceImpl implements TravelTourismService {

	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private FeedbackRepository feedbackRepository;
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private ToursimRepository toursimRepository;
	@Autowired
	private MailService mailservice;

	@Override
	public State registerStateToDatabase(State state) {

		return stateRepository.save(state);
	}

	@Override
	public List<State> getAllStatesFromDataBase() {
		return stateRepository.findAll();
	}

	@Override
	public Tourism addTourismDetails(int stateId, Tourism tourism) {
		State state = stateRepository.findById(stateId).get();
		tourism.setState(state);

		return toursimRepository.save(tourism);
	}

	@Override
	public List<TourismDto> getAllTourismPlacesByStateId(int stateId) {
		List<TourismDto> tourismDtos = new ArrayList<TourismDto>();

		for (State state : stateRepository.findAll()) {
			if (stateId == state.getStateId()) {
				for (Tourism tourism : state.getTourisms()) {
					TourismDto tourismDto = new TourismDto();
					tourismDto.setTourismId(tourism.getTourismId());
					tourismDto.setTourismName(tourism.getTourismName());
					tourismDtos.add(tourismDto);
				}
			}
		}
		return tourismDtos;
	}

	@Override
	public Hotel addHotelToDatabase(int tourismId, Hotel hotel) {
		Tourism tourism = toursimRepository.findById(tourismId).get();
		hotel.setTourism(tourism);
		return hotelRepository.save(hotel);
	}

	@Override
	public Customer registerCustomer(Customer customer) {

		return customerRepository.save(customer);
	}

	@Override
	public String checkingCustomerDetails(String emailId, String password) {
		String login = null;
		for (Customer customer : customerRepository.findAll()) {
			if (emailId.equals(customer.getEmailId())) {
				if (password.equals(customer.getPassword())) {
					login = "logined";
				}
			}
		}
		return login;
	}

	@Override
	public List<Hotel> getAllHotelsPresentInThatTourismPlace(int stateId, int tourismId) {
		double avgRating = 0;
		List<Hotel> hotels = new ArrayList<Hotel>();
		for (State state : stateRepository.findAll()) {
			if (stateId == state.getStateId()) {
				for (Tourism tourism : state.getTourisms()) {
					if (tourismId == tourism.getTourismId()) {
						double rating = 0;
						for (Hotel hotel : tourism.getHotels()) {
							int size = hotel.getFeedbacks().size();
							for (Feedback feedback : hotel.getFeedbacks()) {
								rating = rating + feedback.getRating();
							}
							avgRating = rating / size;
							hotel.setAvgRating(avgRating);
							rating = 0;
							hotels.add(hotel);
						}
					}
				}
			}
		}
		return hotels;
	}

	@Override
	public Customer bookAHotel(String name, int hotelId) {
		Hotel hotel = hotelRepository.findById(hotelId).get();
		Tourism tourism = hotel.getTourism();
		// Tourism tourism = toursimRepository.findById(tourismId).get();
		Customer customer = new Customer();
		for (Customer customer2 : customerRepository.findAll()) {
			if (name.equals(customer2.getEmailId())) {
				// customer2.setHotel(hotel);
				customer = customer2;
			}
		}
		customer.setTourism(tourism);
		customer.setHotel(hotel);
		return customer;
	}

	@Override
	public List<Customer> updateDateOfJourneyOfCustomer(Customer customer, int bookingPrice, Date checkinDate,
			Date checkoutDate) throws MessagingException {
		List<Customer> customers = new ArrayList<Customer>();
		Hotel hotel = customer.getHotel();
		Booking booking = new Booking();
		booking.setCheckinDate(checkinDate);
		booking.setCheckoutDate(checkoutDate);
		booking.setBookingPrice(bookingPrice);
		customer.setBooking(booking);
		booking.setCustomer(customer);
		booking.setHotel(hotel);
		bookingRepository.save(booking);
		customerRepository.save(customer);
		customers.add(customer);
		mailservice.sendEmailWithAttachment(booking);
		return customers;
	}

	@Override
	public List<Feedback> viewCommentsForParticularHotel(int hotelId) {
		List<Feedback> feedbacks = new ArrayList<Feedback>();
		Hotel hotel = hotelRepository.findById(hotelId).get();
		for (Feedback feedback : hotel.getFeedbacks()) {
			feedbacks.add(feedback);
		}
		return feedbacks;
	}

	@Override
	public Customer commentsAndFeedbackForHotel(int customerId) {

		return customerRepository.findById(customerId).get();
	}

	@Override
	public void feedbackAndRatingForHotel(Customer customer, String feedback, double rating) {
		Feedback feedback2 = new Feedback();
		feedback2.setFeedback(feedback);
		feedback2.setRating(rating);
		customer.setFeedback(feedback2);
		Hotel hotel = customer.getHotel();
		Tourism tourism = customer.getTourism();
		feedback2.setCustomer(customer);
		feedback2.setTourism(tourism);
		feedback2.setHotel(hotel);
		feedbackRepository.save(feedback2);
		customerRepository.save(customer);

	}

	@Override
	public List<Customer> viewHotelBookedByUser(String name) {
		List<Customer> customers = new ArrayList<Customer>();
		for (Customer customer : customerRepository.findAll()) {
			if (name.equalsIgnoreCase(customer.getEmailId())) {
				customers.add(customer);
			}
		}
		return customers;
	}

	@Override
	public Customer updateCustomerDetails(int customerId) {

		return customerRepository.findById(customerId).get();
	}

	@Override
	public void updateStrengthOfBranch(Customer customer2, Date checkinDate, Date checkoutDate, int bookingPrice) {
		Booking booking = bookingRepository.findById(customer2.getBooking().getBookingId()).get();
		booking.setCheckinDate(checkinDate);
		booking.setCheckoutDate(checkoutDate);
		booking.setBookingPrice(bookingPrice);
		bookingRepository.save(booking);
	}

}
