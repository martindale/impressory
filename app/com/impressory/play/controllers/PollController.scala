package com.impressory.play.controllers

import com.wbillingsley.handy._
import Ref._
import com.wbillingsley.handyplay.RefConversions._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.iteratee.Enumerator
import play.api.libs.iteratee.Enumeratee
import play.api.libs.iteratee.Iteratee
import play.api.http.HeaderNames
import com.wbillingsley.eventroom.Subscribe
import com.impressory.play.model._
import com.impressory.play.eventroom._
import com.impressory.api._
import ResultConversions._
import com.impressory.play.json.JsonConverters._

object PollController extends Controller {
  
  /**
   * Called when a vote arrives by POST
   */
  def vote(courseId:String, pid:String) = Action(parse.json) { implicit request =>

    val approval = request.approval
    val session = RequestUtils.sessionKey(request.session);
    val sk = session.getOrElse(RequestUtils.newSessionKey);
    
    import MCPollModel._
    
    val resp = for (
        e <- refContentEntry(pid);
        answer <- Ref((request.body \ "options").asOpt[Set[Int]]) orIfNone UserError("No options in that vote");
        previous <- optionally(MCPollResponse.byUserOrSession(e.itself, approval.who, session));
        pr <- MCPollModel.vote(e.itself, approval, Some(sk), answer);
        json = pr.toJson
    ) yield {
      val adjustments = scala.collection.mutable.Map[Int,Int]()
      
      for (idx <- pr.answer) adjustments(idx) = 1
      for (prev <- previous; idx <- prev.answer) adjustments(idx) = adjustments.getOrElse(idx, 0) - 1
      
      EventRoom.default ! MCPollEvents.Vote(e.id.stringify, adjustments.toMap)
      
      Ok(Json.obj("vote" -> json)).withSession(RequestUtils.withSessionKey(request.session, sk))
    }
    resp
  }
  
  /**
   * Pushes the poll to the interaction stream
   */
  def pushMCPollToStream(courseId:String, pid:String) = Action { implicit request =>
    
    import MCPollModel._
    
    val resp = for (
        poll <- refContentEntry(pid);
        approved <- request.approval ask Permissions.Chat(poll.course)
    ) yield {
      EventRoom.default ! MCPollEvents.PushPollToChat(poll)
      Ok("")
    }
    resp    
    
    
  }

}