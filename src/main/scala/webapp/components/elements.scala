package webapp.components

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html

/** Adds wanted text element as a child node
 * 
 *  Text element can be { "h1", ... , "h6", "p"}
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
                    id: Option[String],
                    _class: Option[String]): dom.Element =
  val textElement = document.createElement(element)
  textElement.textContent = text
  addTags(textElement, id, _class)
  targetNode.appendChild(textElement)
  return textElement
end addTextElement

/** Adds input field as a child node
 *  
 *  @param parentNode the parent node where the input field is appended
 *  @param id optional id tag for the input element
 *  @param _class optional class tag for the input element
 *  @return the created input element
 */

def addInputElement(parentNode: dom.Node, 
                    id: Option[String], 
                    _class: Option[String]): dom.Element =
  val form = document.createElement("INPUT")
  addTags(form, id, _class)
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
                    id: Option[String],
                    _class: Option[String]): dom.Element =
  val button = document.createElement("button")
  button.textContent = "Submit"
  addTags(button, id, _class)
  parentNode.appendChild(button)
  button.addEventListener("click", {(e: dom.MouseEvent) => 
    submitAction()
  })
  return button
end addSubmitButton

/** Adds a container
 * 
 *  The container is added as a child node to the assigned parent
 *  Other elements can be easily stored inside of this container
 * 
 *  @param parentNode the parent node where the container element is appended
 *  @param id optional id tag for the container
 *  @param _class optional class tag for the container
 *  @return the created container element
 */

def addContainer(parentNode: dom.Node, 
                id: Option[String], 
                _class: Option[String]): dom.Element = 
  val div = document.createElement("div")  
  addTags(div, id, _class)
  parentNode.appendChild(div)
  return div
end addContainer

/** Adds a picture
 *  
 *  @param parentNode the parent node where the picture is appended
 *  @param src the relative path to the image file
 *  @param id optional id tag for the picture
 *  @param _class optional class tag for the container
 *  @return the created image element
 */

def addPicture(parentNode: dom.Node,
               src: String,
               id: Option[String],
               _class: Option[String]): dom.Element =
  val pic = document.createElement("IMG")
  pic.setAttribute("src", src)
  addTags(pic, id, _class)
  parentNode.appendChild(pic)
  return pic
end addPicture

/** Updates the picture
 * 
 *  @param id id of the picture
 *  @param src src for the new picture
 *  @return the image element
 */
def updatePicture(id: String, src: String): dom.Element = 
  val pic = document.getElementById(id)
  pic.setAttribute("src", src)
  return pic
end updatePicture

/** Adds a linebreak
 * 
 *  @param parentNode the node where you would want a linebreak
 */

def addLinebreak(parentNode: dom.Node): Unit = 
  val br = document.createElement("br")
  parentNode.appendChild(br)
end addLinebreak

/** Updates text for the text element
 * 
 *  @param id id of the node to be updated
 *  @param text the new text for the element
 *  @return the updated element
 */

def updateText(id: String, text: String): dom.Element =
  val elem = document.getElementById(id)
  elem.innerText = text
  return elem
end updateText
