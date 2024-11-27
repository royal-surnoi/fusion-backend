package fusionIQ.AI.V2.fusionIq.service;

import fusionIQ.AI.V2.fusionIq.data.*;
import fusionIQ.AI.V2.fusionIq.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PersonalDetailsService {

    @Autowired
    private PersonalDetailsRepo personalDetailsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ArticlePostRepo articlePostRepo;

    @Autowired
    private ImagePostRepo imagePostRepo;

    @Autowired
    private ShortVideoRepo shortVideoRepo;

    @Autowired
    private LongVideoRepo longVideoRepo;

    @Autowired
    private AIFeedRepo aiFeedRepo;

    public PersonalDetails savePersonalDetails(Long userId, PersonalDetails personalDetails) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            personalDetails.setUser(user);
            checkAndSetProfileComplete(personalDetails);
            return personalDetailsRepo.save(personalDetails);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public PersonalDetails getPersonalDetailsByUserId(Long userId) {
        Optional<PersonalDetails> optional = personalDetailsRepo.findByUserId(userId);
        return optional.orElse(null);
    }

    public PersonalDetails getPersonalDetailsById(Long id) {
        Optional<PersonalDetails> optional = personalDetailsRepo.findById(id);
        return optional.orElse(null);
    }

    public List<PersonalDetails> getAllPersonalDetails() {
        return personalDetailsRepo.findAll();
    }

    public void deletePersonalDetails(Long id) {
        personalDetailsRepo.deleteById(id);
    }

    public PersonalDetails updatePersonalDetails(Long id, PersonalDetails updatedDetails) {
        Optional<PersonalDetails> optional = personalDetailsRepo.findById(id);
        if (optional.isPresent()) {
            PersonalDetails existingDetails = optional.get();
            copyNonNullProperties(updatedDetails, existingDetails);
            checkAndSetProfileComplete(existingDetails);
            return personalDetailsRepo.save(existingDetails);
        } else {
            throw new RuntimeException("Personal details not found");
        }
    }

    private void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || (srcValue instanceof Integer && ((Integer) srcValue == 0))) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }



    public void updateLocation(Long userId, Double latitude, Double longitude) {
        Optional<PersonalDetails> optional = personalDetailsRepo.findByUserId(userId);
        if (optional.isPresent()) {
            PersonalDetails personalDetails = optional.get();
            personalDetails.setLatitude(latitude);
            personalDetails.setLongitude(longitude);
            personalDetailsRepo.save(personalDetails);
        } else {
            throw new RuntimeException("Personal details not found for userId: " + userId);
        }
    }
    public void checkAndSetProfileComplete(PersonalDetails personalDetails) {
        boolean isComplete = personalDetails.getAge() != null && personalDetails.getAge() > 0 &&
                personalDetails.getInterests() != null && !personalDetails.getInterests().isEmpty() &&
                personalDetails.getLatitude() != null &&
                personalDetails.getLongitude() != null &&
                personalDetails.getPermanentAddress() != null && !personalDetails.getPermanentAddress().isEmpty() &&
                personalDetails.getPermanentCity() != null && !personalDetails.getPermanentCity().isEmpty() &&
                personalDetails.getPermanentCountry() != null && !personalDetails.getPermanentCountry().isEmpty() &&
                personalDetails.getPermanentState() != null && !personalDetails.getPermanentState().isEmpty() &&
                personalDetails.getPermanentZipcode() != null && !personalDetails.getPermanentZipcode().isEmpty() &&
                personalDetails.getSkills() != null && !personalDetails.getSkills().isEmpty() &&
                personalDetails.getUserLanguage() != null && !personalDetails.getUserLanguage().isEmpty() &&
                personalDetails.getUserDescription() != null && !personalDetails.getUserDescription().isEmpty() &&
                personalDetails.getProfession() != null && !personalDetails.getProfession().isEmpty();

        personalDetails.setProfileComplete(isComplete);
        personalDetailsRepo.save(personalDetails);
    }

    public PersonalDetails findByUserId(Long userId) {
        PersonalDetails details = personalDetailsRepo.findByUserId(userId).orElse(null);
        if (details != null) {
            checkAndSetProfileComplete(details);
        }
        return details;
    }

    public Map<String, Object> getPersonalDetailsWithUserFields(Long userId) {
        List<Object[]> result = personalDetailsRepo.findPersonalDetailsAndUserFieldsByUserId(userId);

        if (result.isEmpty()) {
            throw new RuntimeException("No personal details found for user with id: " + userId);
        }

        Object[] fields = result.get(0);

        Map<String, Object> detailsMap = new HashMap<>();
        detailsMap.put("profession", fields[0]);
        detailsMap.put("userLanguage", fields[1]);
        detailsMap.put("userDescription", fields[2]);
        detailsMap.put("age", fields[3]);
        detailsMap.put("latitude", fields[4]);
        detailsMap.put("longitude", fields[5]);
        detailsMap.put("interests", fields[6]);
        detailsMap.put("name", fields[7]);
        detailsMap.put("email", fields[8]);
        detailsMap.put("userImage", fields[9]);
        detailsMap.put("userId", fields[10]);

        return detailsMap;
    }

    public List<Map<String, Object>> getSelectedPersonalDetails() {
        List<Object[]> results = personalDetailsRepo.findSelectedPersonalDetails();
        List<Map<String, Object>> mappedResults = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("userId", row[0]);
            userDetails.put("age", row[1]);
            userDetails.put("longitude", row[2]);
            userDetails.put("latitude", row[3]);
            userDetails.put("interests", row[4]);

            mappedResults.add(userDetails);
        }

        return mappedResults;

    }

    public void createAiFeed(Long userId, String feedType, boolean feedInteraction, LocalDateTime createdAt,
                             Long articleId, Long imageId, Long shortVideoId, Long longVideoId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        AiFeed aiFeed = new AiFeed();
        aiFeed.setUser(user);
        aiFeed.setFeedType(feedType);
        aiFeed.setFeedInteraction(feedInteraction);
        aiFeed.setCreatedAt(createdAt);

        if (articleId != null) {
            ArticlePost articlePost = articlePostRepo.findById(articleId).orElse(null);
            aiFeed.setArticlePost(articlePost);
        }

        if (imageId != null) {
            ImagePost imagePost = imagePostRepo.findById(imageId).orElse(null);
            aiFeed.setImagePost(imagePost);
        }

        if (shortVideoId != null) {
            ShortVideo shortVideo = shortVideoRepo.findById(shortVideoId).orElse(null);
            aiFeed.setShortVideo(shortVideo);
        }

        if (longVideoId != null) {
            LongVideo longVideo = longVideoRepo.findById(longVideoId).orElse(null);
            aiFeed.setLongVideo(longVideo);
        }

        aiFeedRepo.save(aiFeed);
    }
}
