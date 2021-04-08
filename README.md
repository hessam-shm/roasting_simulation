# roasting simulation
## How to run
Once the app is built the data generator code will produce the required code. Then after a 3 second pause, the simulations starts.
### docker
The simplest way to run this app is to use docker. Two docker images will be created. One fore postgres and the other the main application. In the root of application execute this command:
```docker-compose up```
### local
To run this app locally a running instance of Postgresql on local host is required. The app uses the schema *cropster*. All tests use in memory H2 database.
## Assumptions
### Non technical assumptions
#### Rounding
Probably the most challenging part of this project was to coming up with a consistant yet sensible rounding strategy for green coffee amounts.
* The first strategy was to simply let go of decimal part and save the start and end weight of a roasing process without any decimal precision too. This worked surprisingly good and kept the data integrity. So at the end of the simulation the sum of roasting processes amounts and the remaining amount of green coffees added up exatly equal to the initial amount. But there were two disadvantages. First In some cases where the machine capacity is small the constraint that start weight must be greater than the end weight got violated. So this constraint should have been relaxed to ```start_weight >= end_weight```. The second violation was using integer instead of decimal for start and end weight.
* The second and current strategy that replaced the first one in [this commit](https://github.com/hessam-shm/roasting_simulation/commit/2751797a4dd89125ee7a8639dbd18c04e458adb7) involved using doubles with two decimal point precision. This time to be safe against violating the constraint ceiling is used for rounding. It keeps the constraint in expense of losing almost 1% of green coffees amount in rounding.
#### Ending simulation
Finding a balanced condition to end the simulation was another challenge. The idea of used up is to consume green coffees as much as possible but it could lead to somehow lengthy simulations. The current condition to declare a simulation finished is _when in all facilities there is not any green bean with the amount greater than the smallest capacity of the machines of the same facility._ (In many tries the worst case took around 30 minutes to finish)
### Technical assumptions
* Due to uncertainity about green coffee names, the names are not normalized to a separate table.
* The idea of having two separate services for random and non random, didn't work well in code. It led to more classes and some repeated code (while not violating *three strikes and you refactor*)
* Placing roast method in ```MachineService``` instead of ```RoastingProcessService``` as the behavior of machine made a little bit more sense and decreased the dependencies.
* The **final most important assumption** in every software project is it worked on my machine so it works on any other machine :)
### Work in progress
* Since not part of the requirements, controllers are there but just as a stub code, neither fully implemented nor tested.
* Externalizing constants to a properties file so changing conditions becomes easier
* While the transactional nature of the code avoids any conflicts but a multithreaded simulation would be welcome for testing
