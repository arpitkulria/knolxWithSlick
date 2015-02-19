package com.knol.core
/**
 * KnolRepo for function body of operations on KNOL table 
 * 
 */
import scala.slick.driver.PostgresDriver.simple._
import java.util.Date
import java.text.SimpleDateFormat
import scala.slick.lifted.ProvenShape
import com.knol.db.connection.DbConnection
import com.knol.core._


/**
 * creatting a trait and we will give implementation of functions 
 */
trait KnolRepo extends DbConnection {
/**
 * util2sqlDateMapper is for converting java.util.date to sql.date and vice versa
 */
  implicit val util2sqlDateMapper = MappedColumnType.base[java.util.Date, java.sql.Date](
    { utilDate => new java.sql.Date(utilDate.getTime()) },
    { sqlDate => new java.util.Date(sqlDate.getTime()) })

    /**
     * class knolTable for mapping this class to table "knol"
     */
    class KnolTable(tag: Tag) extends Table[Knol](tag, "knol") {
    def knol_id: Column[Int] = column[Int]("knol_id", O.PrimaryKey, O.AutoInc)
    def name: Column[String] = column[String]("name", O.NotNull)
    def email: Column[String] = column[String]("email", O.NotNull)
    def mobile: Column[String] = column[String]("mobile", O.NotNull)
    def * = (name, email, mobile, knol_id) <> (Knol.tupled, Knol.unapply)
  }
/**
 * generating TableQuery obj of KnolTable Class
 */
  val knolTableQuery = TableQuery[KnolTable]

  /**
   * getting DataBase Obj in "con"
   */
  
  val con = getConnection().get

  /**
   * function body of insert() getting object of Knol class and returning Int 1 for success and 0 for failure
   */
  def insert(clg: Knol): Option[Int] = {
    con.withSession { implicit session =>

      val ans = knolTableQuery.insert(clg)

      Some(ans)

    }

  }
  /**
   * function body of delete() getting id to be deleted and returning Int 1 for success and 0 for failure
   */
  
  def delete(idToBeDeleted: Int): Option[Int] = {

    con.withSession { implicit session =>

      Some(knolTableQuery.filter(_.knol_id === idToBeDeleted).delete)

    }

  }
  /**
   * function body of update() getting object of Knol class and returning Int 1 for success and 0 for failure
   */
  def update(clg: Knol): Option[Int] = {
    con.withSession { implicit session =>
      Some(knolTableQuery.filter(_.knol_id === clg.knol_idClass).update(clg))
    }

  }
  
  /**
   * function body of show() getting ID and returning list of knol objs
   */
  def show(id: Int): Option[List[Knol]] = {
    con.withSession { implicit session =>

      val listOfKnols = knolTableQuery.filter(_.knol_id === id).list

      //listOfKnols.foreach(i => println(i))

      listOfKnols foreach println
      Some(listOfKnols)

    }

  }

}

/**
   * defining case class Knol
   */
case class Knol(name: String, email: String,mobile:String,knol_idClass:Int = 0)
