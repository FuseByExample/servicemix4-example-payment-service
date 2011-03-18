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

package com.fusesource.examples.payment_service.impl;

import com.fusesource.examples.payment_service.Payment;
import com.fusesource.examples.payment_service.types.TransferRequest;
import com.fusesource.examples.payment_service.types.TransferResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PaymentImpl implements Payment {
    private static final Log LOG = LogFactory.getLog(PaymentImpl.class);

    public TransferResponse transferFunds(TransferRequest payload) {
        TransferResponse response = new TransferResponse();

        LOG.info("Bank1: transferred amount " + payload.getAmount()
                + " from " + payload.getFrom()
                + " to " + payload.getTo());

        response.setReply("Bank1: OK");

        return response;
    }

    public void init() {
        LOG.info("Bank1: PaymentImpl started...");
    }
}
