package com.knol.core
/**
   * importing packages
   */
import scala.slick.driver.PostgresDriver.simple._
import java.util.Date
import java.text.SimpleDateFormat
import scala.slick.lifted.ProvenShape
import com.knol.db.connection.DbConnection
import com.knol.core._
//import com.knol.core.KnolRepo
/**
   * defining trait SessionRepo for body of functions and mapping to DB
   */
trait SessionRepo extends KnolRepo with DbConnection {
  /**
   * defining class KnolxTable for mapping to knolx table in DB
   */
  class KnolxTable(tag: Tag) extends Table[KnolSessionCC](tag, "knolx") {
    def id: Column[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def topic: Column[String] = column[String]("topic", O.NotNull)
    def date: Column[Date] = column[Date]("date", O.NotNull)
    def knol_id: Column[Int] = column[Int]("knol_id", O.NotNull)

    def * = (topic, date, knol_id, id) <> (KnolSessionCC.tupled, KnolSessionCC.unapply)
  }
/**
   * creating Table query obj of knolxTable
   */
  val knolxTable = TableQuery[KnolxTable]
  
  
/**
   * function body of insertOfSession() getting object of KnolSessionCC class and returning Int 1 for success and 0 for failure
   */
  def insertOfSession(sess: KnolSessionCC): Option[Int] = {
    con.withSession { implicit session =>
      println("call insert")
      Some(knolxTable.insert(sess))

    }

  }

  /**
   * function body of deleteOfSession() getting ID and returning Int 1 for success and 0 for failure
   */
  def deleteOfSession(id: Int): Option[Int] = {

    con.withSession { implicit session =>
      Some(knolxTable.filter(_.id === 1).delete)

    }

  }
  
  /**
   * function body of updateOfSession() getting object of KnolSessionCC class and returning Int 1 for success and 0 for failure
   */
  def updateOfSession(clg: KnolSessionCC): Option[Int] = {

    con.withSession { implicit session =>
      Some(knolxTable.filter(_.id === clg.id).update(clg))

    }

  }
  
  /**
   * function body of showOfSession() getting ID and returning List of KnolSessionCC objs
   */
  def showOfSession(id: Int): Option[List[KnolSessionCC]] = {

    con.withSession { implicit session =>

      val listOfKnols = knolxTable.filter(_.id === id).list

      listOfKnols foreach println
      Some(listOfKnols)

    }

  }
  /**
   * function body of joinQueryFunction() getting ID and returning List of KnolJoinSession objs
   * this method is doing Join Query
   */

  def joinQueryFunction(idToBeSee: Int): List[KnolJoinSession] = {

    con.withSession { implicit session =>

      val ans = knolTableQuery innerJoin knolxTable on (_.knol_id === _.knol_id) list

      val anc = ans.map {
        case (knolTableQuery, knolxTable) => KnolJoinSession(knolxTable.id, knolTableQuery.knol_idClass, knolTableQuery.name, knolxTable.topic, knolxTable.date)
      }
      anc
    }
  }
}

case class KnolSessionCC(topic: String, date: Date, knol_id: Int, id: Int = 0)
case class KnolJoinSession(id: Int, knol_id: Int, name: String, topic: String, date: Date)

