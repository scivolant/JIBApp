##GestionSuivi

###A program to follow and manage investments using a SQL database.

Main class: GestionSuivi.java, the program then runs in a window (not output, just modification of the database).

It uses a PostgreSQL database to store data (use baseDeDonnees.sql and InitialisationBdD.sql to create the database). 

Packages:
* "accueil" - default/initial panel
* "aexecuter" - panel with the alerts on quotations
* "compta" - Java side of objects in the database (Pojo)
* "data" contains the commands for the database (and in particular the subpackage DAO)
* "edition" - edition panel of the program (where new investments can be defined, for instance)
* "observer" for an observer pattern
* "operation" - operation panel, where buying and selling investments are recorded
* "util" - classes used in several different panels.
