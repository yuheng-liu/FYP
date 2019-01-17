//  This file was automatically generated and should not be edited.

import AWSAppSync

public struct CreateMyTypeInput: GraphQLMapConvertible {
  public var graphQLMap: GraphQLMap

  public init(id: GraphQLID? = nil, title: String, content: String, price: Int? = nil, rating: Double? = nil) {
    graphQLMap = ["id": id, "title": title, "content": content, "price": price, "rating": rating]
  }

  public var id: GraphQLID? {
    get {
      return graphQLMap["id"] as! GraphQLID?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "id")
    }
  }

  public var title: String {
    get {
      return graphQLMap["title"] as! String
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "title")
    }
  }

  public var content: String {
    get {
      return graphQLMap["content"] as! String
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "content")
    }
  }

  public var price: Int? {
    get {
      return graphQLMap["price"] as! Int?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "price")
    }
  }

  public var rating: Double? {
    get {
      return graphQLMap["rating"] as! Double?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "rating")
    }
  }
}

public struct UpdateMyTypeInput: GraphQLMapConvertible {
  public var graphQLMap: GraphQLMap

  public init(id: GraphQLID, title: String? = nil, content: String? = nil, price: Int? = nil, rating: Double? = nil) {
    graphQLMap = ["id": id, "title": title, "content": content, "price": price, "rating": rating]
  }

  public var id: GraphQLID {
    get {
      return graphQLMap["id"] as! GraphQLID
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "id")
    }
  }

  public var title: String? {
    get {
      return graphQLMap["title"] as! String?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "title")
    }
  }

  public var content: String? {
    get {
      return graphQLMap["content"] as! String?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "content")
    }
  }

  public var price: Int? {
    get {
      return graphQLMap["price"] as! Int?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "price")
    }
  }

  public var rating: Double? {
    get {
      return graphQLMap["rating"] as! Double?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "rating")
    }
  }
}

public struct DeleteMyTypeInput: GraphQLMapConvertible {
  public var graphQLMap: GraphQLMap

  public init(id: GraphQLID? = nil) {
    graphQLMap = ["id": id]
  }

  public var id: GraphQLID? {
    get {
      return graphQLMap["id"] as! GraphQLID?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "id")
    }
  }
}

public struct ModelMyTypeFilterInput: GraphQLMapConvertible {
  public var graphQLMap: GraphQLMap

  public init(id: ModelIDFilterInput? = nil, title: ModelStringFilterInput? = nil, content: ModelStringFilterInput? = nil, price: ModelIntFilterInput? = nil, rating: ModelFloatFilterInput? = nil, and: [ModelMyTypeFilterInput?]? = nil, or: [ModelMyTypeFilterInput?]? = nil, not: ModelMyTypeFilterInput? = nil) {
    graphQLMap = ["id": id, "title": title, "content": content, "price": price, "rating": rating, "and": and, "or": or, "not": not]
  }

  public var id: ModelIDFilterInput? {
    get {
      return graphQLMap["id"] as! ModelIDFilterInput?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "id")
    }
  }

  public var title: ModelStringFilterInput? {
    get {
      return graphQLMap["title"] as! ModelStringFilterInput?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "title")
    }
  }

  public var content: ModelStringFilterInput? {
    get {
      return graphQLMap["content"] as! ModelStringFilterInput?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "content")
    }
  }

  public var price: ModelIntFilterInput? {
    get {
      return graphQLMap["price"] as! ModelIntFilterInput?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "price")
    }
  }

  public var rating: ModelFloatFilterInput? {
    get {
      return graphQLMap["rating"] as! ModelFloatFilterInput?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "rating")
    }
  }

  public var and: [ModelMyTypeFilterInput?]? {
    get {
      return graphQLMap["and"] as! [ModelMyTypeFilterInput?]?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "and")
    }
  }

  public var or: [ModelMyTypeFilterInput?]? {
    get {
      return graphQLMap["or"] as! [ModelMyTypeFilterInput?]?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "or")
    }
  }

  public var not: ModelMyTypeFilterInput? {
    get {
      return graphQLMap["not"] as! ModelMyTypeFilterInput?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "not")
    }
  }
}

public struct ModelIDFilterInput: GraphQLMapConvertible {
  public var graphQLMap: GraphQLMap

  public init(ne: GraphQLID? = nil, eq: GraphQLID? = nil, le: GraphQLID? = nil, lt: GraphQLID? = nil, ge: GraphQLID? = nil, gt: GraphQLID? = nil, contains: GraphQLID? = nil, notContains: GraphQLID? = nil, between: [GraphQLID?]? = nil, beginsWith: GraphQLID? = nil) {
    graphQLMap = ["ne": ne, "eq": eq, "le": le, "lt": lt, "ge": ge, "gt": gt, "contains": contains, "notContains": notContains, "between": between, "beginsWith": beginsWith]
  }

