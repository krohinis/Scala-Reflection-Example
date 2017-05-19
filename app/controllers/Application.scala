package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future
import scala.reflect.runtime.universe

case class Person(name:String,address:String){
  println("Instance Created")
}
 case class Employee(name:String,address:String){
  println("Employee Instance Created")
}

object Application extends Controller{
  def studentDetails = Action.async { request =>
    val jsonRequest = request.body.asJson.get
    val classHolders = (jsonRequest \ "data").as[List[ClassHolder]]
    classHolders.foreach(data=>{
      ClassInst(data.classType,data.params)
    })
    Future(Ok(Json.toJson(classHolders)))
  }
}

object ClassInst{
  def apply(clsName: String,params:Map[String,String])={
    val runtimeMirror = universe.runtimeMirror(getClass.getClassLoader)
    val classSymbol = runtimeMirror.classSymbol(Class.forName(clsName))
    val classMirror = runtimeMirror.reflectClass(classSymbol)

   /* val clsnm=classSymbol.getClass
    val instanceMirror = runtimeMirror.reflect(clsnm)
    val marks = universe.typeOf[clsnm.type ].declaration(universe.newTermName("name")).asTerm
    val fieldMirror = instanceMirror.reflectField(marks)

    println(fieldMirror.set(clsnm))
    println(classMirror)
    println(fieldMirror)*/


    println(classMirror)
  }

}

