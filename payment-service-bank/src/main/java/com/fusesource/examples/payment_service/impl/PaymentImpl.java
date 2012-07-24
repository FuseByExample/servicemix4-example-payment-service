/*
 * Copyright 2012 FuseSource
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentImpl implements Payment {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentImpl.class);

    public TransferResponse transferFunds(TransferRequest payload) {
        TransferResponse response = new TransferResponse();

        LOG.info("Bank: transferred amount {} from {} to {}", new Object[]{payload.getAmount(), payload.getFrom(), payload.getTo()});

        response.setReply("Bank: OK");

        return response;
    }

    public void init() {
        LOG.info("Bank: PaymentImpl started...");
    }
}
