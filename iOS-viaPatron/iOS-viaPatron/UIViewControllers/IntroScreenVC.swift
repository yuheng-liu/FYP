//
//  IntroScreenVC.swift
//  iOS-viaPatron
//
//  Created by Liu Yuheng on 1/1/19.
//  Copyright © 2019 Liu Yuheng. All rights reserved.
//

import UIKit

class IntroScreenVC: UIViewController {
    
    @IBAction func toMainScreen(_ sender: Any) {
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        AWSAuthenticationManager.sharedInstance.showSignIn()
    }
}
