package com.checkmate.checkmate.FakeLocations;

import com.checkmate.checkmate.FakeLocations.Models.Location;

import java.util.Stack;

public class FakeLocations {
    public String mUser;
    public Stack<Location> mLocationsStack;

    public FakeLocations(String currentUser){
        mUser = currentUser;
        mLocationsStack = new Stack<Location>();
    }

    public void createLocationDate(){
        if(mUser == "bob"){
            mLocationsStack.push(new Location(60.608013,-120.335167));
            mLocationsStack.push(new Location(50.608013,-122.335167));
            mLocationsStack.push(new Location(47.608013,-122.335167));
        }else {
            mLocationsStack.push(new Location(70.608013,-120.335167));
            mLocationsStack.push(new Location(50.608013,-122.335167));
            mLocationsStack.push(new Location(47.608013,-122.335167));

        }
    }
}
