================================================ Purpose                ================================================

Show the simplest possible "Hello, World" J2EE web app, and a more realistic webapp that lets a user edit a single
street address.

================================================ Build                  ================================================

There is no build, the project is intended to only run locally.

================================================ Run                    ================================================

Simply execute the me.bantling.j2ee.basics.run.J2EEBasicsJetty class as a plain old Java application, and it will start
up Jetty and wait for you to terminate the process.

The console output will indicate the URLs you use in your browser to access the web apps.

================================================ Layout                 ================================================

+=====================================+==============================================+
| Location                            | Purpose                                      |
|=====================================|==============================================|
| src/main                            | Main code                                    |
|  me.bantling.j2ee.basics.bean       |  Immutable JavaBeans and Enums               |
|  me.bantling.j2ee.basics.dao        |  DAO classes                                 |
|  me.bantling.j2ee.basics.form       |  Create JavaBeans from request paramaters    |
|  me.bantling.j2ee.basics.listener   |  Log runtime details of webapps and requests |
|  me.bantling.j2ee.basics.run        |  Start Jetty and execute webapps             |
|  me.bantling.j2ee.basics.servlet    |  Webapp classes                              |
|  me.bantling.j2ee.basics.validation |  Validate JavaBeans before persisting        |
| src/test                            | Testing code                                 |
|  test.me.bantling.j2ee.basics.run   |  Test HelloWorld webapp                      |
+=====================================+==============================================+

================================================ Tests                  ================================================

The only test is test.me.bantling.j2ee.basics.run.TJ2EEBasicsJetty, which does the following:

1. Start Jetty
2. Connect to the URL for the "Hello, World" webapp
3. Dump the generated page
4. Stop Jetty
