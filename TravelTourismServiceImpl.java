package com.mindtree.tourismapplication.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.tourismapplication.dto.TourismDto;
import com.mindtree.tourismapplication.entity.Hotel;
import com.mindtree.tourismapplication.entity.State;
import com.mindtree.tourismapplication.entity.Tourism;
import com.mindtree.tourismapplication.repository.BookingRepository;
import com.mindtree.tourismapplication.repository.CustomerRepository;
import com.mindtree.tourismapplication.repository.FeedbackRepository;
import com.mindtree.tourismapplication.repository.HotelRepository;
import com.mindtree.tourismapplication.repository.StateRepository;
import com.mindtree.tourismapplication.repository.ToursimRepository;
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

}
