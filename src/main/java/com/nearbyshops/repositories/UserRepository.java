/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.repositories;

import com.nearbyshops.models.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<AppUser, String> {

    AppUser findByEmail(String email);
}
