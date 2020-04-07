# risksense-assignment

Spring Boot application with single rest endpoint which can be used to convert json to xml by 
specifying the source and destination file path. There are different ways we can do conversion between json and xml.
The way this application does can be understood by the example given at the end.

#### Build steps
````
mvn clean - to clean the target directory
mvn install - to generate a jar file out of the spring boot project
````

#### Running the Service
````
java -jar target/JsontoXML-1.0.0.jar - to run the spring boot application that listens on the port 8080
  
if port 8080 is already being used in your system, then use
java -jar -Dserver.port={customport} target/JsontoXML-1.0.0.jar to start the spring boot application in custom port
````
#### Sending request through any API client or Browser:
````
http://localhost:8080/jsonToXml?jsonFilePath=large.json&xmlFilePath=output.xml

large.json file should be present at the project level.

The application will generate output.xml at the project level if file doesn't exist, 
if it does exist, it will override the content with the xml it generated

````

#### Example
````
JSON :
{
    "organization" : {
        "name" : "RiskSense",
        "type" : "Inc",
        "building_number" : 4,
        "floating" : -17.4,
        "null_test": null
    },
    "security_related" : true,
    "array_example0" : ["red", "green", "blue", "black"],
    "array_example1" : [1, "red", [{ "nested" : true}], { "obj" : false}]
}

XML :
<object>
	<object name="organization">
		<string name="name">RiskSense</string>
		<string name="type">Inc</string>
		<number name="building_number">4</number>
		<number name="floating">-17.4</number>
		<null name="null_test" />
	</object>
	<boolean name="security_related">true</boolean>
	<array name="array_example0">
		<string>red</string>
		<string>green</string>
		<string>blue</string>
		<string>black</string>
	</array>
	<array name="array_example1">
		<number>1</number>
		<string>red</string>
		<array>
			<object>
				<boolean name="nested">true</boolean>
			</object>
		</array>
		<object>
			<boolean name="obj">false</boolean>
		</object>
	</array>
</object>
````
