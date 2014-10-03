package com.gvolpe.akka.actors

import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.Props

case class StartProcessFileMsg()

class WordCounterActor(filename: String) extends Actor {

  private var running = false
  private var totalLines = 0
  private var linesProcessed = 0
  private var result = 0
  private var fileSender: Option[ActorRef] = None
  
  def receive = {
    
    case StartProcessFileMsg() => {
      if (!running) {
        running = true
        fileSender = Some(sender)
        
        import scala.io.Source._
        fromFile(filename).getLines.foreach { line =>
          context.actorOf(Props[StringCounterActor]) ! ProcessStringMsg(line)
          totalLines += 1
        }
      }
    }
    case StringProcessedMsg(wordsAmount) => {
      result += wordsAmount
      linesProcessed += 1
      if (linesProcessed == totalLines) {
        fileSender.map(_ ! result)
      }
    }
    case _ => println("WordCounterActor: Message not recognized.")
    
  }
  
}