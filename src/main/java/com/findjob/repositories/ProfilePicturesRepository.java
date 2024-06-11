package com.findjob.repositories;

import com.findjob.models.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilePicturesRepository extends JpaRepository<ProfilePicture, Integer> {
    Optional<ProfilePicture> findProfilePictureById(int id);
    Optional<ProfilePicture> findProfilePictureByPersonId(int id);

}
