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
   val contactDB = new ContactDB(); contactDB.saveContact(Contact(nextId(), contact))
  }

  def delete(id: Long) = { contacts -= id}

//  def list() = contacts.values.toList

  def list(): List[Contact] = { val contactDB = new ContactDB; contactDB.retrieveContactNames() }
}