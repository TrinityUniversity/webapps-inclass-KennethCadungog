package models

object CodeGen extends App {
    slick.codegen.SourceCodeGenerator.run(
        "slick.jdbc.PostgresProfile",
        "org.postgresql.Driver",
        "jdbc:postgresql://localhost/kcadungo?user=kcadungo&password=0850099",
        "/users/kcadungo/webapps-inclass-KennethCadungog/server/app/",
        "models", None, None, true, false 
    )
}
