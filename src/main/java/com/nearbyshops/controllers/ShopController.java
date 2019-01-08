/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.controllers;

import com.nearbyshops.models.Shop;
import com.nearbyshops.services.ShopServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    public ResponseEntity<List<Shop>> nearestShops(@RequestParam double lat, @RequestParam double lon, @RequestParam double distance) {
        // Get all nearest shops within distance specified by request distance parameter
        return ResponseEntity.ok(shopServices.nearestShops(lat,lon,distance));
    }

    @GetMapping("/api/shops/preferred")
    public List<Shop> preferredShops() {
        // Get user preferred shops
        return shopServices.userPreferredShops();
    }

    @PostMapping("/api/shops/preferred")
    public List<Shop> addPreferredShop(@RequestBody Shop shop) {
        return shopServices.addPreferredShop(shop.getId());
    }

    @DeleteMapping("/api/shops/preferred")
    public List<Shop> removePreferredShop(@RequestBody Shop shop) {
        return shopServices.removePreferredShop(shop.getId());
    }

    @PostMapping("/api/shops/disliked")
    public List dislikeShop(@RequestBody Shop shop) {
        return new ArrayList(shopServices.dislikeShop(shop.getId()).keySet());
    }
}