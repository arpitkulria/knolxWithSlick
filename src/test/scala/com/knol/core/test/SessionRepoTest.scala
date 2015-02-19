package com.knol.core.test

import org.scalatest.FunSuite
import com.knol.db.connection.DbConnection
import com.knol.core.KnolRepo
import org.scalatest.BeforeAndAfter
import scala.slick.driver.PostgresDriver.simple._
import java.util.Date
import java.text.SimpleDateFormat
import scala.slick.lifted.ProvenShape
import com.knol.db.connection.DbConnection
import com.knol.core._
//import com.knol.core.KnolRepo.

class SessionRepoTest extends FunSuite with DbConnection with SessionRepo with KnolRepo with BeforeAndAfter {

  before {
    con.withSession { implicit session =>
      (knolxTable.ddl ++ knolTableQuery.ddl).create
      println("create")
      insert(Knol("DemoName", "Demo@Demo.com", "Demo777", 1))
      insertOfSession(KnolSessionCC("Scala", new Date("2/2/2012"), 1, 1))
      println(" after insert")
    }

  }
  after {
    con.withSession { implicit session =>
      (knolxTable.ddl ++ knolTableQuery.ddl).drop
    }

  }

  test("Test For Insert In Sesssion") {
    //pending

    val interVal = KnolSessionCC("Scala Collection", new Date("12/3/2014"), 1, 1)

    val output = insertOfSession(interVal)

    assert(output === Some(1))

  }
  test("Test for Delete") {
    //pending

    val output = deleteOfSession(1)

    assert(output === Some(1))

  }
  test("Test For Update") {
    //pending

    val interVal = KnolSessionCC("Scala With Slick", new Date("2/2/2015"), 1, 1)

    val output = updateOfSession(interVal)

    assert(output === Some(1))

  }

  test("Test For Show Data") {
    //pending
    val output = showOfSession(1)
    val expectedOP = scala.collection.immutable.List(KnolSessionCC("Scala", new Date("2/2/2012"), 1, 1))
    assert(output.get === expectedOP)

  }

  test("Test For Join") {
    //pending

    val output = joinQueryFunction(1)

    assert(output === scala.collection.immutable.List(KnolJoinSession(1, 1, "DemoName", "Scala", new Date("2/2/2012"))))

  }

}