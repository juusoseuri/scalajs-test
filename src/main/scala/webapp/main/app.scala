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

    addTextElement(rootDiv, "Header", "h1", "quizHeader")

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
    formButton.setAttribute("id", "submitButton")
    
    rootDiv.appendChild(form)
    form.appendChild(nameInput)
    form.appendChild(valueInput)
    form.appendChild(formButton)

    formButton.addEventListener("click", {(e: dom.MouseEvent) => 
      submitForm(nameInput, valueInput, rootDiv)
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

  def submitForm(inputNameNode: dom.Node, inputValueNode: dom.Node, outputNode: dom.Node): Unit = 
    val value = inputValueNode.asInstanceOf[html.Input].value
    val name = inputNameNode.asInstanceOf[html.Input].value
    if value == "" || name == "" then return
    
    inputValueNode.asInstanceOf[html.Input].value = ""
    val msgContainer = document.createElement("div")
    msgContainer.setAttribute("class", "msgContainer")
    outputNode.appendChild(msgContainer)

    addTextElement(msgContainer, s"user: $name", "p", _class = "name")
    addTextElement(msgContainer, value, "p", _class = "value")
  end submitForm

  /** Appends wanted text element
   *  
   *  @param targetNode node where the text element is going to be appended
   *  @param text wanted text for the element
   *  @param element the type of text element in HTML
   *  @param id optional id tag for the element
   *  @param _class optional class tag for the element
   */

  def addTextElement(targetNode: dom.Node, 
                     text: String, 
                     element: String,
                     id: String = null,
                     _class: String = null): Unit =
    val parNode = document.createElement(element)
    parNode.textContent = text
    if id != null then parNode.id = id
    if _class != null then parNode.classList.add(_class)
    targetNode.appendChild(parNode)
  end addTextElement

  def addInputElement(targetNode: dom.Node, 
                      id: String = null, 
                      _class: String = null): Unit =
    val form = document.createElement("INPUT")
    if id != null then form.setAttribute("id", id)
    if _class != null then form.setAttribute("class", _class)
    targetNode.appendChild(form)
  end addInputElement

  def addSubmitButton(valueInput: dom.Node,
                      targetNode: dom.Node,
                      submitAction: () => Unit,  
                      id: String = null,
                      _class: String = null): Unit =
    val button = document.createElement("button")
    if id != null then button.setAttribute("id", id)
    if _class != null then button.setAttribute("class", _class)
    targetNode.appendChild(button)
    button.addEventListener("click", {(e: dom.MouseEvent) => 
      submitAction()
    })
  end addSubmitButton

end TutorialApp