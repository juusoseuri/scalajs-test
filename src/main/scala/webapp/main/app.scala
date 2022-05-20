package webapp.main

import org.scalajs.dom
import org.scalajs.dom.document
import webapp.components._

object TutorialApp:

  /** Main function for the application
   * 
   *  This function is called when the page is loaded
   *  @param args 
   */

  def main(args: Array[String]): Unit =
    document.addEventListener("DOMContentLoaded", { (e: dom.Event) =>
      setupUI()
    })
  end main

  /** Sets up the UI for the page
   * 
   *  When called creates and renders necessary parts for the page
   */

  def setupUI(): Unit =
    val rootDiv = addContainer(document.body, Some("rootDiv"), None)
    val header = addTextElement(rootDiv, "Header", "h1", Some("quizHeader"), None)

    val inputContainer = addContainer(rootDiv, Some("inputContainer"), None)
    
    val nameInput = addInputElement(inputContainer, Some("nameInput"), None)
    val valueInput = addInputElement(inputContainer, Some("valueInput"), None)
    val submitButton = addSubmitButton(
      inputContainer, 
      () => submitForm(nameInput, valueInput, rootDiv),
      Some("submitButton"),
      None
    )
    val pic = addPicture(rootDiv, "images/koira.jpeg", Some("kuva"), None)
  end setupUI

  /** Handles the form submission
   * 
   *  Takes the value of the input, resets its value and 
   *  then makes a response to the page
   * 
   *  This would be a method that the students can do themselves
   *  and the current version is just an example and a placeholder
   *  
   *  @param name the name input field element
   *  @param msg the message input field element
   *  @param output node where the response is going to be appended
   */

  def submitForm(name: dom.Element, msg: dom.Element, output: dom.Element): Unit = 
    val msgValue = getInputValue(msg)
    val nameValue = getInputValue(name)

    if msgValue == "" || nameValue == "" then return
    setInputValue(msg, "")

    val msgContainer = addContainer(output, None, Some("msgContainer"))

    addTextElement(msgContainer, nameValue, "p", None, Some("name"))
    addTextElement(msgContainer, msgValue, "p", None, Some("value"))
  end submitForm
end TutorialApp