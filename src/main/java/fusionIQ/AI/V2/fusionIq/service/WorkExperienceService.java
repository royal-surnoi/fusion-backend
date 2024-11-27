package fusionIQ.AI.V2.fusionIq.service;

import fusionIQ.AI.V2.fusionIq.data.PersonalDetails;
import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.data.WorkExperience;
import fusionIQ.AI.V2.fusionIq.exception.ResourceNotFoundException;
import fusionIQ.AI.V2.fusionIq.repository.PersonalDetailsRepo;
import fusionIQ.AI.V2.fusionIq.repository.UserRepo;
import fusionIQ.AI.V2.fusionIq.repository.WorkExperienceRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WorkExperienceService {

    @Autowired
    private WorkExperienceRepo workExperienceRepository;

    @Autowired
    private PersonalDetailsRepo personalDetailsRepository;

    @Autowired
    private UserRepo userRepo;

    // Create a new WorkExperience record
//    public WorkExperience createWorkExperience(Long personalDetailsId, WorkExperience workExperience) {
//        Optional<PersonalDetails> personalDetails = personalDetailsRepository.findById(personalDetailsId);
//        if (personalDetails.isPresent()) {
//            workExperience.setPersonalDetails(personalDetails.get());
//            return workExperienceRepository.save(workExperience);
//        } else {
//            throw new ResourceNotFoundException("PersonalDetails not found with id: " + personalDetailsId);
//        }
//    }

    // Update an existing WorkExperience record
    public WorkExperience updateWorkExperience(Long id, WorkExperience updatedWorkExperience) {
        Optional<WorkExperience> existingWorkExperience = workExperienceRepository.findById(id);
        if (existingWorkExperience.isPresent()) {
            WorkExperience workExperience = existingWorkExperience.get();
            copyNonNullProperties(updatedWorkExperience, workExperience);
            return workExperienceRepository.save(workExperience);
        } else {
            throw new ResourceNotFoundException("WorkExperience not found with id: " + id);
        }
    }

    public void deleteWorkExperience(Long id) {
        if (workExperienceRepository.existsById(id)) {
            workExperienceRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("WorkExperience not found with id: " + id);
        }
    }

//    public List<WorkExperience> getAllWorkExperienceByPersonalDetailsId(Long personalDetailsId) {
//        return workExperienceRepository.findByPersonalDetailsId(personalDetailsId);
//    }

    // Utility method to copy non-null properties
    private void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    // Get the names of properties that are null
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
//
//    public WorkExperience getWorkExperienceByPersonalDetailsIdAndWorkExperienceId(Long personalDetailsId, Long workExperienceId) {
//        return workExperienceRepository.findByIdAndPersonalDetailsId(workExperienceId, personalDetailsId)
//                .orElseThrow(() -> new ResourceNotFoundException("WorkExperience not found with id: " + workExperienceId + " and personalDetailsId: " + personalDetailsId));
//    }

    public WorkExperience saveWorkExperience(Long userId, WorkExperience workExperience) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        workExperience.setUser(user); // Set the user in the work experience entity
        return workExperienceRepository.save(workExperience);
    }

    public List<WorkExperience> getWorkExperienceByUserId(Long userId) {
        return workExperienceRepository.findByUserId(userId);
    }


    public WorkExperience getWorkExperienceByUserIdAndWorkExperienceId(Long userId, Long workExperienceId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return workExperienceRepository.findById(workExperienceId)
                .filter(workExperience -> workExperience.getUser().getId() == userId)  // Use == for primitive long comparison
                .orElseThrow(() -> new RuntimeException("WorkExperience not found with id: " + workExperienceId + " for userId: " + userId));
    }
}
