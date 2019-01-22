package com.mainiacs.saving.savingmainiacsapp;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by zax on 4/12/17
 */

public class FakeSocket {
    List<Charity> charities;

    public FakeSocket() {
        charities = new LinkedList<Charity>();
        MakeFakeCharities();
    }

    private void MakeFakeCharities() {
        charities.add(new Charity(0,"Bangor Homeless Shelter","199 State Street, Bangor ME 04410", 44.804980, -68.762183));
    }

    public List<Charity> GetCharityDataFromServer() {

        return charities;
    }

}
