package rs.raf.Web_Project.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.Web_Project.entities.User;
import rs.raf.Web_Project.entities.enums.Type;
import rs.raf.Web_Project.repositories.user.IUserRepository;
import rs.raf.Web_Project.resources.CommentResource;

import javax.inject.Inject;
import java.util.Date;

public class UserService {
    @Inject
    IUserRepository userRepository;

    public User create(User user){
        String hashedPassword = DigestUtils.sha256Hex(user.getPassword());
        user.setPassword(hashedPassword);
        return this.userRepository.addUser(user);
    }

    public User update(User user) {
        String hashedPassword = DigestUtils.sha256Hex(user.getPassword());
        user.setPassword(hashedPassword);
        return this.userRepository.update(user);
    }

    public User find(String email) {
        return this.userRepository.findUser(email);
    }

    public boolean deactivate(Integer id){
        return this.userRepository.deactivateUser(id);
    }

    public String login(String email, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);
        User user = this.userRepository.findUser(email);
        if (user == null || !user.getPassword().equals(hashedPassword)) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("secret");

        // JWT-om mozete bezbedono poslati informacije na FE
        // Tako sto sve sto zelite da posaljete zapakujete u claims mapu
        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("role", user.getType().toString())
                .sign(algorithm);
    }

    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();
        User user = this.userRepository.findUser(email);

        if (user == null){
            return false;
        }

        return true;
    }

    public boolean isAdmin(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();
        Type role = Type.valueOf(jwt.getClaim("role").asString());
        User user = this.userRepository.findUser(email);

        if (user == null || !role.equals(Type.ADMIN)){
            return false;
        }
        return true;
    }
}
