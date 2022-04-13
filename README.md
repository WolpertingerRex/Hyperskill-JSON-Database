# Hyperskill-JSON-Database
A client-server application that allows the clients to store their data on the server in JSON format.
Google Gson is used to process JSON. 
Supports GET, SET and DELETE commands.

Commands should be passed as a command line arguments as follows:
Example: java Main -t set -k 1 -v "Hello world!"
where -t argument specifies type of command (set, get, delete, exit)
-k is for key and -v is for value (for the SET command).

Also command can be read from file. To do this, -in argument should be passed to command line  
Example: java Main -in myFile.json 

Keys and values could be strings or JSON objects. For example:
 "person": {
        "name": "Adam",
        "surname": "Smith"
    }
If user wants to get, change or delete only a specific field of complex nested JSON object, they should type the full path to this field in a form of a JSON array: for exampe ["person", "surname"]. If the user wants to get the full person object, then they should type ["person"]. If user wants to add a new field, let's say add age of 25, they should pass as a key JSON array ["person", "age"] and a value of 25.
