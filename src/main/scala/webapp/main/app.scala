package webapp.main

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html
import org.scalajs.dom.fetch
import webapp.components.*
import org.scalajs.dom
import dom.ext.Ajax

import concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSON
import io.circe.*
import io.circe.parser.*
import io.circe.generic.auto.*

import scala.collection.mutable.Buffer
import scala.language.postfixOps

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

    val inputContainer = addForm(rootDiv, id = Some("inputContainer"))
  
    val input = addInputElement(inputContainer, id = Some("valueInput"))
    val submitButton = addSubmitButton(
      inputContainer, 
      () => submitForm(input, rootDiv),
      Some("submitButton")
    )
    setInputValue(input, "Finland")
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

          val pop = country.population.toString.reverse
          var popWithSpace = ""
          for 
            i <- 0 until pop.length 
          do 
            if (i % 3 == 0) popWithSpace = " " + popWithSpace
            popWithSpace = pop(i) + popWithSpace
          updateText("population", s"Population: ${popWithSpace}")
          
          addTextElement(languageContainer, "Language(s):\n", "p")
          for 
            language <- country.languages
          do 
            addTextElement(languageContainer, s"– ${language.name}\n", "p", _class = Some("language"))
            updatePicture("flag", country.flags.png)
        }
      }
    ).recover(xhr => 
      updateText("feedback", s"Country \"${countryName}\" not found")
    )      
  end submitForm
end TutorialApp