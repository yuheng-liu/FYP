//
//  AWSAuthenticationManager.swift
//  iOS-viaPatron
//
//  Created by Liu Yuheng on 16/1/19.
//  Copyright © 2019 Liu Yuheng. All rights reserved.
//

import Foundation
import AWSMobileClient

class AWSAuthenticationManager {
    static let sharedInstance = AWSAuthenticationManager()
    
    func initialise(){
        // Initialisation of AWS User Authentication
        AWSMobileClient.sharedInstance().initialize { (userState, error) in
            if let userState = userState {
                print("UserState: \(userState.rawValue)")
            } else if let error = error {
                print("error: \(error.localizedDescription)")
            }
        }
    }
    
    func showSignIn(){
        if let navigationController = UIApplication.shared.keyWindow?.rootViewController as? UINavigationController {
            AWSMobileClient.sharedInstance().showSignIn(navigationController: navigationController, { (signInState, error) in
                if signInState != nil {
                    print("logged in!")
                } else {
                    print("error logging in: \(String(describing: error?.localizedDescription))")
                }
            })
        }
    }
    
    func signOut(){
        AWSMobileClient.sharedInstance().signOut()

        if !AWSMobileClient.sharedInstance().isSignedIn {
            if let topController = UIApplication.topViewController(){
                topController.dismiss(animated: true, completion: nil)
            }
            print("logged out!")
            showSignIn()
        }
    }
}
