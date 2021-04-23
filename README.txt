This is a simple POC for instagram-like application that allows different users to upload images and updates both their personal and a public feed in real-time using websockets.
	
Java with Spring Boot + very simple html and javascript.

To run the app execute ./mvnw spring-boot:run in root project folder.

Once the server loads it should display something like 
Started LimegramApplication in 1.392 seconds (JVM running for 1.668)

You can then access the application in a browser at localhost:8080

Note:
Every page reload is generating a unique user id (displayed at the top of the page) such as yd71F. 
This is used instead of implementing create profile/login functionality. 
So if you refresh the page or open it in 2 different tabs this will result in 2 different user ids - you can use both to upload images (or a completely random one).

To upload an image either:

1) curl -F "file=@./image 1.jpg" http://localhost:8080/upload/yd71F

2) Use something like Postman or any tool that can be used with REST APIs.
POST localhost:8080/upload/yd71F
in body select form-data and add a property with key 'file' and value - the image file you want to upload 

If you upload using the generated user id the image should appear in both personal and public feeds.
If you upload using a random id, the image should appear only in the public feed.
If you refresh the page a new id will be generated and the public feed will load with all previously uploaded images (before server restart).