package webapp.main

import org.scalajs.dom
import org.scalajs.dom.document
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html

object TutorialApp:

  /** Main function for the application
   * 
   *  @param args 
   */
  def main(args: Array[String]): Unit =
    document.addEventListener("DOMContentLoaded", { (e: dom.Event) =>
      setupUI()
    })
    println("Hello world")
  end main

  /** Sets up the UI for the page when called
   * 
   *  When called calls and renders necessary parts
   *  for the page
   */

  def setupUI(): Unit =
    val rootDiv = document.createElement("div")
    rootDiv.id = "rootDiv"
    document.body.appendChild(rootDiv)

    appendTextElement(rootDiv, "Quiz", "h1", "quizHeader")

    createForm()

  end setupUI

  /** Creates the form
   *  
   *  Sets the event listener to the submit button so that
   *  the input values can be used by submitForm
   */
  def createForm(): Unit =
    val rootDiv = document.getElementById("rootDiv")

    val form = document.createElement("div")
    form.setAttribute("id", "form")

    val nameInput = document.createElement("INPUT")
    nameInput.setAttribute("id", "nameInput")
    nameInput.setAttribute("type", "text")

    
    val valueInput = document.createElement("INPUT")
    valueInput.setAttribute("id", "valueInput")
    valueInput.setAttribute("type", "text")
    
    val formButton = document.createElement("button")
    formButton.textContent = "Submit"
    
    rootDiv.appendChild(form)
    form.appendChild(nameInput)
    form.appendChild(valueInput)
    form.appendChild(formButton)

    formButton.addEventListener("click", {(e: dom.MouseEvent) => 
      submitForm(valueInput, form)
    })
  end createForm

  /** Handles the form submission
   * 
   *  Takes the value of the input, resets its value and 
   *  then makes a response to the page
   *  
   *  @param inputNode node which value is going to be read
   *  @param outputNode node where the response is going to be appended
   */

  def submitForm(inputNode: dom.Node, outputNode: dom.Node): Unit = 
    val value = inputNode.asInstanceOf[html.Input].value
    println(value)
    inputNode.asInstanceOf[html.Input].value = ""
    appendTextElement(outputNode, value, "p")
  end submitForm

  /** Appends wanted text element
   *  
   *  @param targetNode node where the text element is going to be appended
   *  @param text wanted text for the element
   *  @param element the type of text element in HTML
   *  @param id optional id tag for the element
   *  @param _class option class tag for the element
   */

  def appendTextElement(targetNode: dom.Node, text: String, element: String,  id: String = null, _class: String = null): Unit =
    val parNode = document.createElement(element)
    parNode.textContent = text
    if id != null then parNode.id = id
    targetNode.appendChild(parNode)
  end appendTextElement

end TutorialApp