  public var ne: GraphQLID? {
    get {
      return graphQLMap["ne"] as! GraphQLID?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "ne")
    }
  }

  public var eq: GraphQLID? {
    get {
      return graphQLMap["eq"] as! GraphQLID?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "eq")
    }
  }

  public var le: GraphQLID? {
    get {
      return graphQLMap["le"] as! GraphQLID?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "le")
    }
  }

  public var lt: GraphQLID? {
    get {
      return graphQLMap["lt"] as! GraphQLID?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "lt")
    }
  }

  public var ge: GraphQLID? {
    get {
      return graphQLMap["ge"] as! GraphQLID?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "ge")
    }
  }

  public var gt: GraphQLID? {
    get {
      return graphQLMap["gt"] as! GraphQLID?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "gt")
    }
  }

  public var contains: GraphQLID? {
    get {
      return graphQLMap["contains"] as! GraphQLID?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "contains")
    }
  }

  public var notContains: GraphQLID? {
    get {
      return graphQLMap["notContains"] as! GraphQLID?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "notContains")
    }
  }

  public var between: [GraphQLID?]? {
    get {
      return graphQLMap["between"] as! [GraphQLID?]?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "between")
    }
  }

  public var beginsWith: GraphQLID? {
    get {
      return graphQLMap["beginsWith"] as! GraphQLID?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "beginsWith")
    }
  }
}

public struct ModelStringFilterInput: GraphQLMapConvertible {
  public var graphQLMap: GraphQLMap

  public init(ne: String? = nil, eq: String? = nil, le: String? = nil, lt: String? = nil, ge: String? = nil, gt: String? = nil, contains: String? = nil, notContains: String? = nil, between: [String?]? = nil, beginsWith: String? = nil) {
    graphQLMap = ["ne": ne, "eq": eq, "le": le, "lt": lt, "ge": ge, "gt": gt, "contains": contains, "notContains": notContains, "between": between, "beginsWith": beginsWith]
  }

  public var ne: String? {
    get {
      return graphQLMap["ne"] as! String?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "ne")
    }
  }

  public var eq: String? {
    get {
      return graphQLMap["eq"] as! String?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "eq")
    }
  }

  public var le: String? {
    get {
      return graphQLMap["le"] as! String?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "le")
    }
  }

  public var lt: String? {
    get {
      return graphQLMap["lt"] as! String?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "lt")
    }
  }

  public var ge: String? {
    get {
      return graphQLMap["ge"] as! String?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "ge")
    }
  }

  public var gt: String? {
    get {
      return graphQLMap["gt"] as! String?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "gt")
    }
  }

  public var contains: String? {
    get {
      return graphQLMap["contains"] as! String?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "contains")
    }
  }

  public var notContains: String? {
    get {
      return graphQLMap["notContains"] as! String?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "notContains")
    }
  }

  public var between: [String?]? {
    get {
      return graphQLMap["between"] as! [String?]?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "between")
    }
  }

  public var beginsWith: String? {
    get {
      return graphQLMap["beginsWith"] as! String?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "beginsWith")
    }
  }
}

public struct ModelIntFilterInput: GraphQLMapConvertible {
  public var graphQLMap: GraphQLMap

  public init(ne: Int? = nil, eq: Int? = nil, le: Int? = nil, lt: Int? = nil, ge: Int? = nil, gt: Int? = nil, contains: Int? = nil, notContains: Int? = nil, between: [Int?]? = nil) {
    graphQLMap = ["ne": ne, "eq": eq, "le": le, "lt": lt, "ge": ge, "gt": gt, "contains": contains, "notContains": notContains, "between": between]
  }

  public var ne: Int? {
    get {
      return graphQLMap["ne"] as! Int?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "ne")
    }
  }

  public var eq: Int? {
    get {
      return graphQLMap["eq"] as! Int?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "eq")
    }
  }

  public var le: Int? {
    get {
      return graphQLMap["le"] as! Int?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "le")
    }
  }

  public var lt: Int? {
    get {
      return graphQLMap["lt"] as! Int?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "lt")
    }
  }

  public var ge: Int? {
    get {
      return graphQLMap["ge"] as! Int?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "ge")
    }
  }

  public var gt: Int? {
    get {
      return graphQLMap["gt"] as! Int?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "gt")
    }
  }

  public var contains: Int? {
    get {
      return graphQLMap["contains"] as! Int?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "contains")
    }
  }

  public var notContains: Int? {
    get {
      return graphQLMap["notContains"] as! Int?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "notContains")
    }
  }

  public var between: [Int?]? {
    get {
      return graphQLMap["between"] as! [Int?]?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "between")
    }
  }
}

