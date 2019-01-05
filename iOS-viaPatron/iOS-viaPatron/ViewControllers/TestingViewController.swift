//
//  TestingViewController.swift
//  iOS-viaPatron
//
//  Created by Liu Yuheng on 5/1/19.
//  Copyright © 2019 Liu Yuheng. All rights reserved.
//

import UIKit
import AWSAppSync

class TestingViewController: UIViewController {
    //Reference AppSync client
    var appSyncClient: AWSAppSyncClient?
    
    @IBAction func onRunMutation(_ sender: Any) {
        runMutation()
    }
    
    @IBAction func onRunQuery(_ sender: Any) {
        runQuery()
    }
    
    @IBAction func onSuscribe(_ sender: Any) {
        subscribe()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appSyncClient = appDelegate.appSyncClient
    }
    
    func runMutation(){
        let mutationInput = CreateTodoInput(name: "Use AppSync", description:"Realtime and Offline")
        appSyncClient?.perform(mutation: CreateTodoMutation(input: mutationInput)) { (result, error) in
            if let error = error as? AWSAppSyncClientError {
                print("Error occurred: \(error.localizedDescription )")
            }
            if let resultError = result?.errors {
                print("Error saving the item on server: \(resultError)")
                return
            }
        }
    }
    
    func runQuery(){
        appSyncClient?.fetch(query: ListTodosQuery()) {(result, error) in
            if error != nil {
                print(error?.localizedDescription ?? "")
                return
            }
            result?.data?.listTodos?.items!.forEach { print(($0?.name)! + " " + ($0?.description)!) }
        }
    }
    
    var discard: Cancellable?
    
    func subscribe() {
        do {
            discard = try appSyncClient?.subscribe(subscription: OnCreateTodoSubscription(), resultHandler: { (result, transaction, error) in
                if let result = result {
                    print(result.data!.onCreateTodo!.name + " " + result.data!.onCreateTodo!.description!)
                } else if let error = error {
                    print(error.localizedDescription)
                }
            })
        } catch {
            print("Error starting subscription.")
        }
    }
}

