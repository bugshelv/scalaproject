package security

import org.mindrot.jbcrypt.BCrypt

object PasswordHasher {

  def hashPassword(plainPassword: String): String =
    BCrypt.hashpw(plainPassword, BCrypt.gensalt())

  def checkPassword(plainPassword: String, hashedPassword: String): Boolean =
    BCrypt.checkpw(plainPassword, hashedPassword)
}