package fusionIQ.AI.V2.fusionIq.controller;


import fusionIQ.AI.V2.fusionIq.data.Location;
import fusionIQ.AI.V2.fusionIq.data.PersonalDetails;
import fusionIQ.AI.V2.fusionIq.service.PersonalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/personalDetails")
public class PersonalDetailsController {

    @Autowired
    private PersonalDetailsService personalDetailsService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<PersonalDetails> createPersonalDetails(@PathVariable Long userId, @RequestBody PersonalDetails personalDetails) {
        PersonalDetails createdDetails = personalDetailsService.savePersonalDetails(userId, personalDetails);
        return new ResponseEntity<>(createdDetails, HttpStatus.CREATED);
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<PersonalDetails> getPersonalDetailsByUserId(@PathVariable("userId") Long userId) {
        PersonalDetails details = personalDetailsService.getPersonalDetailsByUserId(userId);
        if (details != null) {
            return new ResponseEntity<>(details, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PersonalDetails> getPersonalDetailsById(@PathVariable("id") Long id) {
        PersonalDetails details = personalDetailsService.getPersonalDetailsById(id);
        if (details != null) {
            return new ResponseEntity<>(details, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PersonalDetails>> getAllPersonalDetails() {
        List<PersonalDetails> detailsList = personalDetailsService.getAllPersonalDetails();
        return new ResponseEntity<>(detailsList, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePersonalDetails(@PathVariable("id") Long id) {
        personalDetailsService.deletePersonalDetails(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PersonalDetails> updatePersonalDetails(@PathVariable Long id, @RequestBody PersonalDetails updatedDetails) {
        try {
            PersonalDetails updatedPersonalDetails = personalDetailsService.updatePersonalDetails(id, updatedDetails);
            return new ResponseEntity<>(updatedPersonalDetails, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/location")
    public ResponseEntity<Void> updateLocation(
            @RequestParam Long userId,
            @RequestBody Location location) {
        try {
            personalDetailsService.updateLocation(userId, location.getLatitude(), location.getLongitude());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/checkAndSetProfileComplete/{userId}")
    public ResponseEntity<PersonalDetails> getPersonalDetails(@PathVariable Long userId) {
        PersonalDetails details = personalDetailsService.findByUserId(userId);
        if (details != null) {
            return ResponseEntity.ok(details);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
