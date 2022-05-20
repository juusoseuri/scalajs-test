package webapp.components

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html

/** Gets input value from a input element
 * 
 *  @param node the input node which field's value is desired
 *  @return the string responding for the value of the input field
 */

def getInputValue(node: dom.Element): String = node.asInstanceOf[html.Input].value

/** Sets the value for the input field
 *  
 *  @param node the input node which field's value is going to be set
 *  @param String the desired value for the input field
 */

def setInputValue(node: dom.Element, value: String): Unit = node.asInstanceOf[html.Input].value = value

/** Helper function for adding tags to a element
 * 
 *  @param node the node where the tags are going to be added
 *  @param id optional id tag for the element
 *  @param _class optional class tag for the element
 */

def addTags(node: dom.Element,
            id: Option[String],
            _class: Option[String]): Unit =
  id match
    case Some(i) => node.id = i
    case None => 
  _class match
    case Some(c) => node.setAttribute("class", c)
    case None =>
end addTags