package webapp.main

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html
import org.scalajs.dom.fetch
import webapp.components._
import org.scalajs.dom
import dom.ext.Ajax
import concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSON
import io.circe._, io.circe.parser._, io.circe.generic.auto._
import scala.collection.mutable.Buffer

case class Country(
  name: String, 
  population: Int,
  independent: Boolean,
  languages: Buffer[Language],
  flags: Flag
)

case class Language(
  iso639_1: String,
  iso639_2: String,
  name: String,
  nativeName: String
)

case class Flag(
  png: String,
  svg: String
)

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
    val rootDiv = addContainer(document.body, id = Some("rootDiv"))
    val header = addTextElement(rootDiv, "Find countries", "h1", id = Some("quizHeader"))

    val inputContainer = addContainer(rootDiv, id = Some("inputContainer"))
  
    val valueInput = addInputElement(inputContainer, id = Some("valueInput"))
    val submitButton = addSubmitButton(
      inputContainer, 
      () => submitForm(valueInput, rootDiv),
      Some("submitButton")
    )
    val countryContainer = addContainer(rootDiv)
    addPicture(countryContainer, "", id = Some("flag"))
    addTextElement(countryContainer, "", "p", id = Some("feedback"))
    addTextElement(countryContainer, "", "p", id = Some("name"))
    addTextElement(countryContainer, "", "p", id = Some("population"))
    addContainer(countryContainer, id = Some("languages"))

  end setupUI

  def submitForm(input: dom.Element, output: dom.Element): Unit = 
    val countryName = getInputValue(input)
    val languageContainer = document.getElementById("languages")
    
    updateText("name", "")
    updateText("population", "")
    updateText("languages", "")
    updatePicture("flag", "")
    updateText("feedback", "loading...")
    
    Ajax.get(
      s"https://restcountries.com/v2/name/${countryName}?fields=name,languages,population,flags"
    ).map(xhr => 
      val response = decode[List[Country]](xhr.responseText)
      
      updateText("feedback", "")
      response match {
        case Left(error) => {
          println(error)
          updateText("feedback", "Problem with JSON")
        }
        case Right(countryList) => {
          val country = countryList(0)
          updateText("name", s"Name: ${country.name}")
          updateText("population", s"Population: ${country.population}")
          addTextElement(languageContainer, "Language(s):\n", "p")
          for 
            language <- country.languages
          do 
            addTextElement(languageContainer, s"– $language\n", "p", _class = Some("language"))
            updatePicture("flag", country.flags.png)
        }
      }
    ).recover(xhr => 
      updateText("feedback", s"Country \"${countryName}\" not found")
    )      
  end submitForm
end TutorialApp