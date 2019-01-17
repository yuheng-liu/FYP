//
//  UIApplicationExtension.swift
//  iOS-viaPatron
//
//  Created by Liu Yuheng on 16/1/19.
//  Copyright © 2019 Liu Yuheng. All rights reserved.
//

import UIKit

// Extension for getting the top view controller
// Use the following to get top controller
// if let topController = UIApplication.topViewController() {}
extension UIApplication {
    class func topViewController(controller: UIViewController? = UIApplication.shared.keyWindow?.rootViewController) -> UIViewController? {
        if let navigationController = controller as? UINavigationController {
            return topViewController(controller: navigationController.visibleViewController)
        }
        if let tabController = controller as? UITabBarController {
            if let selected = tabController.selectedViewController {
                return topViewController(controller: selected)
            }
        }
        if let presented = controller?.presentedViewController {
            return topViewController(controller: presented)
        }
        return controller
    }
}
