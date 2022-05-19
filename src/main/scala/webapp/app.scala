package webapp

import org.scalajs.dom
import org.scalajs.dom.document
import scala.scalajs.js.annotation.JSExportTopLevel

object TutorialApp:
  def main(args: Array[String]): Unit =
    document.addEventListener("DOMContentLoaded", { (e: dom.Event) =>
      setupUI()
    })
    println("Hello world")
  end main
  
  def appendH1(targetNode: dom.Node, text: String): Unit =
    val parNode = document.createElement("h1")
    parNode.textContent = text
    targetNode.appendChild(parNode)
  end appendH1

  @JSExportTopLevel("addClickedMessage")
  def addClickedMessage(): Unit =
    appendH1(document.body, "You clicked!")
  end addClickedMessage

  def setupUI(): Unit =
    appendH1(document.body, "Hello world")
    val button = document.createElement("button")
    button.textContent = "Click me!"
    button.addEventListener("click", { (e: dom.MouseEvent) => 
      addClickedMessage()
    })
    document.body.appendChild(button)
  end setupUI

end TutorialApp