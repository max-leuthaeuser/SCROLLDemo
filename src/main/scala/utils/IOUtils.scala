package utils

import java.io.{File, FileWriter, PrintWriter}

import scala.io.Source.fromFile
import scala.language.reflectiveCalls

object IOUtils {
  def writeToFile(
                   fileName: String,
                   data: String
                   ) {
    using(new FileWriter(fileName)) {
      fileWriter => fileWriter.write(data)
    }
  }

  def appendToFile(
                    fileName: String,
                    textData: String
                    ) {
    using(new FileWriter(fileName, true)) {
      fileWriter =>
        using(new PrintWriter(fileWriter)) {
          printWriter => printWriter.print(textData)
        }
    }
  }

  /**
   * Used for reading/writing to database, files, etc.
   * Code From the book "Beginning Scala"
   * http://www.amazon.com/Beginning-Scala-David-Pollak/dp/1430219890
   */
  def using[A <: {def close()}, B](param: A)
                                  (f: A => B): B =
    try {
      f(param)
    } finally {
      param.close()
    }

  def createDirectory(dir: File) {
    if (!dir.exists && !dir.isDirectory) {
      dir.mkdirs()
    }
  }

  def getFileTree(f: File): Stream[File] =
    f #:: (if (f.isDirectory) {
      f.listFiles().toStream.flatMap(getFileTree)
    }
    else {
      Stream.empty
    })

  def getFileTreeFilter(
                         f: File,
                         str: String
                         ): Array[String] = getFileTree(f).filter(_.getName.endsWith(str))
    .map(_.getName.dropRight(str.length)).toArray

  def readFromFile(f: File): String = {
    val src = fromFile(f)
    try {
      src.getLines().mkString("\n")
    }
    finally src match {
      case b: scala.io.BufferedSource => b.close()
    }
  }

}
