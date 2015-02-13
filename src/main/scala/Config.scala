import com.typesafe.config.ConfigFactory

/** Loads and holds the configuration for the server */
object Config
{
  private val conf = ConfigFactory.load("application.conf")
  lazy val IP = conf.getString("spray.can.server.ip")
  lazy val PORT = conf.getInt("spray.can.server.port")
  lazy val HIDE_SWING = conf.getBoolean("spray.can.server.hide-swing")
  lazy val MAP = conf.getString("spray.can.server.default-map")
  lazy val ADMIN = conf.getString("spray.can.server.admin.pass")
  lazy val HIDE_MAZES = conf.getBoolean("spray.can.server.hide-mazes")
  lazy val MAZE_WIDTH = conf.getInt("spray.can.server.maze-width")
  lazy val MAZE_HEIGHT = conf.getInt("spray.can.server.maze-height")
}
