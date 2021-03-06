package com.impressory.play

/**
 * 
 */

import play.api.libs.json.{Writes, JsString, JsNull}
import reactivemongo.bson.BSONObjectID
import com.impressory.reactivemongo.HasBSONId
import com.wbillingsley.handy.{Ref, LazyId}


package object model {
  
  import _root_.com.impressory.reactivemongo
  
  /*
   * For the moment, we're using package type aliases to direct
   * particular types to particular database implementations.
   * 
   * This is a temporary measure, as code is ported from the
   * previous structure (The Intelligent Book)
   */
  type User = reactivemongo.User
  val User = reactivemongo.User
  def refUser(id:String) = new LazyId(classOf[User], id)

  type Identity = reactivemongo.Identity
  val Identity = reactivemongo.Identity
  
  type Course = reactivemongo.Course
  val Course = reactivemongo.Course
  def refCourse(id:String) = new LazyId(classOf[Course], id)
  
  type Registration = reactivemongo.Registration
  val Registration = reactivemongo.Registration

  type ContentEntry = reactivemongo.ContentEntry
  val ContentEntry = reactivemongo.ContentEntry
  type CESettings = reactivemongo.CESettings
  val CESettings = reactivemongo.CESettings
  type CETags = reactivemongo.CETags
  val CETags = reactivemongo.CETags

  def refContentEntry(id:String) = new LazyId(classOf[ContentEntry], id)
  
  type ContentItem = reactivemongo.ContentItem
  
  type ContentSequence = reactivemongo.ContentSequence
  val ContentSequence = reactivemongo.ContentSequence
  
  type WebPage = reactivemongo.WebPage
  val WebPage = reactivemongo.WebPage
  
  type ChatComment = reactivemongo.ChatComment
  val ChatComment = reactivemongo.ChatComment
  
  type CourseInvite = reactivemongo.CourseInvite
  val CourseInvite = reactivemongo.CourseInvite
  
  type RecordedChatEvent = reactivemongo.RecordedChatEvent
  
  type QnAQuestion = reactivemongo.QnAQuestion
  val QnAQuestion = reactivemongo.QnAQuestion
  def refQnAQuestion(id:String) = new LazyId(classOf[QnAQuestion], id)
  
  type QnAAnswer = reactivemongo.QnAAnswer
  val QnAAnswer = reactivemongo.QnAAnswer
  
  type EmbeddedComment = reactivemongo.EmbeddedComment
  val EmbeddedComment = reactivemongo.EmbeddedComment
  
  type UpDownVoting = reactivemongo.UpDownVoting
  val UpDownVoting = reactivemongo.UpDownVoting

  type GoogleSlides = reactivemongo.GoogleSlides
  val GoogleSlides = reactivemongo.GoogleSlides
  type YouTubeVideo = reactivemongo.YouTubeVideo
  val YouTubeVideo = reactivemongo.YouTubeVideo
  type MarkdownPage = reactivemongo.MarkdownPage
  val MarkdownPage = reactivemongo.MarkdownPage
  type MultipleChoicePoll = reactivemongo.MultipleChoicePoll
  val MultipleChoicePoll = reactivemongo.MultipleChoicePoll
  type MCPollResponse = reactivemongo.MCPollResponse
  val MCPollResponse = reactivemongo.MCPollResponse
  
}