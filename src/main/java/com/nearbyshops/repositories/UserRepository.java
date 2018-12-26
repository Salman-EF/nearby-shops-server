/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.repositories;

import com.nearbyshops.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
}
