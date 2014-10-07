package com.gvolpe.akka.actors

import akka.actor.{ActorRef, Actor, Props}

case object StartProcessFileMsg

object WordCounterActor {

  def props(filename: String) = {
    Props(new WordCounterActor(filename))
  }

}

class WordCounterActor(filename: String) extends Actor {
  
  private var totalLines = 0
  private var linesProcessed = 0
  private var result = 0
  private var fileSender: Option[ActorRef] = None
  
  def receive = startProcess
  
  def processAlreadyRunning: Receive = {
    case StringProcessedMsg(wordsAmount) => {
      result += wordsAmount
      linesProcessed += 1
      if (linesProcessed == totalLines) {
        fileSender.map(_ ! result)
      }
    }
    case _ => println("Process is already running!")
  }
  
  def startProcess: Receive = {
    case StartProcessFileMsg => {
      context.become(processAlreadyRunning)
      fileSender = Some(sender)
      
      import scala.io.Source._
      fromFile(filename).getLines.foreach { line =>
        context.actorOf(Props[StringCounterActor]) ! ProcessStringMsg(line)
        totalLines += 1
      }
    }
    case _ => println("WordCounterActor: Message not recognized.")
  }
  
}