package nl.hu.bep.setup.security;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.Calendar;
import java.util.Map;
import nl.hu.bep.setup.domain.User;
/**
 * The type Authentication resource.
 */
@Path("authentication")
public class AuthenticationResource {

    public static final Key key = MacProvider.generateKey();

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(LogonRequest logonRequest) {
        try {

            String username = logonRequest.getUsername();
            String password = logonRequest.getPassword();

            User user = User.getUser(username, password);

            String token = createToken(logonRequest.getUsername());

            return Response.ok(Map.of("JWT", token)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String createToken(String username) throws JwtException {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration.getTime())
                .claim("role", "user")
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}