public struct ModelFloatFilterInput: GraphQLMapConvertible {
  public var graphQLMap: GraphQLMap

  public init(ne: Double? = nil, eq: Double? = nil, le: Double? = nil, lt: Double? = nil, ge: Double? = nil, gt: Double? = nil, contains: Double? = nil, notContains: Double? = nil, between: [Double?]? = nil) {
    graphQLMap = ["ne": ne, "eq": eq, "le": le, "lt": lt, "ge": ge, "gt": gt, "contains": contains, "notContains": notContains, "between": between]
  }

  public var ne: Double? {
    get {
      return graphQLMap["ne"] as! Double?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "ne")
    }
  }

  public var eq: Double? {
    get {
      return graphQLMap["eq"] as! Double?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "eq")
    }
  }

  public var le: Double? {
    get {
      return graphQLMap["le"] as! Double?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "le")
    }
  }

  public var lt: Double? {
    get {
      return graphQLMap["lt"] as! Double?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "lt")
    }
  }

  public var ge: Double? {
    get {
      return graphQLMap["ge"] as! Double?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "ge")
    }
  }

  public var gt: Double? {
    get {
      return graphQLMap["gt"] as! Double?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "gt")
    }
  }

  public var contains: Double? {
    get {
      return graphQLMap["contains"] as! Double?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "contains")
    }
  }

  public var notContains: Double? {
    get {
      return graphQLMap["notContains"] as! Double?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "notContains")
    }
  }

  public var between: [Double?]? {
    get {
      return graphQLMap["between"] as! [Double?]?
    }
    set {
      graphQLMap.updateValue(newValue, forKey: "between")
    }
  }
}

public final class CreateMyTypeMutation: GraphQLMutation {
  public static let operationString =
    "mutation CreateMyType($input: CreateMyTypeInput!) {\n  createMyType(input: $input) {\n    __typename\n    id\n    title\n    content\n    price\n    rating\n  }\n}"

  public var input: CreateMyTypeInput

  public init(input: CreateMyTypeInput) {
    self.input = input
  }

  public var variables: GraphQLMap? {
    return ["input": input]
  }

  public struct Data: GraphQLSelectionSet {
    public static let possibleTypes = ["Mutation"]

    public static let selections: [GraphQLSelection] = [
      GraphQLField("createMyType", arguments: ["input": GraphQLVariable("input")], type: .object(CreateMyType.selections)),
    ]

    public var snapshot: Snapshot

    public init(snapshot: Snapshot) {
      self.snapshot = snapshot
    }

    public init(createMyType: CreateMyType? = nil) {
      self.init(snapshot: ["__typename": "Mutation", "createMyType": createMyType.flatMap { $0.snapshot }])
    }

    public var createMyType: CreateMyType? {
      get {
        return (snapshot["createMyType"] as? Snapshot).flatMap { CreateMyType(snapshot: $0) }
      }
      set {
        snapshot.updateValue(newValue?.snapshot, forKey: "createMyType")
      }
    }

    public struct CreateMyType: GraphQLSelectionSet {
      public static let possibleTypes = ["MyType"]

      public static let selections: [GraphQLSelection] = [
        GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
        GraphQLField("id", type: .nonNull(.scalar(GraphQLID.self))),
        GraphQLField("title", type: .nonNull(.scalar(String.self))),
        GraphQLField("content", type: .nonNull(.scalar(String.self))),
        GraphQLField("price", type: .scalar(Int.self)),
        GraphQLField("rating", type: .scalar(Double.self)),
      ]

      public var snapshot: Snapshot

      public init(snapshot: Snapshot) {
        self.snapshot = snapshot
      }

      public init(id: GraphQLID, title: String, content: String, price: Int? = nil, rating: Double? = nil) {
        self.init(snapshot: ["__typename": "MyType", "id": id, "title": title, "content": content, "price": price, "rating": rating])
      }

      public var __typename: String {
        get {
          return snapshot["__typename"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "__typename")
        }
      }

      public var id: GraphQLID {
        get {
          return snapshot["id"]! as! GraphQLID
        }
        set {
          snapshot.updateValue(newValue, forKey: "id")
        }
      }

      public var title: String {
        get {
          return snapshot["title"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "title")
        }
      }

      public var content: String {
        get {
          return snapshot["content"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "content")
        }
      }

