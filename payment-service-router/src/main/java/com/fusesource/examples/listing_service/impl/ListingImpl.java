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

package com.fusesource.examples.listing_service.impl;

import com.fusesource.examples.listing_service.Listing;
import com.fusesource.examples.listing_service.types.ListingRequest;
import com.fusesource.examples.listing_service.types.ListingResponse;
import org.apache.camel.Property;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.springframework.osgi.context.BundleContextAware;

import java.util.List;

public class ListingImpl implements Listing, BundleContextAware {
    private static final Log LOG = LogFactory.getLog(ListingImpl.class);
    private static final String PAYMENT_SERVICE_INTERFACE = "com.fusesource.examples.payment_service.Payment";

    private BundleContext bundleContext;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public ListingResponse discoverListings(ListingRequest payload) {
        ListingResponse response = new ListingResponse();
        ListingResponse.Banks bankListing = new ListingResponse.Banks();
        List<String> banks = bankListing.getBank();

        try {
            ServiceReference[] references = bundleContext.getServiceReferences(PAYMENT_SERVICE_INTERFACE, null);

            if ((references != null) && (references.length > 0)) {
                for (ServiceReference ref : references) {
                    banks.add((String) ref.getProperty("NAME"));
                }
            } else {
                LOG.info("No Payment services exist");
            }
        } catch (InvalidSyntaxException e) {
            LOG.error("Error getting bank service names", e);
        }

        response.setBanks(bankListing);

        return response;
    }

    public String lookupBankNmrByName(@Property(value = "targetBank") String name) {
        String nmrString = null;

        try {
            ServiceReference[] references = bundleContext.getServiceReferences(PAYMENT_SERVICE_INTERFACE, "(NAME=" + name + ")");

            if ((references != null) && (references.length > 0)) {
                nmrString = "nmr:" + references[0].getProperty("NMR");

                LOG.info("routing to = " + nmrString);
            } else {
                throw new BankNotFoundException("Could not find bank service with name '" + name + "'");
            }
        } catch (InvalidSyntaxException e) {
            LOG.error("Error getting Bank service", e);
        }

        return nmrString;
    }
}
