package controllers

import play.api._
import play.api.i18n.{MessagesApi, I18nSupport}

import play.api.mvc._
import play.api.data._
import play.api.data.Form._
import play.api.data.Forms._

import javax.inject.Inject

import models._
import views._

class Application @Inject()(val messagesApi : MessagesApi) extends Controller with I18nSupport {

  val contactForm = Form("contact" -> nonEmptyText)

  def index = listContacts

  def listContacts() = Action {
    Ok(views.html.index(Contact.list, contactForm))
  }

  def createContact() = Action { implicit request =>
    contactForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Contact.list, errors)),
      contact => {
        Contact.create(contact)
        Redirect(routes.Application.listContacts)
      }
    )
  }

  def deleteContact(id:Long) = Action {
    Contact.delete(id)
    Redirect(routes.Application.listContacts)
  }
}
