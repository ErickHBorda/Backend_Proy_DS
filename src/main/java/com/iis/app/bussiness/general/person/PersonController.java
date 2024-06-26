package com.iis.app.bussiness.general.person;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iis.app.bussiness.general.person.request.SoInsert;
import com.iis.app.bussiness.general.person.response.SoGetAll;
import com.iis.app.dto.DtoPerson;
import com.iis.app.service.PersonService;


@RestController
@RequestMapping("person")
public class PersonController {
	@Autowired
	private PersonService personService;

	@PostMapping(path = "insert", consumes = { "multipart/form-data" })
	public ResponseEntity<Boolean> actionInsert(@ModelAttribute SoInsert soInsert) {
		
		try {
			DtoPerson dtoPerson = new DtoPerson();

			dtoPerson.setFirstName(soInsert.getFirstName());
			dtoPerson.setSurName(soInsert.getSurName());
			dtoPerson.setDni(soInsert.getDni());
			dtoPerson.setGender(soInsert.isGender());
			dtoPerson.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(soInsert.getBirthDate()));

			personService.insert(dtoPerson);
		} catch (Exception e) {}
		
		return new ResponseEntity<>(true, HttpStatus.CREATED);
	}

	@GetMapping(path = "getall")
	public ResponseEntity<List<SoGetAll>> actionGetAll() {
		List<DtoPerson> listDtoPerson = personService.getAll();

		List<SoGetAll> listSoPersonGet = new ArrayList<>();

		for (DtoPerson dtoPerson : listDtoPerson) {
			listSoPersonGet.add(new SoGetAll(
				dtoPerson.getIdPerson(),
				dtoPerson.getFirstName(),
				dtoPerson.getSurName(),
				dtoPerson.getDni(),
				dtoPerson.getGender(),
				dtoPerson.getBirthDate()
			));
		}

		return new ResponseEntity<>(listSoPersonGet, HttpStatus.OK);
	}
}