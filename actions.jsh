
//Function<Room, Room> takeSword = x -> x;


Function<Room, Room> takeSword = 
    x -> {
    if (!x.isSword()) { 
      System.out.println("--> There is no sword.");
      return x;
    } else if (x.isSwordPickup()) { 
      System.out.println("--> You already have sword.");
      return x;
    } else {
      System.out.println("--> You have taken sword.");
      return x.pickSword();
    }};

Function<Room, Room> dropSword =
    x -> { 
    System.out.println("--> You have dropped sword."); 
    return x.droppingSword(); };
        


Function<Room, Room> killTroll = 
    x -> {
    if (!x.isTroll()) {
        System.out.println("--> There is no troll.");
        return x;
    } else if (!x.isSwordPickup()) {
        System.out.println("--> You have no sword.");
        return x;
    } else {
        System.out.println("--> Troll is killed.");
        return x.killTroll();
    }};