      public var price: Int? {
        get {
          return snapshot["price"] as? Int
        }
        set {
          snapshot.updateValue(newValue, forKey: "price")
        }
      }

      public var rating: Double? {
        get {
          return snapshot["rating"] as? Double
        }
        set {
          snapshot.updateValue(newValue, forKey: "rating")
        }
      }
    }
  }
}

public final class UpdateMyTypeMutation: GraphQLMutation {
  public static let operationString =
    "mutation UpdateMyType($input: UpdateMyTypeInput!) {\n  updateMyType(input: $input) {\n    __typename\n    id\n    title\n    content\n    price\n    rating\n  }\n}"

  public var input: UpdateMyTypeInput

  public init(input: UpdateMyTypeInput) {
    self.input = input
  }

  public var variables: GraphQLMap? {
    return ["input": input]
  }

  public struct Data: GraphQLSelectionSet {
    public static let possibleTypes = ["Mutation"]

    public static let selections: [GraphQLSelection] = [
      GraphQLField("updateMyType", arguments: ["input": GraphQLVariable("input")], type: .object(UpdateMyType.selections)),
    ]

    public var snapshot: Snapshot

    public init(snapshot: Snapshot) {
      self.snapshot = snapshot
    }

    public init(updateMyType: UpdateMyType? = nil) {
      self.init(snapshot: ["__typename": "Mutation", "updateMyType": updateMyType.flatMap { $0.snapshot }])
    }

    public var updateMyType: UpdateMyType? {
      get {
        return (snapshot["updateMyType"] as? Snapshot).flatMap { UpdateMyType(snapshot: $0) }
      }
      set {
        snapshot.updateValue(newValue?.snapshot, forKey: "updateMyType")
      }
    }

    public struct UpdateMyType: GraphQLSelectionSet {
      public static let possibleTypes = ["MyType"]

      public static let selections: [GraphQLSelection] = [
        GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
        GraphQLField("id", type: .nonNull(.scalar(GraphQLID.self))),
        GraphQLField("title", type: .nonNull(.scalar(String.self))),
        GraphQLField("content", type: .nonNull(.scalar(String.self))),
        GraphQLField("price", type: .scalar(Int.self)),
        GraphQLField("rating", type: .scalar(Double.self)),
      ]

      public var snapshot: Snapshot

      public init(snapshot: Snapshot) {
        self.snapshot = snapshot
      }

      public init(id: GraphQLID, title: String, content: String, price: Int? = nil, rating: Double? = nil) {
        self.init(snapshot: ["__typename": "MyType", "id": id, "title": title, "content": content, "price": price, "rating": rating])
      }

      public var __typename: String {
        get {
          return snapshot["__typename"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "__typename")
        }
      }

      public var id: GraphQLID {
        get {
          return snapshot["id"]! as! GraphQLID
        }
        set {
          snapshot.updateValue(newValue, forKey: "id")
        }
      }

      public var title: String {
        get {
          return snapshot["title"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "title")
        }
      }

      public var content: String {
        get {
          return snapshot["content"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "content")
        }
      }

      public var price: Int? {
        get {
          return snapshot["price"] as? Int
        }
        set {
          snapshot.updateValue(newValue, forKey: "price")
        }
      }

      public var rating: Double? {
        get {
          return snapshot["rating"] as? Double
        }
        set {
          snapshot.updateValue(newValue, forKey: "rating")
        }
      }
    }
  }
}

public final class DeleteMyTypeMutation: GraphQLMutation {
  public static let operationString =
    "mutation DeleteMyType($input: DeleteMyTypeInput!) {\n  deleteMyType(input: $input) {\n    __typename\n    id\n    title\n    content\n    price\n    rating\n  }\n}"

  public var input: DeleteMyTypeInput

  public init(input: DeleteMyTypeInput) {
    self.input = input
  }

  public var variables: GraphQLMap? {
    return ["input": input]
  }

  public struct Data: GraphQLSelectionSet {
    public static let possibleTypes = ["Mutation"]

    public static let selections: [GraphQLSelection] = [
      GraphQLField("deleteMyType", arguments: ["input": GraphQLVariable("input")], type: .object(DeleteMyType.selections)),
    ]

    public var snapshot: Snapshot

    public init(snapshot: Snapshot) {
      self.snapshot = snapshot
    }

    public init(deleteMyType: DeleteMyType? = nil) {
      self.init(snapshot: ["__typename": "Mutation", "deleteMyType": deleteMyType.flatMap { $0.snapshot }])
    }

    public var deleteMyType: DeleteMyType? {
      get {
        return (snapshot["deleteMyType"] as? Snapshot).flatMap { DeleteMyType(snapshot: $0) }
      }
      set {
        snapshot.updateValue(newValue?.snapshot, forKey: "deleteMyType")
      }
    }

