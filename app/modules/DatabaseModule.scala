package modules

import com.google.inject.{AbstractModule, Provides, Singleton}
import javax.inject.Inject
import play.api.Configuration
import slick.jdbc.PostgresProfile.api.Database

class DatabaseModule extends AbstractModule {
  override def configure(): Unit = {}

  @Provides
  @Singleton
  def provideSlickDatabase(conf: Configuration): Database =
    Database.forConfig("db", conf.underlying)
}
