package com.findjob.services;

import com.findjob.models.Picture;
import com.findjob.repositories.PicturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PicturesService {

    private final PicturesRepository picturesRepository;

    @Autowired
    public PicturesService(PicturesRepository picturesRepository) {
        this.picturesRepository = picturesRepository;
    }

    public Picture getProfilePicture(int id) {
        Optional<Picture> profilePicture = picturesRepository.findProfilePictureById(id);
        return profilePicture.orElse(null);
    }

    public Picture getProfilePictureByPersonId(int personId) {
        Optional<Picture> profilePicture = picturesRepository.findProfilePictureByPersonId(personId);
        return profilePicture.orElse(null);
    }

    @Transactional
    public Picture save(Picture profilePicture) {
        return picturesRepository.save(profilePicture);
    }

    @Transactional
    public void update(int id, Picture profilePicture) {
        profilePicture.setId(id);
        picturesRepository.save(profilePicture);
    }

    @Transactional
    public void delete(int id) {
        picturesRepository.deleteById(id);
    }
}
