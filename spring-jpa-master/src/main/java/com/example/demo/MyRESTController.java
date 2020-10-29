package com.example.demo;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRESTController {

	@Autowired
	ContactRepository repository;

	@Autowired
	PlaceRepository placeRepository;

	@GetMapping("/contacts")
	public Iterable<Contact> getContacts() {
		return repository.findAll();
	}

	// 2. Add a REST method to add a contact which is received as JSON
	@PostMapping(path = "/createContact", consumes = "application/json")
	public ResponseEntity<Contact> createCustomer(@Valid Contact contact) {
		// Return 200 Ok upon successful saving else return 400 bad request
		if (contact != null) {
			contact = repository.save(contact);
			return new ResponseEntity<Contact>(contact, HttpStatus.OK);
		} else {
			return new ResponseEntity<Contact>(HttpStatus.BAD_REQUEST);
		}
	}

	// 3. Add a REST method to delete a contact by email
	@DeleteMapping("/deleteContactByEmail")
	public HttpStatus deleteCustomer(String email) {
		repository.deleteByEmail(email);
		return HttpStatus.OK;
	}

	// 5. Then provide a REST method to get all contacts based on the place name
	@GetMapping("contactsByPlace")
	public List<Contact> getContactsByPlaceName(String placeName) {
		Place place = placeRepository.findByName(placeName);
		List<Contact> contacts = null;
		if (place != null) {
			if (!place.getContacts().isEmpty() && place.getContacts().size() > 0)
				contacts = place.getContacts();
		}
		return contacts;
	}

}