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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        // Because the shops list should be displayed without preferred ones we'll clear shops list from preferred shops
        // Get all nearest shops
        List<Shop> allShops = shopRepository.findByLocationNear(new Point(lon,lat), new Distance(distance, Metrics.KILOMETERS));
        // Get the preferred shops
        List<Shop> preferredShops = userPreferredShops();
        // The magic happen here
        for(Shop preferredShop:preferredShops) {
            for(Shop shop:allShops) {
                if (preferredShop.getId().equals(shop.getId())) {
                    allShops.remove(shop);break;
                }
            }
        }
        logger.info("Find "+allShops.size()+" shops within "+distance+"KM distance");
        return allShops;
    }

    public List<Shop> userPreferredShops() {
        AppUser user = userServices.authenticatedUser();
        return user.getPreferredShops();
    }

    public List<Shop> addPreferredShop(String shop_id) {
        // Get the authenticated user
        AppUser user = userServices.authenticatedUser();
        // Filter the user preferredShops list if this shop already exist in it --- Until now I didn't figure out why but the list.contains() function won't work here!!
        Shop shopExist = user.getPreferredShops().stream().filter(shop -> shop_id.equals(shop.getId())).findAny().orElse(null);
        // If the filter return null means that this shop not exist in the user preferredShops
        if (shopExist==null) {
            // Get the shop added
            Shop shopAdded = shopRepository.findById(shop_id).get();
            // Add the shop to the user preferredShopsList
            user.getPreferredShops().add(shopAdded);
            // Save the user changes
            userServices.updateUser(user);
            logger.info("Added new preferred shop: "+shopAdded.getName());
        }
        logger.info("Preferred shops: "+user.getPreferredShops().size());
        return user.getPreferredShops();
    }

    public List<Shop> removePreferredShop(String shop_id) {
        // Get the authenticated user
        AppUser user = userServices.authenticatedUser();
        // Filter the user preferredShops list and delete shop by id
        user.getPreferredShops().removeIf(shop -> shop_id.equals(shop.getId()));
        // Save the user changes
        userServices.updateUser(user);
        logger.info("Preferred shops: "+user.getPreferredShops().size());
        return user.getPreferredShops();
    }

    public HashMap<String, Date> dislikeShop(String shop_id) {
        // Get the authenticated user
        AppUser user = userServices.authenticatedUser();
        // shop already exist in it
        Shop shopDisliked = shopRepository.findById(shop_id).get();
        // If the filter return null means that this shop not exist
        if (shopDisliked!=null) {
            // Add the shop to the user preferredShopsList
            HashMap<String, Date> dislikedShops = user.getDislikedShops();
            dislikedShops.put(shopDisliked.getId(), new Date());
            user.setDislikedShops((HashMap<String, Date>) refreshDislikedShops(dislikedShops));
            // Save the user changes
            userServices.updateUser(user);
        }
        logger.info("Disliked shops: "+user.getDislikedShops().size());
        return user.getDislikedShops();
    }

    // Custom function for refreshing DislikedShops list and filtering it from shops disliked more than 2 hours ago
    private Map<String, Date> refreshDislikedShops(HashMap<String, Date> shopsList) {
        return shopsList.entrySet().stream()
                .filter(map -> { // Get the difference between the date & time now and dislike time
                    double hoursBetween = (new Date().getTime() - map.getValue().getTime()) / 36e5;
                    return hoursBetween < 2;
                } )
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }
}
