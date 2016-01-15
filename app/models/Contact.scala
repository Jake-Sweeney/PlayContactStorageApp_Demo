package models

/**
 * Created by Jake on 10/01/2016.
 */

import play.api._
import play.api.mvc._

case class Contact(id:Long, contact:String)

object Contact {

  var id = 0L

  def nextId(): Long = { id += 1; id}

  def create(contact:String) = { ContactDB.saveContact(Contact(nextId(), contact))}

  def delete(id: Long) = {ContactDB.deleteContact(id)}

  def list(): List[Contact] = {ContactDB.retrieveContactNames() }
}