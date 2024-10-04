//import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
//import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.function.RequestPredicates;
//import org.springframework.web.servlet.function.RouterFunction;
//import org.springframework.web.servlet.function.RouterFunctions;
//import org.springframework.web.servlet.function.ServerResponse;
//
//import java.net.URI;
//
//@Configuration
//public class Routes {
//
//    @Bean
//    public RouterFunction<ServerResponse> productServiceRoute() {
//        return RouterFunctions.route(
//                RequestPredicates.path("/api/product/**"),
//                request -> ServerResponse.permanentRedirect(URI.create("http://localhost:8081" + request.path())).build()
//        );
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> orderServiceRoute() {
//        return RouterFunctions.route(
//                RequestPredicates.path("/api/order/**"),
//                request -> ServerResponse.permanentRedirect(URI.create("http://localhost:8082" + request.path())).build()
//        );
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> inventoryServiceRoute() {
//        return RouterFunctions.route(
//                RequestPredicates.path("/api/inventory/**"),
//                request -> ServerResponse.permanentRedirect(URI.create("http://localhost:8083" + request.path())).build()
//        );
//    }
//
//}
