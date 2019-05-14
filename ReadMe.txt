PlaceFinder application:

Currently only 1 search provider - GOOGLE is supported, with FOUR_SQUARE to be implemented

How to run this app.

1) Edit the file: src/main/resources/application.properties
    - Provide a valid google api key

2) Compile the changes:
    $ mvn clean install -DskipTests

    Note: Before running tests, edit the file: src/test/resources/application.properties

3) Run app:
    $ mvn spring-boot:run

4) Rest Endpoints:
    a) http://localhost:8080/places/aundh/chinese%20restaurants
    b) http://localhost:8080/places/aundh/chinese%20restaurants?distance=500&searchProvider=GOOGLE
