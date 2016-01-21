package ch.usi.inf.reveal.parsing.stormed.service

import ch.usi.inf.reveal.parsing.model.HASTNode


case class ParseRequest(text: String, key: String)

trait Response {
  val status: String
}
case class SuccessResponse(result: Seq[HASTNode], quotaRemaining: Int, status: String) extends Response
case class ErrorResponse(message: String, status: String) extends Response




