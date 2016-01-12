package models

/**
 * Created by Jake on 10/01/2016.
 */

import play.api._
import play.api.mvc._
import scala.collection.mutable.HashMap


case class Contact(id:Long, contact:String)

object Contact {

  val contacts = new HashMap[Long, Contact]
  var id = 0L;

  def nextId(): Long = { id += 1; id}

  def create(contact:String) = {
//    val id = nextId(); contacts += id -> Contact(id, contact)
   val contactDB = new ContactDB(Contact(nextId(), contact)); contactDB.saveContact
  }

  def delete(id: Long) = { contacts -= id}

  def list(): List[Contact] = { contacts.values.toList}
}