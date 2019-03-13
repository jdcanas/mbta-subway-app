# mbta-subway-app

##About this App
This application prints the following pieces of information to the console:
1. The subway lines (or routes) with type 0,1, as defined and returned by the MBTA API, located here `https://api-v3.mbta.com/routes`. 
2. The stop IDs corresponding to the subway stops on the routes from 1), retrieved from this API: `https://api-v3.mbta.com/stops?route=[route]`
3. The route with the most stops, and the number of stops. 
4. The route with the least stops, and the number of stops.
5. All stops that connect one route to another (defined as a stop that is on more than one route) 
6. The stops between stop specific stops. Note that the stop must be specified via ID as returned by the api mentioned in 2)

This app contains an extensive test suite, powered by JUnit 5. Mockito is used in some tests for easy mocking.

##Running the App
To run the app, follow these steps:
1. Clone the app via `git clone https://github.com/jdcanas/mbta-subway-app.git`
2. Install `gradle`: https://gradle.org/install/
3. Execute `gradle run --args"[srcStopID] [destStopID]`. The arguments are only required for functionality 6), and must be an ID contained in the return of 2) to successfully route. For example, `gradle run --args="place-cedgr place-gover"`

##Testing the App
1. Execute `gradle test`

##Building the App
1. Execute `gradle build`. This will produce a jar file in `build/libs/` named `mbta.jar` (JAR name subject to change based on `build.gradle` configuration file)
2. To run the jar, execute `java -jar mbta.jar [srcStopID] [destStopID]`, for example `java -jar build/libs/mbta.jar place-cedgr place-gover`. See the Running the App section for details on how to get a stopID. 

##Debugging the tests in eclipse
1. Execute `gradle test --debug-jvm`
2. Go to Run -> Debug Configurations -> Remote Java Application -> Create a configuration for `localhost` and port `5005`