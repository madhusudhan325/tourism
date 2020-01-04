package com.mindtree.tourismapplication.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mindtree.tourismapplication.entity.Customer;
import com.mindtree.tourismapplication.entity.Feedback;
import com.mindtree.tourismapplication.entity.Hotel;
import com.mindtree.tourismapplication.entity.State;
import com.mindtree.tourismapplication.entity.Tourism;
import com.mindtree.tourismapplication.service.TravelTourismService;

@Controller
public class TourismAppController {

	@Autowired
	private TravelTourismService travelTourismService;
	static String name = "";
	static Customer customer = new Customer();

	@GetMapping("/register")
	public String index0() {
		return "index";
	}

	@GetMapping("/form")
	public String index1() {
		return "state";
	}

	@RequestMapping("/addState")
	public String addCustomerDetails(@ModelAttribute("state") State state) {
		travelTourismService.registerStateToDatabase(state);
		return "index";
	}

	@GetMapping("/form1")
	public String index1(Model model) {
		List<State> states = travelTourismService.getAllStatesFromDataBase();
		model.addAttribute("states", states);
		return "tourism";
	}

	@RequestMapping("/addTourism")
	public String addDebitCardDetailsToFlight(@RequestParam("stateId") int stateId,
			@ModelAttribute("tourism") Tourism tourism, Model model) {

		travelTourismService.addTourismDetails(stateId, tourism);
		return "index";
	}

	@GetMapping("/form2")
	private String index(Model model) {
		List<State> states = travelTourismService.getAllStatesFromDataBase();
		model.addAttribute("states", states);
		return "hotel";
	}

	@RequestMapping("/addHotel")
	public String buyingAVechile(@RequestParam("tourismId") int tourismId, @ModelAttribute("hotel") Hotel hotel) {
		travelTourismService.addHotelToDatabase(tourismId, hotel);
		return "index";

	}

	@GetMapping("/")
	public String index11() {
		return "login";
	}

	@GetMapping("/form3")
	public String index() {
		return "register";
	}

	@RequestMapping("/addCustomer")
	public String addCustomerDetails(@ModelAttribute("customer") Customer customer) {
		travelTourismService.registerCustomer(customer);

		return "login";
	}

	@RequestMapping("/loginpage")
	public String checkCustomer(String emailId, String password, Model model) {
		name = emailId;
		String admin2 = travelTourismService.checkingCustomerDetails(emailId, password);
		if (admin2.equals("logined")) {
			return "booking";
		} else {
			return "error";
		}

	}

	@GetMapping("/form5")
	public String index5(Model model) {
		List<State> states = travelTourismService.getAllStatesFromDataBase();
		model.addAttribute("states", states);
		return "customerpage";
	}

	/*
	 * @RequestMapping("/form4") public String index4(Model model) {
	 * 
	 * return "customerpage"; }
	 */
	@RequestMapping("/getHotels")
	public String getAllHotelsForParticulatTourism(@RequestParam("stateId") int stateId,
			@RequestParam("tourismId") int tourismId, Model model) {
		List<State> states = travelTourismService.getAllStatesFromDataBase();
		model.addAttribute("states", states);
		List<Hotel> hotels = travelTourismService.getAllHotelsPresentInThatTourismPlace(stateId, tourismId);
		model.addAttribute("hotels", hotels);
		return "customerpage";

	}

	@GetMapping("/book/{hotelId}")
	public String bookingHotelForCustomer(Model model, @PathVariable int hotelId) {
		customer = travelTourismService.bookAHotel(name, hotelId);
		return "bookingpage";
	}

	@RequestMapping("/bookahotel")
	public String bookAHotelForCustomer(@RequestParam("checkinDate") Date checkinDate,
			@RequestParam("checkoutDate") Date checkoutDate, @RequestParam("bookingPrice") int bookingPrice,
			Model model) {
		List<Customer> customers = travelTourismService.updateDateOfJourneyOfCustomer(customer, bookingPrice,
				checkinDate, checkoutDate);
		model.addAttribute("customers", customers);
		return "bookedpage";
	}

	@GetMapping("/view/{hotelId}")
	public String viewCommentsOfThatHotel(Model model, @PathVariable int hotelId) {
		List<Feedback> feedbacks = travelTourismService.viewCommentsForParticularHotel(hotelId);
		model.addAttribute("feedbacks", feedbacks);
		return "viewcomments";
	}

	@GetMapping("/comments/{customerId}")
	public String commentsForHotelByCustomer(Model model, @PathVariable int customerId) {
		customer = travelTourismService.commentsAndFeedbackForHotel(customerId);
		return "feedback";
	}

	@RequestMapping("/feedbackforhotel")
	public String FeedbackForHotel(String feedback, double rating) {
		travelTourismService.feedbackAndRatingForHotel(customer, feedback, rating);
		return "booking";
	}

}
