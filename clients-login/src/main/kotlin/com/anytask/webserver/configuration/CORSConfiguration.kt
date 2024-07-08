//package com.anytask.webserver.configuration
//
//import org.springframework.core.Ordered
//import org.springframework.core.annotation.Order
//import org.springframework.stereotype.Component
//import java.io.IOException
//import javax.servlet.*
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//class CORSConfiguration : Filter {
//    @Throws(IOException::class, ServletException::class)
//    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
//        val request = req as HttpServletRequest
//        val response = res as HttpServletResponse
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"))
//        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE")
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Authorization,Content-Type")
//        response.setHeader("Access-Control-Max-Age", "3600")
//
//        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
//        if (request.method == "OPTIONS") {
//            response.status = HttpServletResponse.SC_ACCEPTED
//            return
//        }
//        chain.doFilter(req, res)
//    }
//
//    override fun init(filterConfig: FilterConfig) {}
//    override fun destroy() {}
//}
