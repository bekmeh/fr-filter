# fr-filter

This is a Filter API which determines whether a resource matches certain criteria.

## Example Usage

#### Method 1
```java
Filter filter = new Filter("age", Comparator.LT, 20).and("height", Comparator.GT, 5.5);
Resource user = new Resource();
user.put("age", 15);
user.put("height", 6);
filter.matches(user); // Should evaluate to true
```
In this case, the methods can be chained indefinitely.

#### Method 2
```java
Filter filter = new Filter(
                          new OrNode(
                                      new NotNode(new LessThanNode("age", 20)), 
                                      new GreaterThanNode("height", 5.5)));
Resource user = new Resource();
user.put("age", 25);
user.put("height", 4);
filter.matches(user); // Should evaluate to true
```

## Approach

My initial approach was a simplistic one (see [first commit](https://github.com/bekmeh/fr-filter/commit/4f5d4e32b7c359a5d6133ffebdb2c45fd1c54478)). However, the code became overly complex and it did not take into account order of precedence. 

I decided to model the problem using a binary tree instead, because it better reflects the structure of boolean logic. Here, the evaluate() method determines the boolean values all the way from the bottom branch back to the top. 
The `Filter` class provides a nicer interface for creating and evaluating `FilterNode`s, and allows the use of chained and()/or()/not() methods. 
These are used to place nodes in the tree based on the [order of precedence of the operator](https://en.wikipedia.org/wiki/Logical_connective#Order_of_precedence).

## References

https://en.wikipedia.org/wiki/Logical_connective

https://en.wikipedia.org/wiki/Binary_tree
