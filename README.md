## GestionSuivi

### A program to follow and manage investments using a SQL database.

Main class: GestionSuivi.java, the program then runs in a window (no output, just modification of the database).

It uses a PostgreSQL database to store data.

### Quickstart

* The JIBApp.jar file enables to run the interface.
* However, to get a fully functional system, you need to create a PostgreSQL database (default name: "jibapp", default user: "postgres"). To get a minimal functioning database, use baseDeDonnees.sql and InitialisationBdD.sql to create and populate the database.

### About the structure

Packages:
* "accueil" - default/initial panel
* "aexecuter" - panel with the alerts on quotations
* "compta" - Java side of objects in the database (Pojo)
* "data" contains the commands for the database (and in particular the subpackage DAO)
* "edition" - edition panel of the program (where new investments can be defined, for instance)
* "observer" for an observer pattern
* "operation" - operation panel, where buying and selling investments are recorded
* "util" - classes used in several different panels.

The git repository contains only the source codes, so the best option to edit it in Eclipse is to clone the repository in .../JIBapp/src/gestion (to avoid any problem with packages...).
