/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.repositories;

import com.nearbyshops.models.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepository extends MongoRepository<Shop, String> {


}
