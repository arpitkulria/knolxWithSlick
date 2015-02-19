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

class KnolRepoTest extends FunSuite with DbConnection with KnolRepo with BeforeAndAfter {

  before {
    con.withSession { implicit session =>
      (knolTableQuery.ddl).create
      insert(Knol("DemoName", "Demo@Demo.com", "Demo777", 1))

    }

  }
  after {

    con.withSession { implicit session =>
      (knolTableQuery.ddl).drop

    }

  }

  test("Test For Insert") {
    //pending

    val interVal = Knol("Arpit", "arpit@knoldus.com", "7737778097", 0)

    val output = insert(interVal)

    assert(output === Some(1))

  }
    test("Test for Delete") {
    //pending

     val output = delete(1)

    assert(output === Some(1))

  }
      test("Test For Update") {
    //pending

    val interVal = Knol("Arpit", "arpit@knoldus.com", "7737778097", 1)

    val output = update(interVal)

    assert(output === Some(1))

  }
        test("Test For Show Data") {
    //pending
    val output = show(1)
    val expectedOP= scala.collection.immutable.List(Knol("DemoName", "Demo@Demo.com", "Demo777", 1))
      assert(output.get === expectedOP)

  }

}