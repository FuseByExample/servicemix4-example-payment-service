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

import com.fusesource.examples.payment_service.Payment;
import com.fusesource.examples.payment_service.PaymentService;
import com.fusesource.examples.payment_service.types.TransferRequest;
import com.fusesource.examples.payment_service.types.TransferResponse;

import java.net.MalformedURLException;
import java.net.URL;

public class PaymentClient {
    private static final String WSDL_URL = "http://localhost:9090/paymentService?WSDL";

    public static void main(String args[]) throws MalformedURLException {
        Payment payment = new PaymentService(new URL(WSDL_URL)).getPaymentPort();

        TransferRequest request = new TransferRequest();
        request.setBank("bank1");
        request.setFrom("Scott");
        request.setTo("Claus");
        request.setAmount("40.00");

        System.out.println("Request Bank = " + request.getBank());

        TransferResponse response = payment.transferFunds(request);

        System.out.println("Response = " + response.getReply());

        request.setBank("bank2");

        System.out.println("Request Bank = " + request.getBank());

        response = payment.transferFunds(request);

        System.out.println("Response = " + response.getReply());
    }
}
