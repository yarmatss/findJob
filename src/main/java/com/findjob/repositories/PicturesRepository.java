package com.findjob.repositories;

import com.findjob.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PicturesRepository extends JpaRepository<Picture, Integer> {
    Optional<Picture> findProfilePictureById(int id);
    Optional<Picture> findProfilePictureByPersonId(int id);

}
