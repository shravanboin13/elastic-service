package com.demo.constants;

import java.util.HashMap;
import java.util.Map;

public class AttributeMaps {
    public static final Map<String,Integer> furnitureAttributeMap = new HashMap<String,Integer>();
    public static final Map<String,Integer> wearableAttributeMap = new HashMap<String,Integer>();
    public static final Map<String,Integer> clothingAttributeMap = new HashMap<String,Integer>();


    public static final Map<String,Integer> bluRayDVDPlayers = new HashMap<String,Integer>();
    public static final Map<String,Integer> camerasAttributeMap = new HashMap<String,Integer>();
    public static final Map<String,Integer> fansAttributeMap = new HashMap<String,Integer>();
    public static final Map<String,Integer> laptopsAttributeMap = new HashMap<String,Integer>();
    public static final Map<String,Integer> tvAttributeMap = new HashMap<String,Integer>();
    public static final Map<String,Integer> mediaStreaminAttributeMap = new HashMap<String,Integer>();
    public static final Map<String,Integer> tvMountAttributeMap = new HashMap<String,Integer>();
    public static final Map<String,Integer> movieTvShowsAttributeMap = new HashMap<String,Integer>();
    public static final Map<String,Integer> printersAttributeMap = new HashMap<String,Integer>();
    public static final Map<String,Integer> refrigeratorsAttributeMap = new HashMap<String,Integer>();
    public static final Map<String,Integer> softwaresAttributeMap = new HashMap<String,Integer>();


    public static final Map<Integer,String> furnitureOrderMap = new HashMap<Integer,String>();
    public static final Map<Integer,String> wearableOrderMap = new HashMap<Integer,String>();
    public static final Map<Integer,String> clothingOrderMap = new HashMap<Integer,String>();

    public static void intializeMaps(){
    furnitureAttributeMap.put("Brand",1);
    furnitureAttributeMap.put("Price",2);
    furnitureAttributeMap.put("Color",3);

        wearableAttributeMap.put("Brand",1);
        wearableAttributeMap.put("Size",2);
        wearableAttributeMap.put("Price",3);
        wearableAttributeMap.put("Color",4);

        clothingAttributeMap.put("Brand",1);
        clothingAttributeMap.put("Size",2);
        clothingAttributeMap.put("Price",3);
        clothingAttributeMap.put("Color",4);

        furnitureOrderMap.put(1,"Brand");
        furnitureOrderMap.put(2,"Price");
        furnitureOrderMap.put(3,"Color");


        wearableOrderMap.put(1,"Brand");
        wearableOrderMap.put(2,"Size");
        wearableOrderMap.put(3,"Price");
        wearableOrderMap.put(4,"Color");

        clothingOrderMap.put(1,"Brand");
        clothingOrderMap.put(2,"Size");
        clothingOrderMap.put(3,"Price");
        clothingOrderMap.put(4,"Color");

        bluRayDVDPlayers.put("Brand",1);
        bluRayDVDPlayers.put("Price",2);
        bluRayDVDPlayers.put("Player Type",3);
        bluRayDVDPlayers.put("Techonology",4);
        bluRayDVDPlayers.put("Port",5);
        bluRayDVDPlayers.put("Color",6);


        camerasAttributeMap.put("Brand",1);
        camerasAttributeMap.put("Price",2);
        camerasAttributeMap.put("Camera Type",3);
        camerasAttributeMap.put("Lens Type",4);
        camerasAttributeMap.put("Camera Features",5);
        camerasAttributeMap.put("Resolution",6);
        camerasAttributeMap.put("Shooting Speed",7);
        camerasAttributeMap.put("Screen Size",8);
        camerasAttributeMap.put("Colors",8);

        fansAttributeMap.put("Brand",1);
        fansAttributeMap.put("Price",2);
        fansAttributeMap.put("Fan Feature",3);
        fansAttributeMap.put("Fan Speed",4);
        fansAttributeMap.put("Fan Size",5);
        fansAttributeMap.put("Fan Color",6);
        fansAttributeMap.put("Certification",7);



        laptopsAttributeMap.put("Brand",1);
        laptopsAttributeMap.put("Price",2);
        laptopsAttributeMap.put("Laptop Type",3);
        laptopsAttributeMap.put("Kind of PC",4);
        laptopsAttributeMap.put("Usage",5);
        laptopsAttributeMap.put("Operating System",6);
        laptopsAttributeMap.put("Hard Disk",7);
        laptopsAttributeMap.put("Processor",8);
        laptopsAttributeMap.put("Screen Size",6);
        laptopsAttributeMap.put("Color",7);

        tvAttributeMap.put("Brand",1);
        tvAttributeMap.put("Price",2);
        tvAttributeMap.put("Display Technology",3);
        tvAttributeMap.put("Size",4);
        tvAttributeMap.put("Resolution",5);
        tvAttributeMap.put("Technology",6);
        tvAttributeMap.put("Screen Type",7);

        mediaStreaminAttributeMap.put("Brand",1);
        mediaStreaminAttributeMap.put("Price",2);
        mediaStreaminAttributeMap.put("Service Type",3);
        mediaStreaminAttributeMap.put("Streaming Partners",4);


        tvMountAttributeMap.put("Brand",1);
        tvMountAttributeMap.put("Price",2);
        tvMountAttributeMap.put("Mount Type",3);
        tvMountAttributeMap.put("Size",4);
        tvMountAttributeMap.put("Mount Features",5);
        tvMountAttributeMap.put("Color",6);

        movieTvShowsAttributeMap.put("Genre",1);
        movieTvShowsAttributeMap.put("Price",2);
        movieTvShowsAttributeMap.put("Format",3);
        movieTvShowsAttributeMap.put("Show Type",4);
        movieTvShowsAttributeMap.put("Sub Genre",5);
        movieTvShowsAttributeMap.put("Specific Title",6);

        printersAttributeMap.put("Brand",1);
        printersAttributeMap.put("Price",2);
        printersAttributeMap.put("Printer Type",3);
        printersAttributeMap.put("Printer Subtype",4);
        printersAttributeMap.put("Scanner Type",5);
        printersAttributeMap.put("Printer Features",6);

        refrigeratorsAttributeMap.put("Brand",1);
        refrigeratorsAttributeMap.put("Price",2);
        refrigeratorsAttributeMap.put("Configuration",3);
        refrigeratorsAttributeMap.put("Compressor Size",4);
        refrigeratorsAttributeMap.put("Depth",5);
        refrigeratorsAttributeMap.put("Width",6);

        softwaresAttributeMap.put("Operating System",1);
        softwaresAttributeMap.put("Price",2);
        softwaresAttributeMap.put("Software Type",3);
        softwaresAttributeMap.put("Kind of Software",4);
        softwaresAttributeMap.put("Functionality",5);
        softwaresAttributeMap.put("More Functionality",6);




    }

}
