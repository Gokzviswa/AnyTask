//package com.propawn.webserver.configuration
//
//import io.jsonwebtoken.Jwts
//import io.jsonwebtoken.SignatureAlgorithm
//import org.apache.tomcat.util.codec.binary.Base64
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.stereotype.Component
//import java.io.File
//import java.io.IOException
//import java.security.GeneralSecurityException
//import java.security.KeyFactory
//import java.security.interfaces.RSAPrivateKey
//import java.security.interfaces.RSAPublicKey
//import java.security.spec.PKCS8EncodedKeySpec
//import java.security.spec.X509EncodedKeySpec
//import java.util.*
//import javax.servlet.http.HttpServletRequest
//
//@Component(value = "jwtService")
//open class JwtService(
//    @Value("\${jwtTokenValidity}") private var jwtTokenValidity: Int,
//    @Value("\${pubKeyPath}") private val  pubKeyPath: String,
//    @Value("\${privateKeyPath}") private val privateKeyPath: String) {
//
//    //    @PostConstruct
//    @Throws(Exception::class)
//    fun setPrivateKey(): String {
//        var strKeyPEM = ""
//        val lines = readFileAsLinesUsingBufferedReader(privateKeyPath)
//        for (line in lines) {
//            strKeyPEM += line +"\n"
//        }
//        return strKeyPEM
//    }
//
//    private fun readFileAsLinesUsingBufferedReader(fileName: String): List<String>
//            = File(fileName).bufferedReader().readLines()
//
//    open fun generateToken(userName: String, role: String, httpServletRequest: HttpServletRequest): String? {
//        val rsaPrivateKey: RSAPrivateKey
//        try {
//
//            rsaPrivateKey = getPrivateKeyFromString(setPrivateKey())
//            return Jwts.builder()
//                .setId(UUID.randomUUID().toString())
//                .setSubject("LoginResponse")
//                .claim("userName", userName)
//                .claim("role", role)
//                .claim("remoteAddress",getClientIp(httpServletRequest))
//                .setIssuedAt(Date(System.currentTimeMillis()))
//                .setExpiration(Date(System.currentTimeMillis() + jwtTokenValidity * 1000))
//                .signWith(SignatureAlgorithm.RS256, rsaPrivateKey)
//                .compact()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } catch (e: GeneralSecurityException) {
//            e.printStackTrace()
//        }
//        return null
//    }
//
//    @Throws(IOException::class, GeneralSecurityException::class)
//    open fun getPrivateKeyFromString(key: String): RSAPrivateKey {
//        var privateKeyPEM = key
//        privateKeyPEM = privateKeyPEM.replace("-----BEGIN RSA PRIVATE KEY-----\n", "")
//        privateKeyPEM = privateKeyPEM.replace("-----END RSA PRIVATE KEY-----", "")
//        val encoded = Base64.decodeBase64(privateKeyPEM)
//        val kf = KeyFactory.getInstance("RSA")
//        val keySpec = PKCS8EncodedKeySpec(encoded)
//        return kf.generatePrivate(keySpec) as RSAPrivateKey
//    }
//
//    private fun getClientIp(request: HttpServletRequest?): String {
//        var remoteAddr = ""
//        if (request != null) {
////            remoteAddr = request.getHeader("X-FORWARDED-FOR")
////            if (remoteAddr == null || "" == remoteAddr) {
////
////            }
//            remoteAddr = request.remoteAddr
//        }
//        return remoteAddr
//    }
//
//
//}