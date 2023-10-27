package com.techelevator.services;

import org.springframework.stereotype.Component;

import com.techelevator.model.CatPic;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCatPicService implements CatPicService {

	private static final String API_BASE_URL = "https://cat-data.netlify.app/api/pictures/random";
	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public CatPic getPic() {
		// TODO Auto-generated method stub
		try {
			return restTemplate.getForObject(API_BASE_URL, CatPic.class);
		} catch (RestClientResponseException e){
			throw new RuntimeException("Error while fetching a cat photo, meow", e);
		}
	}

}	