    public struct DeleteMyType: GraphQLSelectionSet {
      public static let possibleTypes = ["MyType"]

      public static let selections: [GraphQLSelection] = [
        GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
        GraphQLField("id", type: .nonNull(.scalar(GraphQLID.self))),
        GraphQLField("title", type: .nonNull(.scalar(String.self))),
        GraphQLField("content", type: .nonNull(.scalar(String.self))),
        GraphQLField("price", type: .scalar(Int.self)),
        GraphQLField("rating", type: .scalar(Double.self)),
      ]

      public var snapshot: Snapshot

      public init(snapshot: Snapshot) {
        self.snapshot = snapshot
      }

      public init(id: GraphQLID, title: String, content: String, price: Int? = nil, rating: Double? = nil) {
        self.init(snapshot: ["__typename": "MyType", "id": id, "title": title, "content": content, "price": price, "rating": rating])
      }

      public var __typename: String {
        get {
          return snapshot["__typename"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "__typename")
        }
      }

      public var id: GraphQLID {
        get {
          return snapshot["id"]! as! GraphQLID
        }
        set {
          snapshot.updateValue(newValue, forKey: "id")
        }
      }

      public var title: String {
        get {
          return snapshot["title"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "title")
        }
      }

      public var content: String {
        get {
          return snapshot["content"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "content")
        }
      }

      public var price: Int? {
        get {
          return snapshot["price"] as? Int
        }
        set {
          snapshot.updateValue(newValue, forKey: "price")
        }
      }

      public var rating: Double? {
        get {
          return snapshot["rating"] as? Double
        }
        set {
          snapshot.updateValue(newValue, forKey: "rating")
        }
      }
    }
  }
}

public final class GetMyTypeQuery: GraphQLQuery {
  public static let operationString =
    "query GetMyType($id: ID!) {\n  getMyType(id: $id) {\n    __typename\n    id\n    title\n    content\n    price\n    rating\n  }\n}"

  public var id: GraphQLID

  public init(id: GraphQLID) {
    self.id = id
  }

  public var variables: GraphQLMap? {
    return ["id": id]
  }

  public struct Data: GraphQLSelectionSet {
    public static let possibleTypes = ["Query"]

    public static let selections: [GraphQLSelection] = [
      GraphQLField("getMyType", arguments: ["id": GraphQLVariable("id")], type: .object(GetMyType.selections)),
    ]

    public var snapshot: Snapshot

    public init(snapshot: Snapshot) {
      self.snapshot = snapshot
    }

    public init(getMyType: GetMyType? = nil) {
      self.init(snapshot: ["__typename": "Query", "getMyType": getMyType.flatMap { $0.snapshot }])
    }

    public var getMyType: GetMyType? {
      get {
        return (snapshot["getMyType"] as? Snapshot).flatMap { GetMyType(snapshot: $0) }
      }
      set {
        snapshot.updateValue(newValue?.snapshot, forKey: "getMyType")
      }
    }

    public struct GetMyType: GraphQLSelectionSet {
      public static let possibleTypes = ["MyType"]

      public static let selections: [GraphQLSelection] = [
        GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
        GraphQLField("id", type: .nonNull(.scalar(GraphQLID.self))),
        GraphQLField("title", type: .nonNull(.scalar(String.self))),
        GraphQLField("content", type: .nonNull(.scalar(String.self))),
        GraphQLField("price", type: .scalar(Int.self)),
        GraphQLField("rating", type: .scalar(Double.self)),
      ]

      public var snapshot: Snapshot

      public init(snapshot: Snapshot) {
        self.snapshot = snapshot
      }

      public init(id: GraphQLID, title: String, content: String, price: Int? = nil, rating: Double? = nil) {
        self.init(snapshot: ["__typename": "MyType", "id": id, "title": title, "content": content, "price": price, "rating": rating])
      }

      public var __typename: String {
        get {
          return snapshot["__typename"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "__typename")
        }
      }

      public var id: GraphQLID {
        get {
          return snapshot["id"]! as! GraphQLID
        }
        set {
          snapshot.updateValue(newValue, forKey: "id")
        }
      }

      public var title: String {
        get {
          return snapshot["title"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "title")
        }
      }

      public var content: String {
        get {
          return snapshot["content"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "content")
        }
      }

      public var price: Int? {
        get {
          return snapshot["price"] as? Int
        }
        set {
          snapshot.updateValue(newValue, forKey: "price")
        }
      }

