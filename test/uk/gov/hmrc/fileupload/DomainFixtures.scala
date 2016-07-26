/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.fileupload

import java.net.URLConnection
import java.util.UUID

import org.apache.commons.io.FileUtils
import play.api.libs.iteratee.Enumerator

object DomainFixtures {

  def anyFileId = FileId(randomUUID)

  def anyEnvelopeId = EnvelopeId(randomUUID)

  def anyFile() = anyFileFor()

  def temporaryTexFile(data: Option[String] = None) = {
    val temporaryFile = java.io.File.createTempFile("tmp", ".txt")

    data.foreach(FileUtils.writeStringToFile(temporaryFile, _))

    temporaryFile.deleteOnExit()
    temporaryFile
  }

  def anyFileFor(envelopeId: EnvelopeId = anyEnvelopeId, fileId: FileId = anyFileId, file: java.io.File = temporaryTexFile()) = {
    import scala.concurrent.ExecutionContext.Implicits.global

    File(Enumerator.fromFile(file), file.getName, Some(URLConnection.guessContentTypeFromName(file.getName)), envelopeId, fileId)
  }

  private def randomUUID = UUID.randomUUID().toString
}