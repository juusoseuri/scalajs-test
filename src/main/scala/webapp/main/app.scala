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
  name: Option[String] = None, 
  capital: Option[String] = None, 
  population: Option[Int] = None,
  independent: Boolean,
  languages: Option[Buffer[Language]] = None,
  flags: Option[Flag] = None
)

case class Language(
  iso639_1: Option[String] = None,
  iso639_2: Option[String] = None,
  name: Option[String] = None,
  nativeName: Option[String] = None
)

case class Flag(
  png: Option[String] = None,
  svg: Option[String] = None
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
    val header = addTextElement(rootDiv, "Header", "h1", id = Some("quizHeader"))

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
      s"https://restcountries.com/v2/name/${countryName}?fields=name,capital,languages,population,flags"
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
          country.name match {
            case Some(name) => updateText("name", s"Name: $name")
            case None => updateText("name", s"Name: Not available")
          }
          country.population match {
            case Some(pop) => updateText("population", s"Population: $pop")
            case None => updateText("population", s"Population: Not available")
          }
          country.languages match {
            case None => updateText("languages", s"Language not available")
            case Some(list) => 
              addTextElement(languageContainer, "Language(s):\n", "p")
              for 
                language <- list
              do 
                language.name match {
                  case Some(language) => 
                    addTextElement(languageContainer, s"– $language\n", "p", _class = Some("language"))
                  case None =>
                }
          }
          country.flags match {
            case Some(flag) => 
              flag.png match {
                case Some(src) => updatePicture("flag", src)
                case None =>
              }
            case None =>
          }
        }
      }
    ).recover(xhr => 
      updateText("feedback", s"Country \"${countryName}\" not found")
    )      
  end submitForm
end TutorialApp