//
//  TripNavigationVC.swift
//  iOS-viaPatron
//
//  Created by Liu Yuheng on 8/1/19.
//  Copyright © 2019 Liu Yuheng. All rights reserved.
//

import UIKit
import MapKit

class TripNavigationVC: UIViewController, MKMapViewDelegate, CLLocationManagerDelegate {
    
    @IBOutlet weak var mapView: MKMapView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setupMap()
    }

    func setupMap(){
        let locationManager = CLLocationManager()
        locationManager.requestAlwaysAuthorization()
        locationManager.requestWhenInUseAuthorization()
        if CLLocationManager.locationServicesEnabled(){
            locationManager.delegate = self
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.startUpdatingLocation()
        }
        
        mapView.delegate = self
        let initialCoordinate = locationManager.location?.coordinate
        let regionRadius: CLLocationDistance = 3000
        let coordinateRegion = MKCoordinateRegion(center: initialCoordinate!,
                                                  latitudinalMeters: regionRadius, longitudinalMeters: regionRadius)
        mapView.setRegion(coordinateRegion, animated: true)
        mapView.showsScale = true
        mapView.showsPointsOfInterest = true
        mapView.showsUserLocation = true
    }
}
