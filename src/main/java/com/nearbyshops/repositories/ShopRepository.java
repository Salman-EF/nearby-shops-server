/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.repositories;

import com.nearbyshops.models.Shop;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShopRepository extends MongoRepository<Shop, String> {

    List<Shop> findByLocationNear(Point point, Distance distance);
}
