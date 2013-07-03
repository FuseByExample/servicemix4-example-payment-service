This project is an example of using OSGi, Camel, and JBoss Fuse together.
A number of things are shown within this project:

* use of the ActiveMQ and activemq-camel component for inter OSGi bundle communication
* multiple front-end proxies (WS and batch file)
* bridging one way (fire and forget) messaging with request-response
* and much more...

The scenario is a payment transfer service where transfer requests can be made
either through a WS (SOAP/HTTP) interface or through batch files.

This solution is a bit over-engineered, but the goal of this effort is to
provide examples of best practices in creating applications using these
technologies. Following is a brief overview of the included modules.
Module Overview:

* payment-service-shared: contains wsdl and generated JAXB artifacts, providing
  a common data model for data moving through the system
* payment-service-bank: process the transfer request
* payment-service-ws: SOAP/HTTP request-response proxy for transfer service
* payment-service-batch: splits multiple requests within a file, and forwards
  them to the router. Adapts a one-way process (batch file) to a
  request-response service (transfer).

Requirements:

* JBoss Fuse 6.0.0 (http://www.jboss.org/jbossfuse)
* Maven 3.0 (http://maven.apache.org/)
* Java SE 6

To run:

1) Build this project so bundles are deployed into your local maven repo

    <project home> $ mvn clean install

2) Start JBoss Fuse (based on Apache ServiceMix 4.5)

    <JBoss Fuse home> $ bin/fuse

3) Add this projects features.xml config to JBoss Fuse from the Fuse
   Console (makes it easier to install bundles with all required dependencies)

    JBossFuse:karaf@root> features:addUrl mvn:org.fusesource.examples/payment-service-shared/1.4.0-SNAPSHOT/xml/features

4) Install the bundles.

   Note: payment-service-shared gets installed by the other features.

    JBossFuse:karaf@root> features:install payment-service-bank
    JBossFuse:karaf@root> features:install payment-service-ws
    JBossFuse:karaf@root> features:install payment-service-batch

   there is also a shortcut features that installs all the others

    JBossFuse:karaf@root> features:install payment-service-all

5) To test the batch file processing, there is an existing batch file in the
   payment-service-batch modules.

   Note: <JBoss Fuse Home>/tmp/file-in directory is created automatically by
         Camel within the payment-service-batch bundle.

    <project home> $ cp payment-service-batch/transfers.xml <JBoss Fuse home>/tmp/file-in

   To see what happened look at the JBoss Fuse log file, either from the console

    JBossFuse:karaf@root> log:display

   or from the command line

    <JBoss Fuse home> $ tail -f data/log/fuse.log

6) To test the WS, use your favorite WS tool (e.g. SoapUI) against the following
   WSDLs hosted by the payment-service-ws bundle -- http://localhost:9090/paymentService?WSDL

   you can also the payment-service-client, which shows using CXF generated
   client code

    payment-service/payment-service-client> mvn -PPayment

## Notes

* if you see linkage errors related to javax.activation.DataHandler, you may need to edit JBoss Fuse's
 `etc/jre.properties` file, and add javax.activation to the list of packages exported by the base
 bundle by uncommenting (remote the leading '#') the javax.activation line.

    jre-1.6= \  
      javax.accessibility, \  
      javax.activation;version="1.1", \  
      javax.activity, \  
      ....  

* In JBoss Fuse, make sure that you've added a admin/admin userid and password to
 `<JBoss Fuse Home>/etc/users.properties`, or update the included Camel route to the userid and
 password you have defined for the JBoss Fuse embedded ActiveMQ.

