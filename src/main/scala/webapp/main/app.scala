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
    val rootDiv = addContainer(document.body, id = "rootDiv")
    val header = addTextElement(rootDiv, "Header", "h1", "quizHeader")

    val inputContainer = addContainer(rootDiv, id = "inputContainer")
    
    val nameInput = addInputElement(inputContainer, id = "nameInput")
    val valueInput = addInputElement(inputContainer, id = "valueInput")
    val submitButton = addSubmitButton(
      inputContainer, 
      () => submitForm(nameInput, valueInput, rootDiv),
      id = "submitButton"
    )
  end setupUI

  /** Handles the form submission
   * 
   *  Takes the value of the input, resets its value and 
   *  then makes a response to the page
   * 
   *  This could be a method that the students can do themselves
   *  
   *  @param inputName node which value is going to be read
   *  @param outputNode node where the response is going to be appended
   */

  def submitForm(name: dom.Element, msg: dom.Element, output: dom.Element): Unit = 
    val msgValue = getInputValue(msg)
    val nameValue = getInputValue(name)

    if msgValue == "" || nameValue == "" then return
    setInputValue(msg, "")

    val msgContainer = addContainer(output, _class = "msgContainer")

    addTextElement(msgContainer, s"user: $nameValue", "p", _class = "name")
    addTextElement(msgContainer, msgValue, "p", _class = "value")
  end submitForm

  /** Adds wanted text element as a child node
   *  
   *  @param targetNode node where the text element is going to be appended
   *  @param text wanted text for the element
   *  @param element the type of text element in HTML
   *  @param id optional id tag for the element
   *  @param _class optional class tag for the element
   *  @return the created text element
   */

  def addTextElement(targetNode: dom.Node, 
                     text: String, 
                     element: String,
                     id: String = null,
                     _class: String = null): dom.Element =
    val parNode = document.createElement(element)
    parNode.textContent = text
    if id != null then parNode.id = id
    if _class != null then parNode.classList.add(_class)
    targetNode.appendChild(parNode)
    return parNode
  end addTextElement

  /** Adds input field as a child node
   *  
   *  @param parentNode the parent node where the input field is appended
   *  @param id optional id tag for the input element
   *  @param _class optional class tag for the input element
   *  @return the created input element
   */

  def addInputElement(parentNode: dom.Node, 
                      id: String = null, 
                      _class: String = null): dom.Element =
    val form = document.createElement("INPUT")
    if id != null then form.setAttribute("id", id)
    if _class != null then form.classList.add(_class)
    parentNode.appendChild(form)
    return form
  end addInputElement

  /** Adds submit button as a child node
   * 
   *  @param parentNode the parent node where the button element is appended
   *  @param submitAction a function that is called when the button is pressed
   *  @param id optional id tag for the button element
   *  @param _class optional class tag for the button element
   *  @return the created button element
   */

  def addSubmitButton(parentNode: dom.Node,
                      submitAction: () => Unit,  
                      id: String = null,
                      _class: String = null): dom.Element =
    val button = document.createElement("button")
    button.textContent = "Submit"
    addTags(button, id, _class)
    parentNode.appendChild(button)
    button.addEventListener("click", {(e: dom.MouseEvent) => 
      submitAction()
    })
    return button
  end addSubmitButton

  def addContainer(targetNode: dom.Node, 
                   id: String = null, 
                   _class: String= null): dom.Element = 
    val div = document.createElement("div")  
    addTags(div, id, _class)
    targetNode.appendChild(div)
    return div
  end addContainer

  
  def getInputValue(node: dom.Element): String = node.asInstanceOf[html.Input].value
  def setInputValue(node: dom.Element, value: String): Unit = node.asInstanceOf[html.Input].value = value
  
  def addTags(node: dom.Element,
              id: String = null,
              _class: String = null): Unit =
    if id != null then node.id = id
    if _class != null then node.setAttribute("class", _class)
  end addTags
end TutorialApp