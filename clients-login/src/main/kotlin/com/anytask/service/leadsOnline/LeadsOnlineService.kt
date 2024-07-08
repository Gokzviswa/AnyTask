package com.anytask.service.leadsOnline

import com.anytask.models.entity.user.AnyTaskUser
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt
import javax.swing.Spring.height





@Service
@Transactional
@Component
open class LeadsOnlineService() {

    open fun geHelloAge(user: AnyTaskUser, pawnNo: Int, imgURL: String): String {
         val dateTimeFormatter = SimpleDateFormat("MM-dd-yyyy")
        dateTimeFormatter.timeZone = TimeZone.getTimeZone("EST")
        val editedDate = dateTimeFormatter.format(Date.from(Instant.now()))
        println("editedDate $editedDate")
        println("editedDate ${Instant.now()}")
        //val base64String = urlToBase64(imgURL)
        println("imgURL $imgURL")

        val feet = extractFeetInInches(imgURL)
        println("feet $feet")
        return "Happy"
    }
}

private fun parseXmlFile(`in`: String): Document {
    return try {
        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        val `is` = InputSource(StringReader(`in`))
        db.parse(`is`)
    } catch (e: ParserConfigurationException) {
        throw RuntimeException(e)
    } catch (e: SAXException) {
        throw RuntimeException(e)
    } catch (e: IOException) {
        throw RuntimeException(e)
    }
}

private fun urlToBase64(imgURL: String): String {
    val client1 = OkHttpClient.Builder()
        .connectTimeout(90, TimeUnit.SECONDS) // Adjust timeout as needed (e.g., 30 seconds)
        .build()
    var base64String: String? = null
    val request1 = Request.Builder()
        .url(imgURL)
        .build()
    client1.newCall(request1).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code when converting image to Base64 $response")
        val bytes = response.body?.bytes()
        if (bytes != null) {
            base64String = Base64.getEncoder().encodeToString(bytes)
        } else {
            println("Error: Response body is null when converting image to Base64.")
        }
    }
    return base64String!!
}

fun extractFeetInInches(heights: String): Int {
    val height = heights.replace("-","")
    val parts = height.split("'".toRegex()).toTypedArray()
    var feetPart = 0
    var inchesPart = 0
    if (parts.size == 2){
        feetPart = parts[0].toInt()
        inchesPart = parts[1].replace("\"", "").toInt()
    } else{
        println(height.length)
        println(height.toDouble().toInt())
        feetPart = height.toDouble().toInt()
        inchesPart = if(height.length == 3)((height.toDouble() - feetPart)* 10).roundToInt() else ((height.toDouble() - feetPart)* 100).roundToInt()
        println(inchesPart)
        println(inchesPart)
    }
    return feetPart * 12 + inchesPart
}