package com.findjob.services;

import com.findjob.models.ProfilePicture;
import com.findjob.repositories.ProfilePicturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProfilePicturesService {

    private final ProfilePicturesRepository profilePicturesRepository;

    @Autowired
    public ProfilePicturesService(ProfilePicturesRepository profilePicturesRepository) {
        this.profilePicturesRepository = profilePicturesRepository;
    }

    public ProfilePicture getProfilePicture(int id) {
        Optional<ProfilePicture> profilePicture = profilePicturesRepository.findProfilePictureById(id);
        return profilePicture.orElse(null);
    }

    public ProfilePicture getProfilePictureByPersonId(int personId) {
        Optional<ProfilePicture> profilePicture = profilePicturesRepository.findProfilePictureByPersonId(personId);
        return profilePicture.orElse(null);
    }

    @Transactional
    public ProfilePicture save(ProfilePicture profilePicture) {
        return profilePicturesRepository.save(profilePicture);
    }

    @Transactional
    public void update(int id, ProfilePicture profilePicture) {
        profilePicture.setId(id);
        profilePicturesRepository.save(profilePicture);
    }

    @Transactional
    public void delete(int id) {
        profilePicturesRepository.deleteById(id);
    }
}
