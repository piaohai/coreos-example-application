package cakesolutions.api

import akka.event.LoggingReceive
import cakesolutions.logging.Logging
import spray.http.StatusCodes._
import spray.routing._

class RestService(route: Route) extends HttpServiceActor with Logging {

  implicit val rejectionHandler: RejectionHandler = RejectionHandler {
    case AuthenticationFailedRejection(_, _) :: _ =>
      complete(Unauthorized)
  }

  implicit val exceptionHandler = ExceptionHandler {
    case exn =>
      log.error(s"Unexpected exception during routing: ${exceptionString(exn)}")
      complete(InternalServerError)
  }

  val mainRoute: Route =
    logRequestResponse("CoreOS Application API") {
      route ~
        complete(NotFound, "Requested resource was not found")
    }

  def receive = LoggingReceive { runRoute(mainRoute) }

}