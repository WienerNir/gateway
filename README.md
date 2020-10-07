# Scala gateway 
This project is based on [Akka HTTP Quickstart for Scala](https://developer.lightbend.com/guides/akka-http-quickstart-scala/)
## Running locally

- On OSX or Linux systems, enter `./sbt`
- On Windows systems, enter `sbt.bat`.

At the sbt prompt, enter `server/reStart`.
The output should look something like this:
```
...
[2020-07-13 20:52:30,231] [INFO] [akka.actor.typed.ActorSystem] [HelloAkkaHttpServer-akka.actor.default-dispatcher-6] [] - Server online at http://127.0.0.1:8000/
```
The server is now running, and you can test it by sending simple HTTP requests.

You can restart it by entering reStart again, and stop it with `server/reStop`. 


#Unit Tests
At the sbt prompt, enter `server/test`.

The output should look something like this:
[info] RunnerSpec:
[info] Runner
[info] - should return 5
[info] Run completed in 325 milliseconds.
[info] Total number of tests run: 1
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 1, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.



