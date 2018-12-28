/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.controllers;

import com.nearbyshops.models.Shop;
import com.nearbyshops.services.ShopServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopController {

    @Autowired
    ShopServices shopServices;

    @GetMapping("/api/shops")
    public List<Shop> allShops() {
        // Get all shops without distance sorting
        return shopServices.allShops();
    }

    @GetMapping("/api/shops/nearest")
    public List<Shop> nearestShops(@RequestParam double lat,@RequestParam double lon,@RequestParam double distance) {
        // Get all nearest shops within distance specified by request distance parameter
        return shopServices.nearestShops(lat,lon,distance);
    }
}