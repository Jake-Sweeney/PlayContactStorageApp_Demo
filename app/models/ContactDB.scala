package models

/**
  * Created by Jake on 10/01/2016.
  */

import play.api.Play.current
import play.api.db.DB
import play.api.mvc._

import anorm._
import anorm.SQL
import anorm.SqlParser._

import scala.collection.immutable.Iterable
import scala.collection.mutable.HashMap

class ContactDB() {

   def saveContact(contact: Contact) = {
     val contactID = getNextId
     val contactName = contact.contact

      DB.withConnection { implicit connection =>
        val result: Int =
          SQL("INSERT INTO contact VALUES ({id},{name},'0871604565')")
            .on('id -> contactID, 'name -> contactName)
            .executeUpdate()
        println("Contact saved to database.\n" + result + " row(s) updated.")
      }
  }

  def getNextId: Int = DB.withConnection { implicit connection =>
    //use apply and head when result is a single set with a single column.
    val selectid = SQL("select max(contactid) as id from contact").apply().head
    //Cast result as int and retrieve it using alias given in select statement (id).
    val nextId = selectid[Int]("id") + 1
    nextId
  }

  val contactRowParser: ResultSetParser[List[(Int,String)]] = {
    get[Int]("contactid")~get[String]("contactname") map(flatten) *
  }

  def retrieveContactNames() : List[Contact] = DB.withConnection { implicit conection =>
    //For some reason the compiler doesn't recognise this syntax for anorm.
    //Must look into this problem but the function successfully retrieves data from the desired table.
    val selectStatement: List[(Int,String)] = {
      SQL("SELECT contactid, contactname from Contact")
        .as(contactRowParser)
    }
    //Yield returns a collection after the loop has finished.
    // In this case it's a list of Contacts using the key, value pairs of the map.
    val contactsList: List[Contact] = for((key,value) <- selectStatement) yield { new Contact(key, value)}
    contactsList
  }
}
