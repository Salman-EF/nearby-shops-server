/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.services;

import com.nearbyshops.models.AppUser;
import com.nearbyshops.models.Shop;
import com.nearbyshops.repositories.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ShopServices {

    private static final Logger logger = LoggerFactory.getLogger(ShopServices.class);
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    UserServices userServices;

    public List<Shop> allShops() {
        List<Shop> shops = shopRepository.findAll();
        logger.info("Find all "+shops.size()+" shops");
        return shops;
    }

    public List<Shop> nearestShops(double lat, double lon, double distance) {
        List<Shop> shops = shopRepository.findByLocationNear(new Point(lat,lon), new Distance(distance, Metrics.KILOMETERS));
        logger.info("Find "+shops.size()+" shops within "+distance+"KM distance");
        return shops;
    }

    public Set<Shop> userPreferredShops() {
        AppUser user = userServices.userAuthenticated();
        return user.getPreferredShops();
    }
}
