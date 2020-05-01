# elo-ranking
Implement a ranking program using the Elo ranking algorithm.

## Implementation details
This application is a pure Java application, not based on any framework, but with a curated set of dependencies.
The application is developed in such a way that it will be very easy to port to any IoC container, just by removing the DiConfig class.

The requirements are:
* JDK 11 or newer
* Maven 3.6

From the main directory you can run
```
mvn clean package
java -jar .\target\elo-ranking.jar OPTIONS
```

## Usage
This is a command line application, all actions are performed by passing the proper parameters when invoking it.
Run the application without parameters or se the `-h` flag to display an help message.
Some usage examples:
* `-p 5`
    ```
    Id: 5
    Name: Tai
    Rating: 1050.10
    ```
* `-r`
    ```
    Position 001, name: Jacquelynn, rating: 1063.93
    Position 002, name: Odette, rating: 1061.52
    Position 003, name: Brice, rating: 1055.01
    ...
    ```
* `--matches 7`
    ```
    Id: 7, name: Michale
    Opponent: Michale, won: false, rating after match: 911.14
    Opponent: Michale, won: false, rating after match: 926.23
    Opponent: Michale, won: false, rating after match: 944.44
    Opponent: Michale, won: false, rating after match: 961.15
    Opponent: Michale, won: false, rating after match: 980.00
    ```
  
