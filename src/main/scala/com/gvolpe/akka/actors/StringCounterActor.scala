package com.gvolpe.akka.actors

import akka.actor.Actor

case class ProcessStringMsg(string: String)
case class StringProcessedMsg(wordsAmount: Integer)

class StringCounterActor extends Actor {
  
  def receive = {
    case ProcessStringMsg(string) => {
      val wordsInLine = string.split(" ").length
      sender ! StringProcessedMsg(wordsInLine)
    }
    case _ => println("StringCounterActor: Message not recognized.")
  }

}