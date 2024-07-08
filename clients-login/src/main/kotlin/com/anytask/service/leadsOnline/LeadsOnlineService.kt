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
        //val wsURL = "https://www.dataaccess.com/webservicesserver/NumberConversion.wso"
//        val wsURL = "https://sandbox.leadsonline.com/leads/ws/pawn/ticketWS.asmx"
//        var url: URL? = null
//        var connection: URLConnection? = null
//        var httpConn: HttpURLConnection? = null
//        var responseString: String? = null
//        var outputString = ""
//        var out: OutputStream? = null
//        var isr: InputStreamReader? = null
//        var `in`: BufferedReader? = null
//        val dateTimeFormatter = SimpleDateFormat("MM-dd-yyyy")
//        val editedDate = dateTimeFormatter.format(Date.from(Instant.now()))
//        val xmlInput = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
//                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
//                    "    <soap:Body>\n" +
//                    "        <SubmitTransaction xmlns=\"http://www.leadsonline.com/\">\n" +
//                    "            <login>\n" +
//                    "                <storeId>53308</storeId>\n" +
//                    "                <userName>pptesting4</userName>\n" +
//                    "                <password>testing4pp</password>\n" +
//                    "            </login>\n" +
//                    "            <ticket>\n" +
//                    "                <key>\n" +
//                    "                    <ticketType>Pawn</ticketType>\n" +
//                    "                    <ticketnumber>PWN08</ticketnumber>\n" +
//                    "                    <ticketDateTime>05-02-2024</ticketDateTime>\n" +
//                    "                </key>\n" +
//                    "                <redeemByDate></redeemByDate>\n" +
//                    "                <customer>\n" +
//                    "                    <name>Jack</name>\n" +
//                    "                    <address1>123 ANYWHERE STREET</address1>\n" +
//                    "                    <city>PODUNK</city>\n" +
//                    "                    <state>AR</state>\n" +
//                    "                    <postalCode>27272</postalCode>\n" +
//                    "                    <phone>555-555-1212</phone>\n" +
//                    "                    <idType>DL</idType>\n" +
//                    "                    <idNumber>1234567890</idNumber>\n" +
//                    "                    <dob>1975-04-01</dob>\n" +
//                    "                    <weight>150</weight>\n" +
//                    "                    <height>70</height>\n" +
//                    "                    <eyeColor>Brown</eyeColor>\n" +
//                    "                    <hairColor>Brown</hairColor>\n" +
//                    "                    <race>Caucasian</race>\n" +
//                    "                    <sex>M</sex>\n" +
//                    "                    <extraCustomer>\n" +
//                    "                        <PropertyValue>\n" +
//                    "                            <Name>CUSTOMER_EMPLOYER</Name>\n" +
//                    "                            <Value>Disney Corp</Value>\n" +
//                    "                        </PropertyValue>\n" +
//                    "                        <PropertyValue>\n" +
//                    "                            <Name>CUSTOMER_EMPLOYER_PHONE</Name>\n" +
//                    "                            <Value>666-666-1313</Value>\n" +
//                    "                        </PropertyValue>\n" +
//                    "                    </extraCustomer>\n" +
//                    "                </customer>\n" +
//                    "                <items>\n" +
//                    "                    <Item>\n" +
//                    "                        <make>Gold</make>\n" +
//                    "                        <model>Ring</model>\n" +
//                    "                        <description>22kt</description>\n" +
//                    "                        <amount>500.00</amount>\n" +
//                    "                        <itemType>Jewelry</itemType>\n" +
//                    "                        <itemStatus>Active</itemStatus>\n" +
//                    "                        <isVoid>false</isVoid>\n" +
//                    "                        <employee>Carlos</employee>\n" +
//                    "                        <extraItem>\n" +
//                    "                            <PropertyValue>\n" +
//                    "                                <Name>PHONE_IMEI_NUMBER</Name>\n" +
//                    "                                <Value>99988877766655544333</Value>\n" +
//                    "                            </PropertyValue>\n" +
//                    "                            <PropertyValue>\n" +
//                    "                                <Name>ITEM_CONDITION</Name>\n" +
//                    "                                <Value>Like New</Value>\n" +
//                    "                            </PropertyValue>\n" +
//                    "                        </extraItem>\n" +
//                    "                        </Item>\n" +
//                    "                    </items>\n" +
//                    "                    <isVoid>false</isVoid>\n" +
//                    "                </ticket>\n" +
//                    "            </SubmitTransaction>\n" +
//                    "        </soap:Body>\n" +
//                    "    </soap:Envelope>"
//        try {
//            url = URL(wsURL)
//            connection = url.openConnection()
//            httpConn = connection as HttpURLConnection?
//            val buffer = xmlInput.toByteArray()
//            val soapAction = ""
//            // Set the appropriate HTTP parameters.
//            httpConn!!.setRequestProperty("Content-Length", buffer.size.toString())
//            httpConn.setRequestProperty("Content-Type", "text/xml; charset=iso-8859-1")
//            httpConn.setRequestProperty("SOAPAction", soapAction)
//            httpConn.requestMethod = "POST"
//            httpConn.doOutput = true
//            httpConn.doInput = true
//            println("httpConn $httpConn")
//            out = httpConn.outputStream
//            println("httpConn.outputStream ${httpConn.outputStream}")
//            out.write(buffer)
//            out.close()
//            println("out $out")
//            println("httpConn.inputStream ${httpConn.inputStream}")
//
//            // Read the response and write it to standard out.
//            isr = InputStreamReader(httpConn.inputStream)
//            `in` = BufferedReader(isr)
//            while (`in`.readLine().also { responseString = it } != null) {
//                outputString += responseString
//            }
//            println("outputString $outputString")
//            println("******************")
//
//            // Get the response from the web service call
//            val document: Document = parseXmlFile(outputString)
//            //val nodeLst: NodeList = document.getElementsByTagName("m:NumberToWordsResult")
//            val nodeLst: NodeList = document.getElementsByTagName("errorCode")
//            val webServiceResponse: String = nodeLst.item(0).textContent
//            println("The response from the web service call is : $webServiceResponse")
//        } catch (e: Exception) {
//            println("e.message ${e.message}")
//            e.printStackTrace()
//        }
//    }
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
//        val items =
//                "                       <Item>\n" +
//                "                        <make>" + user.email + "</make>\n" +
//                "                        <model>" + user.email + "</model>\n" +
//                "                        <serialNumber>" + user.mobileNumber + "</serialNumber>\n" +
//                "                        <description>" + user.firstName + "</description>\n" +
//                "                        <amount>" + totalInches + "</amount>\n" +
//                "                        <itemType>Jewelry</itemType>\n" +
//                "                        <itemStatus>Active</itemStatus>\n" +
//                "                        <isVoid>false</isVoid>\n" +
//                "                        <images>\n" +
//                "                           <Image>\n" +
//                "                            <imageCategory>Item</imageCategory>\n" +
//                "                            <imageType>Jpeg</imageType>\n" +
//                "                            <imageData> base64String </imageData>\n" +
//                "                          </Image>\n" +
//                "                       </images>\n" +
//                "                        <employee></employee>\n" +
//                "                        </Item>"
//        val mediaType = "text/xml; charset=utf-8".toMediaType()
//        val body = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
//                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
//                "    <soap:Body>\n" +
//                "        <SubmitTransaction xmlns=\"http://www.leadsonline.com/\">\n" +
//                "            <login>\n" +
//                "                <storeId>53308</storeId>\n" +
//                "                <userName>pptesting4</userName>\n" +
//                "                <password>testing4pp</password>\n" +
//                "            </login>\n" +
//                "            <ticket>\n" +
//                "                <key>\n" +
//                "                    <ticketType>Pawn</ticketType>\n" +
//                "                    <ticketnumber>PWN0"+ pawnNo +"</ticketnumber>\n" +
//                "                    <ticketDateTime>"+ editedDate +"</ticketDateTime>\n" +
//                "                </key>\n" +
//                "                <redeemByDate></redeemByDate>\n" +
//                "                <customer>\n" +
//                "                    <name>Jack</name>\n" +
//                "                    <address1>28 METTU STREET</address1>\n" +
//                "                    <city>MIAMI</city>\n" +
//                "                    <state>FL</state>\n" +
//                "                    <postalCode>27272</postalCode>\n" +
//                "                    <phone>555-555-1212</phone>\n" +
//                "                    <idType>DL</idType>\n" +
//                "                    <idNumber>1234567890</idNumber>\n" +
//                "                    <dob>1975-04-01</dob>\n" +
//                "                    <weight>180</weight>\n" +
//                "                    <height>70</height>\n" +
//                "                    <eyeColor>Brown</eyeColor>\n" +
//                "                    <hairColor>Brown</hairColor>\n" +
//                "                    <race>Caucasian</race>\n" +
//                "                    <sex>M</sex>\n" +
//                "                    <extraCustomer>\n" +
//                "                        <PropertyValue>\n" +
//                "                            <Name>CUSTOMER_EMPLOYER</Name>\n" +
//                "                            <Value>Disney Corp</Value>\n" +
//                "                        </PropertyValue>\n" +
//                "                        <PropertyValue>\n" +
//                "                            <Name>CUSTOMER_EMPLOYER_PHONE</Name>\n" +
//                "                            <Value>666-666-1313</Value>\n" +
//                "                        </PropertyValue>\n" +
//                "                    </extraCustomer>\n" +
//                "                </customer>\n" +
//                "                <items>\n" + items +"\n" +
//                "                </items>\n" +
//                "                    <isVoid>false</isVoid>\n" +
//                "                </ticket>\n" +
//                "            </SubmitTransaction>\n" +
//                "        </soap:Body>\n" +
//                "    </soap:Envelope>".trimIndent()
//        val xmlBody = body.toRequestBody(mediaType)
//        println("body $body")
//        val client = OkHttpClient.Builder()
//            .connectTimeout(90, TimeUnit.SECONDS) // Adjust timeout as needed (e.g., 30 seconds)
//            .build()
//        val request = Request.Builder()
//            .url("https://sandbox.leadsonline.com/leads/ws/pawn/ticketWS.asmx?WSDL")
//            .post(xmlBody)
//            .addHeader("Content-Type", "text/xml; charset=utf-8")
//            .build()
//        client.newCall(request).execute().use { response ->
//            println("response $response")
//            val document: Document = parseXmlFile(response.body!!.string())
//            //val nodeLst: NodeList = document.getElementsByTagName("m:NumberToWordsResult")
//            val nodeLst: NodeList = document.getElementsByTagName("errorCode")
//            val webServiceResponse: String = nodeLst.item(0).textContent
//            return if (webServiceResponse != 0.toString()) {
//                val nodeLst1: NodeList = document.getElementsByTagName("errorResponse")
//                val webServiceResponse1: String = nodeLst1.item(0).textContent
//                ("The Error response from the web service call is : $webServiceResponse1")
//            } else {
//                // Get the response from the web service call
//                ("The Success response from the web service call is : $webServiceResponse")
//            }
//        }
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


//            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                    "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
//                    "  <soap:Body>\n" +
//                    "    <NumberToWords xmlns=\"http://www.dataaccess.com/webservicesserver/\">\n" +
//                    "      <ubiNum>" + age + "</ubiNum>\n" +
//                    "    </NumberToWords>\n" +
//                    "  </soap:Body>\n" +
//                    "</soap:Envelope>"

//<?xml version="1.0" encoding="utf-8"?>
//<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
//<soap:Body>
//<SubmitTransactionResponse xmlns="http://www.leadsonline.com/">
//<SubmitTransactionResult>
//<errorCode>0</errorCode>
//<errorResponse />
//</SubmitTransactionResult>
//</SubmitTransactionResponse>
//</soap:Body>
//</soap:Envelope>
//
//<?xml version="1.0" encoding="utf-8"?>
//<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
//<soap:Body>
//<m:NumberToWordsResponse xmlns:m="http://www.dataaccess.com/webservicesserver/">
//<m:NumberToWordsResult>five hundred </m:NumberToWordsResult>
//</m:NumberToWordsResponse>
//</soap:Body>
//</soap:Envelope>