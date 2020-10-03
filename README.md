# Scala payment-gateway skeleton
This skeleton is based on [Akka HTTP Quickstart for Scala](https://developer.lightbend.com/guides/akka-http-quickstart-scala/)
## To run the server:
### Start sbt:

- On OSX or Linux systems, enter `./sbt`
- On Windows systems, enter `sbt.bat`.

When you run sbt, it downloads project dependencies. 
The `>` prompt indicates that sbt is running in interactive mode.

At the sbt prompt, enter `server/reStart`.
The output should look something like this:
```
...
[2020-07-13 20:52:30,231] [INFO] [akka.actor.typed.ActorSystem] [HelloAkkaHttpServer-akka.actor.default-dispatcher-6] [] - Server online at http://127.0.0.1:8000/
```
The Akka HTTP server is now running, and you can test it by sending simple HTTP requests.

You can restart it by entering reStart again, and stop it with `server/reStop`. 
