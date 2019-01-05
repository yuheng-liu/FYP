//
//  MainViewController.swift
//  iOS-viaPatron
//
//  Created by Liu Yuheng on 1/1/19.
//  Copyright © 2019 Liu Yuheng. All rights reserved.
//

import UIKit
import AWSMobileClient

class MainViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        AWSMobileClient.sharedInstance().showSignIn(navigationController: self.navigationController!, { (signInState, error) in
            if signInState != nil {
                print("logged in!")
            } else {
                print("error logging in: \(String(describing: error?.localizedDescription))")
            }
        })
    }
}

