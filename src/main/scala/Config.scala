import com.typesafe.config.ConfigFactory

/** Loads and holds the configuration for the server */
object Config
{
  private val conf = ConfigFactory.load("application.conf")
  lazy val IP = conf.getString("spray.can.server.ip")
  lazy val PORT = conf.getInt("spray.can.server.port")
}