      public var rating: Double? {
        get {
          return snapshot["rating"] as? Double
        }
        set {
          snapshot.updateValue(newValue, forKey: "rating")
        }
      }
    }
  }
}

public final class ListMyTypesQuery: GraphQLQuery {
  public static let operationString =
    "query ListMyTypes($filter: ModelMyTypeFilterInput, $limit: Int, $nextToken: String) {\n  listMyTypes(filter: $filter, limit: $limit, nextToken: $nextToken) {\n    __typename\n    items {\n      __typename\n      id\n      title\n      content\n      price\n      rating\n    }\n    nextToken\n  }\n}"

  public var filter: ModelMyTypeFilterInput?
  public var limit: Int?
  public var nextToken: String?

  public init(filter: ModelMyTypeFilterInput? = nil, limit: Int? = nil, nextToken: String? = nil) {
    self.filter = filter
    self.limit = limit
    self.nextToken = nextToken
  }

  public var variables: GraphQLMap? {
    return ["filter": filter, "limit": limit, "nextToken": nextToken]
  }

  public struct Data: GraphQLSelectionSet {
    public static let possibleTypes = ["Query"]

    public static let selections: [GraphQLSelection] = [
      GraphQLField("listMyTypes", arguments: ["filter": GraphQLVariable("filter"), "limit": GraphQLVariable("limit"), "nextToken": GraphQLVariable("nextToken")], type: .object(ListMyType.selections)),
    ]

    public var snapshot: Snapshot

    public init(snapshot: Snapshot) {
      self.snapshot = snapshot
    }

    public init(listMyTypes: ListMyType? = nil) {
      self.init(snapshot: ["__typename": "Query", "listMyTypes": listMyTypes.flatMap { $0.snapshot }])
    }

    public var listMyTypes: ListMyType? {
      get {
        return (snapshot["listMyTypes"] as? Snapshot).flatMap { ListMyType(snapshot: $0) }
      }
      set {
        snapshot.updateValue(newValue?.snapshot, forKey: "listMyTypes")
      }
    }

    public struct ListMyType: GraphQLSelectionSet {
      public static let possibleTypes = ["ModelMyTypeConnection"]

      public static let selections: [GraphQLSelection] = [
        GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
        GraphQLField("items", type: .list(.object(Item.selections))),
        GraphQLField("nextToken", type: .scalar(String.self)),
      ]

      public var snapshot: Snapshot

      public init(snapshot: Snapshot) {
        self.snapshot = snapshot
      }

      public init(items: [Item?]? = nil, nextToken: String? = nil) {
        self.init(snapshot: ["__typename": "ModelMyTypeConnection", "items": items.flatMap { $0.map { $0.flatMap { $0.snapshot } } }, "nextToken": nextToken])
      }

      public var __typename: String {
        get {
          return snapshot["__typename"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "__typename")
        }
      }

      public var items: [Item?]? {
        get {
          return (snapshot["items"] as? [Snapshot?]).flatMap { $0.map { $0.flatMap { Item(snapshot: $0) } } }
        }
        set {
          snapshot.updateValue(newValue.flatMap { $0.map { $0.flatMap { $0.snapshot } } }, forKey: "items")
        }
      }

      public var nextToken: String? {
        get {
          return snapshot["nextToken"] as? String
        }
        set {
          snapshot.updateValue(newValue, forKey: "nextToken")
        }
      }

      public struct Item: GraphQLSelectionSet {
        public static let possibleTypes = ["MyType"]

        public static let selections: [GraphQLSelection] = [
          GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
          GraphQLField("id", type: .nonNull(.scalar(GraphQLID.self))),
          GraphQLField("title", type: .nonNull(.scalar(String.self))),
          GraphQLField("content", type: .nonNull(.scalar(String.self))),
          GraphQLField("price", type: .scalar(Int.self)),
          GraphQLField("rating", type: .scalar(Double.self)),
        ]

        public var snapshot: Snapshot

        public init(snapshot: Snapshot) {
          self.snapshot = snapshot
        }

        public init(id: GraphQLID, title: String, content: String, price: Int? = nil, rating: Double? = nil) {
          self.init(snapshot: ["__typename": "MyType", "id": id, "title": title, "content": content, "price": price, "rating": rating])
        }

        public var __typename: String {
          get {
            return snapshot["__typename"]! as! String
          }
          set {
            snapshot.updateValue(newValue, forKey: "__typename")
          }
        }

        public var id: GraphQLID {
          get {
            return snapshot["id"]! as! GraphQLID
          }
          set {
            snapshot.updateValue(newValue, forKey: "id")
          }
        }

        public var title: String {
          get {
            return snapshot["title"]! as! String
          }
          set {
            snapshot.updateValue(newValue, forKey: "title")
          }
        }

