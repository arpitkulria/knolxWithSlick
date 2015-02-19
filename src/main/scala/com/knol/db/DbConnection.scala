package com.knol.db.connection
import scala.slick.driver.H2Driver.simple._
trait DbConnection {

    def getConnection():Option[Database]={
      val  drive = "org.postgresql.Driver"
      
      Some(Database.forURL(url = "jdbc:postgresql://localhost:5432/slickdemo", user = "postgres",password = "postgres", driver=drive))
         
    }
}