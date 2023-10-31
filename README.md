# TRE-Provenance-Explorer

## How to run 

Prerequisites

Java 11,
Maven,
Git

````
git clone  https://github.com/RAINS-UOA/accountability-fabric.git
````

cd into the directory and run 

````
mvn package
```` 

cd into the /target directory and run


````
java -jar DaSH-Provenance-Explorer-0.0.1-SNAPSHOT-shaded.jar
```` 

From the file dialogue select the file containing the provenance trace. 

# Demo files 

Download example [provenance trace](https://github.com/TRE-Provenance/Examples/tree/main/provenance%20trace)

Note: the app will expect comments.jsonld file to be present in the same directory as teh provenance trace. Thsi file is used to store comments. 

## Binary distributions 

See releases

## Demo

https://github.com/TRE-Provenance/DaSH-Provenance-Explorer/assets/4025828/3fff0b43-a714-432e-890c-4d48a8d79cb3