        public var content: String {
          get {
            return snapshot["content"]! as! String
          }
          set {
            snapshot.updateValue(newValue, forKey: "content")
          }
        }

        public var price: Int? {
          get {
            return snapshot["price"] as? Int
          }
          set {
            snapshot.updateValue(newValue, forKey: "price")
          }
        }

        public var rating: Double? {
          get {
            return snapshot["rating"] as? Double
          }
          set {
            snapshot.updateValue(newValue, forKey: "rating")
          }
        }
      }
    }
  }
}

public final class OnCreateMyTypeSubscription: GraphQLSubscription {
  public static let operationString =
    "subscription OnCreateMyType {\n  onCreateMyType {\n    __typename\n    id\n    title\n    content\n    price\n    rating\n  }\n}"

  public init() {
  }

  public struct Data: GraphQLSelectionSet {
    public static let possibleTypes = ["Subscription"]

    public static let selections: [GraphQLSelection] = [
      GraphQLField("onCreateMyType", type: .object(OnCreateMyType.selections)),
    ]

    public var snapshot: Snapshot

    public init(snapshot: Snapshot) {
      self.snapshot = snapshot
    }

    public init(onCreateMyType: OnCreateMyType? = nil) {
      self.init(snapshot: ["__typename": "Subscription", "onCreateMyType": onCreateMyType.flatMap { $0.snapshot }])
    }

    public var onCreateMyType: OnCreateMyType? {
      get {
        return (snapshot["onCreateMyType"] as? Snapshot).flatMap { OnCreateMyType(snapshot: $0) }
      }
      set {
        snapshot.updateValue(newValue?.snapshot, forKey: "onCreateMyType")
      }
    }

    public struct OnCreateMyType: GraphQLSelectionSet {
      public static let possibleTypes = ["MyType"]

      public static let selections: [GraphQLSelection] = [
        GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
        GraphQLField("id", type: .nonNull(.scalar(GraphQLID.self))),
        GraphQLField("title", type: .nonNull(.scalar(String.self))),
        GraphQLField("content", type: .nonNull(.scalar(String.self))),
        GraphQLField("price", type: .scalar(Int.self)),
        GraphQLField("rating", type: .scalar(Double.self)),
      ]

      public var snapshot: Snapshot

      public init(snapshot: Snapshot) {
        self.snapshot = snapshot
      }

      public init(id: GraphQLID, title: String, content: String, price: Int? = nil, rating: Double? = nil) {
        self.init(snapshot: ["__typename": "MyType", "id": id, "title": title, "content": content, "price": price, "rating": rating])
      }

      public var __typename: String {
        get {
          return snapshot["__typename"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "__typename")
        }
      }

      public var id: GraphQLID {
        get {
          return snapshot["id"]! as! GraphQLID
        }
        set {
          snapshot.updateValue(newValue, forKey: "id")
        }
      }

      public var title: String {
        get {
          return snapshot["title"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "title")
        }
      }

      public var content: String {
        get {
          return snapshot["content"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "content")
        }
      }

      public var price: Int? {
        get {
          return snapshot["price"] as? Int
        }
        set {
          snapshot.updateValue(newValue, forKey: "price")
        }
      }

      public var rating: Double? {
        get {
          return snapshot["rating"] as? Double
        }
        set {
          snapshot.updateValue(newValue, forKey: "rating")
        }
      }
    }
  }
}

public final class OnUpdateMyTypeSubscription: GraphQLSubscription {
  public static let operationString =
    "subscription OnUpdateMyType {\n  onUpdateMyType {\n    __typename\n    id\n    title\n    content\n    price\n    rating\n  }\n}"

  public init() {
  }

  public struct Data: GraphQLSelectionSet {
    public static let possibleTypes = ["Subscription"]

    public static let selections: [GraphQLSelection] = [
      GraphQLField("onUpdateMyType", type: .object(OnUpdateMyType.selections)),
    ]

    public var snapshot: Snapshot

    public init(snapshot: Snapshot) {
      self.snapshot = snapshot
    }

    public init(onUpdateMyType: OnUpdateMyType? = nil) {
      self.init(snapshot: ["__typename": "Subscription", "onUpdateMyType": onUpdateMyType.flatMap { $0.snapshot }])
    }

    public var onUpdateMyType: OnUpdateMyType? {
      get {
        return (snapshot["onUpdateMyType"] as? Snapshot).flatMap { OnUpdateMyType(snapshot: $0) }
      }
      set {
        snapshot.updateValue(newValue?.snapshot, forKey: "onUpdateMyType")
      }
    }

