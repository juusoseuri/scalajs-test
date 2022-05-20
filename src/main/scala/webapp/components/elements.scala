package webapp.components

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html

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
                  id: String = null, 
                  _class: String= null): dom.Element = 
  val div = document.createElement("div")  
  addTags(div, id, _class)
  parentNode.appendChild(div)
  return div
end addContainer