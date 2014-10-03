package com.gvolpe.akka

import actors._
import akka.actor.{ ActorSystem, Props }
import akka.dispatch.ExecutionContexts._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{ Success, Failure }

object ReaderApp extends App {

  implicit val ec = global

  def sleep(duration: Long) { Thread.sleep(duration) }

  val system = ActorSystem("FileReaderSystem")
  val actor = system.actorOf(Props(new WordCounterActor("files/rockbands.txt")))
  implicit val timeout = Timeout(25 seconds)

  val futureCount = actor ? StartProcessFileMsg()

  futureCount map { result =>
    println("Total words in file: " + result)
    system.shutdown
  }

}