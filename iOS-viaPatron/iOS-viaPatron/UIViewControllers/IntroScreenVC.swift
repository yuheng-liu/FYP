//
//  IntroScreenVC.swift
//  iOS-viaPatron
//
//  Created by Liu Yuheng on 1/1/19.
//  Copyright © 2019 Liu Yuheng. All rights reserved.
//

import UIKit

class IntroScreenVC: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        AWSAuthenticationManager.sharedInstance.showSignIn()
    }
}
