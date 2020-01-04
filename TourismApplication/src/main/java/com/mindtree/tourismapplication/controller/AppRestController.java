package com.mindtree.tourismapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.tourismapplication.dto.TourismDto;
import com.mindtree.tourismapplication.service.TravelTourismService;

@RestController
@RequestMapping("/rest")
public class AppRestController {
	@Autowired
	private TravelTourismService travelTourismService;

	@RequestMapping(value = "/loadTourismPlacesByState/{stateId}", method = RequestMethod.GET)
	public List<TourismDto> getTourisms(@PathVariable int stateId) {

		List<TourismDto> result = travelTourismService.getAllTourismPlacesByStateId(stateId);
		return result;
	}

}
