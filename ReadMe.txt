PlaceFinder application:

Currently only 1 search provider - GOOGLE is supported, with FOUR_SQUARE to be implemented

How to run this app.

1) Edit the file: src/main/resources/application.properties
    - Provide a valid google api key

2) Compile the changes:
    $ mvn clean install -DskipTests

3) Run app:
    $ mvn spring-boot:run

Before running tests, edit the file: src/test/resources/application.properties
