simple-file-reader-akka-actors
==============================

Simple example for the use of Akka actors.

The code is an example explained very well in [this blog](http://www.toptal.com/scala/concurrency-and-fault-tolerance-made-easy-an-intro-to-akka) but with some changes:
* The boolean variable 'running' in the WordCounterActor was replaced using context.become method to switch the actor behavior.
* I use the props method in the companion object to encapsulate the creation of WordCounterActor.

### Execute

* Run the "activator" command from the root folder of the project
* Type "run" and enter

This command will execute the com.gvolpe.akka.ReaderApp main class.
