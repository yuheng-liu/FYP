//
//  AWSAPIManager.swift
//  iOS-viaPatron
//
//  Created by Liu Yuheng on 17/1/19.
//  Copyright © 2019 Liu Yuheng. All rights reserved.
//

import Foundation
import AWSAppSync

class AWSAPIManager {
    static let sharedInstance = AWSAPIManager()
    var appSyncClient: AWSAppSyncClient?

    func initialise(){
        //You can choose your database location
        let databaseURL = URL(fileURLWithPath:NSTemporaryDirectory()).appendingPathComponent("database_name")
        
        do {
            //AppSync configuration & client initialization
            let appSyncConfig = try AWSAppSyncClientConfiguration(appSyncClientInfo: AWSAppSyncClientInfo(),databaseURL: databaseURL)
            appSyncClient = try AWSAppSyncClient(appSyncConfig: appSyncConfig)
        } catch {
            print("Error initializing appsync client. \(error)")
        }
    }
    
    func runMutation(){
        let mutationInput = CreateMyTypeInput(id: "0", title:"Realtime and Offline", content:"something")
        appSyncClient?.perform(mutation: CreateMyTypeMutation(input: mutationInput)) { (result, error) in
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
        appSyncClient?.fetch(query: ListMyTypesQuery()) {(result, error) in
            if error != nil {
                print(error?.localizedDescription ?? "")
                return
            }
            result?.data?.listMyTypes?.items!.forEach { print(($0?.title)! + " " + ($0?.content)!) }
        }
    }
    
    var discard: Cancellable?
    func subscribe() {
        do {
            discard = try appSyncClient?.subscribe(subscription: OnCreateMyTypeSubscription(), resultHandler: { (result, transaction, error) in
                if let result = result {
                    print(result.data!.onCreateMyType!.title + " " + result.data!.onCreateMyType!.content)
                } else if let error = error {
                    print(error.localizedDescription)
                }
            })
        } catch {
            print("Error starting subscription.")
        }
    }
}
