import com.revature.ers.utils.Router;
import io.javalin.Javalin;

// purpose: start application
public class MainDriver {
    public static void main(String[] args) {
        Javalin app = Javalin.create(ctx -> {
            ctx.contextPath = "/ers";
        }).start(8080);

        Router.router(app);
    }
}
