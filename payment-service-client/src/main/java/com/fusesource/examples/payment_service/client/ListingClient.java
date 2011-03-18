/*
 * Copyright 2011 FuseSource
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.fusesource.examples.payment_service.client;

import com.fusesource.examples.listing_service.Listing;
import com.fusesource.examples.listing_service.ListingService;
import com.fusesource.examples.listing_service.types.ListingRequest;
import com.fusesource.examples.listing_service.types.ListingResponse;

import java.net.MalformedURLException;
import java.net.URL;

public class ListingClient {
    private static final String WSDL_URL = "http://localhost:9090/listingService?WSDL";

    public static void main(String args[]) throws MalformedURLException {
        Listing listing = new ListingService(new URL(WSDL_URL)).getListingPort();

        ListingRequest request = new ListingRequest();

        ListingResponse response = listing.discoverListings(request);

        System.out.println("Listing of Banks:");
        for (String bank : response.getBanks().getBank()) {
            System.out.println("  " + bank);
        }
    }
}
