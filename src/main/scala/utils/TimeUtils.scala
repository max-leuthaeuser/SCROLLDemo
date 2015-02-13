package utils

import java.lang.management.ManagementFactory
import java.text.SimpleDateFormat
import java.util.{Calendar, TimeZone}

object TimeUtils {
  private lazy val date_format_peer = new SimpleDateFormat(STD_TIME_FORMAT)
  private val STD_TIME_FORMAT = "HH:mm:ss:SSS"

  def nowAsString: String = {
    val cal = Calendar.getInstance()
    date_format_peer.format(cal.getTime)
  }

  def uptime: String = {
    val mx = ManagementFactory.getRuntimeMXBean
    val df = new SimpleDateFormat("HH 'hours', mm 'mins', ss 'seconds'")
    df.setTimeZone(TimeZone.getTimeZone("GMT+0"))
    val uptime = mx.getUptime
    uptime / (3600 * 1000 * 24) + " day(s), " + df.format(uptime)
  }
}
