//
//  ProfileVC.swift
//  iOS-viaPatron
//
//  Created by Liu Yuheng on 8/1/19.
//  Copyright © 2019 Liu Yuheng. All rights reserved.
//

import UIKit
import AWSMobileClient

class ProfileVC: UIViewController {
    
    @IBAction func onSignOut(_ sender: Any) {
        AWSAuthenticationManager.sharedInstance.signOut()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
    }
}