    public struct OnUpdateMyType: GraphQLSelectionSet {
      public static let possibleTypes = ["MyType"]

      public static let selections: [GraphQLSelection] = [
        GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
        GraphQLField("id", type: .nonNull(.scalar(GraphQLID.self))),
        GraphQLField("title", type: .nonNull(.scalar(String.self))),
        GraphQLField("content", type: .nonNull(.scalar(String.self))),
        GraphQLField("price", type: .scalar(Int.self)),
        GraphQLField("rating", type: .scalar(Double.self)),
      ]

      public var snapshot: Snapshot

      public init(snapshot: Snapshot) {
        self.snapshot = snapshot
      }

      public init(id: GraphQLID, title: String, content: String, price: Int? = nil, rating: Double? = nil) {
        self.init(snapshot: ["__typename": "MyType", "id": id, "title": title, "content": content, "price": price, "rating": rating])
      }

      public var __typename: String {
        get {
          return snapshot["__typename"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "__typename")
        }
      }

      public var id: GraphQLID {
        get {
          return snapshot["id"]! as! GraphQLID
        }
        set {
          snapshot.updateValue(newValue, forKey: "id")
        }
      }

      public var title: String {
        get {
          return snapshot["title"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "title")
        }
      }

      public var content: String {
        get {
          return snapshot["content"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "content")
        }
      }

      public var price: Int? {
        get {
          return snapshot["price"] as? Int
        }
        set {
          snapshot.updateValue(newValue, forKey: "price")
        }
      }

      public var rating: Double? {
        get {
          return snapshot["rating"] as? Double
        }
        set {
          snapshot.updateValue(newValue, forKey: "rating")
        }
      }
    }
  }
}

public final class OnDeleteMyTypeSubscription: GraphQLSubscription {
  public static let operationString =
    "subscription OnDeleteMyType {\n  onDeleteMyType {\n    __typename\n    id\n    title\n    content\n    price\n    rating\n  }\n}"

  public init() {
  }

  public struct Data: GraphQLSelectionSet {
    public static let possibleTypes = ["Subscription"]

    public static let selections: [GraphQLSelection] = [
      GraphQLField("onDeleteMyType", type: .object(OnDeleteMyType.selections)),
    ]

    public var snapshot: Snapshot

    public init(snapshot: Snapshot) {
      self.snapshot = snapshot
    }

    public init(onDeleteMyType: OnDeleteMyType? = nil) {
      self.init(snapshot: ["__typename": "Subscription", "onDeleteMyType": onDeleteMyType.flatMap { $0.snapshot }])
    }

    public var onDeleteMyType: OnDeleteMyType? {
      get {
        return (snapshot["onDeleteMyType"] as? Snapshot).flatMap { OnDeleteMyType(snapshot: $0) }
      }
      set {
        snapshot.updateValue(newValue?.snapshot, forKey: "onDeleteMyType")
      }
    }

    public struct OnDeleteMyType: GraphQLSelectionSet {
      public static let possibleTypes = ["MyType"]

      public static let selections: [GraphQLSelection] = [
        GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
        GraphQLField("id", type: .nonNull(.scalar(GraphQLID.self))),
        GraphQLField("title", type: .nonNull(.scalar(String.self))),
        GraphQLField("content", type: .nonNull(.scalar(String.self))),
        GraphQLField("price", type: .scalar(Int.self)),
        GraphQLField("rating", type: .scalar(Double.self)),
      ]

      public var snapshot: Snapshot

      public init(snapshot: Snapshot) {
        self.snapshot = snapshot
      }

      public init(id: GraphQLID, title: String, content: String, price: Int? = nil, rating: Double? = nil) {
        self.init(snapshot: ["__typename": "MyType", "id": id, "title": title, "content": content, "price": price, "rating": rating])
      }

      public var __typename: String {
        get {
          return snapshot["__typename"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "__typename")
        }
      }

      public var id: GraphQLID {
        get {
          return snapshot["id"]! as! GraphQLID
        }
        set {
          snapshot.updateValue(newValue, forKey: "id")
        }
      }

      public var title: String {
        get {
          return snapshot["title"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "title")
        }
      }

      public var content: String {
        get {
          return snapshot["content"]! as! String
        }
        set {
          snapshot.updateValue(newValue, forKey: "content")
        }
      }

      public var price: Int? {
        get {
          return snapshot["price"] as? Int
        }
        set {
          snapshot.updateValue(newValue, forKey: "price")
        }
      }

      public var rating: Double? {
        get {
          return snapshot["rating"] as? Double
        }
        set {
          snapshot.updateValue(newValue, forKey: "rating")
        }
      }
    }
  }
}