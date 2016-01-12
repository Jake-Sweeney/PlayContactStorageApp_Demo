package models

/**
  * Created by Jake on 10/01/2016.
  */

import play.api.Play.current
import play.api.db.DB
import play.api.mvc._

import anorm._
import anorm.SQL

import scala.collection.mutable.HashMap


class ContactDB(contact: Contact) {

   def saveContact = {
     val contactID = contact.id.toInt
     val contactName = contact.contact

      DB.withConnection { implicit connection =>
        val result: Int =
          SQL("INSERT INTO contact VALUES ({id},{name},'0871604565')")
            .on('id -> contactID, 'name -> contactName)
            .executeUpdate()
        println("Contact saved to database.\n" + result + " row(s) updated.")
      }
  }
}
