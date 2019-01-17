//
//  TestingVC.swift
//  iOS-viaPatron
//
//  Created by Liu Yuheng on 5/1/19.
//  Copyright © 2019 Liu Yuheng. All rights reserved.
//

import UIKit

class TestingVC: UIViewController {
    
    var display = ""
    
    @IBOutlet weak var textDisplay: UILabel!
    
    @IBAction func onRunMutation(_ sender: Any) {
        AWSAPIManager.sharedInstance.runMutation()
        display.append("Mutation executed")
        textDisplay.text = display
    }
    
    @IBAction func onRunQuery(_ sender: Any) {
        AWSAPIManager.sharedInstance.runQuery()
        display.append("Query executed")
        textDisplay.text = display
    }
    
    @IBAction func onSuscribe(_ sender: Any) {
        AWSAPIManager.sharedInstance.subscribe()
        display.append("Suscribe executed")
        textDisplay.text = display
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
}

